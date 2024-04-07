package by.grsu.schedule.dto;

import by.grsu.schedule.persistence.Coordinate;
import lombok.Data;

@Data
public class AddressDto {
    Long id;
    String title;
    String country;
    String city;
    String street;
    String house;
    Coordinate location;
}