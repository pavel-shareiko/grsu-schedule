package by.grsu.schedule.service.gateway.geo;

import by.grsu.schedule.api.dto.AddressDto;
import by.grsu.schedule.persistence.Coordinate;

public interface GeoApiGateway {
    Coordinate getAddressLocation(AddressDto address);

    Coordinate getAddressLocation(String addressQuery);
}
