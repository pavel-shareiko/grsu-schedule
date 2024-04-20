package by.grsu.schedule.controller;

import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geocoding")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GeocodingController {
    AddressService addressService;

    @GetMapping("/forward")
    public ResponseEntity<Coordinate> getCoordinates(@RequestParam(name = "address") String address) {
        Coordinate result = addressService.getAddressLocation(address);
        return ResponseEntity.ok(result);
    }
}
