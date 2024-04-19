package by.grsu.schedule.gateway.geo.locationiq;

import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.service.gateway.geo.AddressQueryBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AddressQueryBuilderTest {
    AddressQueryBuilder builder = new AddressQueryBuilder();

    @ParameterizedTest
    @MethodSource("getAddressQueryTestCases")
    void shouldBuildQuery(AddressDto address, String format, String expectedQuery) {
        // when
        String query = builder.buildAddressQuery(address, format);

        // then
        assertThat(query).isEqualTo(expectedQuery);
    }

    public static Stream<Arguments> getAddressQueryTestCases() {
        return Stream.of(
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "constant format",
                        "constant format"
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "mixed format: %C, %c, %s, %h",
                        "mixed format: Belarus, Hrodna, Fomichova, 1"
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s, %h",
                        "Belarus, Hrodna, Fomichova, 1"
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s",
                        "Belarus, Hrodna, Fomichova"
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s, %h!",
                        "Belarus, Hrodna, Fomichova, 1"
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s, %x, %h!",
                        null
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s, %h, %x",
                        null
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", "1"),
                        "%C, %c, %s, %h, %x!",
                        null
                ),
                Arguments.of(
                        generateAddress(null, "Hrodna", "Fomichova", "1"),
                        "%C!, %c, %s, %h",
                        null
                ),
                Arguments.of(
                        generateAddress("Belarus", null, "Fomichova", "1"),
                        "%C, %c!, %s, %h",
                        null
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", null, "1"),
                        "%C, %c, %s!, %h",
                        null
                ),
                Arguments.of(
                        generateAddress("Belarus", "Hrodna", "Fomichova", null),
                        "%C, %c, %s, %h!",
                        null
                )
        );
    }

    private static AddressDto generateAddress(String country, String city, String street, String house) {
        return new AddressDto()
                .setCountry(country)
                .setCity(city)
                .setStreet(street)
                .setHouse(house);
    }

}