package by.grsu.schedule.api.dto;

import by.grsu.schedule.persistence.Coordinate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    Long id;
    String title;
    String country;
    String city;
    String street;
    String house;
    Coordinate location;
}