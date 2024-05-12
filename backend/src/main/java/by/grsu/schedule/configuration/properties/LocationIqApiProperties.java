package by.grsu.schedule.configuration.properties;

import by.grsu.schedule.api.dto.AddressDto;
import by.grsu.schedule.service.gateway.geo.AddressQueryBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConditionalOnProperty(value = "application.services.location-iq.enabled", havingValue = "true")
@ConfigurationProperties("application.services.location-iq")
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationIqApiProperties {
    /**
     * Defines whether LocationIQ API is enabled
     */
    Boolean enabled;

    /**
     * URL for LocationIQ API
     */
    String url;

    /**
     * API key for LocationIQ API
     */
    String apiKey;

    /**
     * Format of the address
     *
     * @see AddressQueryBuilder#buildAddressQuery(AddressDto, String)
     */
    String addressFormat;

    /**
     * Number of permitted requests to LocationIQ API per second
     */
    Double rateLimit;
}
