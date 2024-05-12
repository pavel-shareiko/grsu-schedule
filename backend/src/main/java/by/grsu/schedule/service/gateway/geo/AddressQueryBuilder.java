package by.grsu.schedule.service.gateway.geo;

import by.grsu.schedule.api.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

@Slf4j
public class AddressQueryBuilder {
    private static final Map<Character, Function<AddressDto, String>> specifierToAddressPartMap = Map.of(
            'C', AddressDto::getCountry,
            'c', AddressDto::getCity,
            's', AddressDto::getStreet,
            'h', AddressDto::getHouse
    );

    /**
     * Builds a query for the given address
     * <p>This method is equivalent to <code>getAddressQuery(address, "%c,%s,%h")</code></p>
     *
     * @param address address to build query for
     * @return query for the given address
     */
    public String buildAddressQuery(AddressDto address) {
        return buildAddressQuery(address, "%c,%s,%h");
    }

    /**
     * Builds a query for the given address
     * <h2>Format specifiers:</h2>
     * <ul>
     *     <li><code>%C</code> - country</li>
     *     <li><code>%c</code> - city</li>
     *     <li><code>%s</code> - street</li>
     *     <li><code>%h</code> - house</li>
     *     <li><code>{specifier}<b>!</b></code> - if a specifier is followed by '!',
     *     it means that the part is required, and null will be returned in case it is missing</li>
     *     <li>Any other character is treated as a literal</li>
     * </ul>
     * <p>If some part of the address is missing, it is omitted from the query</p>
     *
     * @param address address to build query for
     * @param format  format of the query
     * @return query for the given address
     */
    public String buildAddressQuery(AddressDto address, String format) {
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            if (c != '%') {
                query.append(c);
                continue;
            }

            if (i == format.length() - 1) {
                log.warn("Invalid format: % at the end of the string");
                return null;
            }

            char specifier = format.charAt(++i);
            if (!isValidSpecifier(specifier)) {
                log.warn("Invalid specifier: {}", specifier);
                return null;
            }
            boolean isRequired = i < format.length() - 1 && format.charAt(i + 1) == '!';
            if (isRequired) {
                i++;
            }

            String part = getPartBySpecifier(address, specifier);
            if (part == null) {
                if (isRequired) {
                    log.warn("{} is required, but not provided", specifier);
                    return null;
                } else {
                    continue;
                }
            }
            query.append(part);
        }

        return query.toString();
    }

    private boolean isValidSpecifier(char specifier) {
        return specifierToAddressPartMap.containsKey(specifier);
    }

    private String getPartBySpecifier(AddressDto address, char specifier) {
        return specifierToAddressPartMap.getOrDefault(specifier, addr -> null).apply(address);
    }
}
