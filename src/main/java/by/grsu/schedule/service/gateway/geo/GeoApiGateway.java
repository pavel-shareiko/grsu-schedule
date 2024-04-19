package by.grsu.schedule.service.gateway.geo;

import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.persistence.Coordinate;

public interface GeoApiGateway {
    Coordinate getAddressLocation(AddressDto address);
}