package by.grsu.schedule.persistence;

import by.grsu.schedule.util.GeoUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return new EqualsBuilder()
                .append(latitude, that.latitude)
                .append(longitude, that.longitude)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(latitude).append(longitude).toHashCode();
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }
}
