package by.grsu.schedule.controller;

import by.grsu.schedule.api.GeocodingApi;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GeocodingController implements GeocodingApi {
    private final AddressService addressService;

    @Override
    public ResponseEntity<Coordinate> getCoordinates(String address) {
        Coordinate result = addressService.getAddressLocation(address);
        return ResponseEntity.ok(result);
    }
}
