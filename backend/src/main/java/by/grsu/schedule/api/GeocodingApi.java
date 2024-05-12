package by.grsu.schedule.api;

import by.grsu.schedule.persistence.Coordinate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/geocoding")
public interface GeocodingApi {

    @GetMapping("/forward")
    ResponseEntity<Coordinate> getCoordinates(@RequestParam(name = "address") String address);

}
