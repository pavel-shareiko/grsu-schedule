package by.grsu.schedule.util;

import by.grsu.schedule.persistence.Coordinate;
import lombok.experimental.UtilityClass;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;

@UtilityClass
public class GeoUtils {
    public static final int EARTH_RADIUS_IN_KM = 6371;
    public static final Distance ZERO_DISTANCE = new Distance(0.0);
    public static final double AVERAGE_WALKING_SPEED_IN_KMH = 5.0;

    public static Distance calculateDistance(Coordinate first, Coordinate second) {
        double lat1 = Math.toRadians(first.getLatitude());
        double lat2 = Math.toRadians(second.getLatitude());
        double lon1 = Math.toRadians(first.getLongitude());
        double lon2 = Math.toRadians(second.getLongitude());

        double acos = Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1) + Math.sin(lat1) * Math.sin(lat2));
        return new Distance(acos * EARTH_RADIUS_IN_KM, Metrics.KILOMETERS);
    }
}
