package by.grsu.schedule.persistence;

import by.grsu.schedule.util.GeoUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Distance;

@Getter
@Setter
public class Coordinate {
    private Double latitude;
    private Double longitude;

    public static Coordinate of(Double latitude, Double longitude) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        return coordinate;
    }

    public Distance distanceTo(Coordinate coordinate) {
        return GeoUtils.calculateDistance(this, coordinate);
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }
}
