package by.grsu.schedule.gateway.grsu.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GrsuGroupDto {
    Long id;
    String title;
}
