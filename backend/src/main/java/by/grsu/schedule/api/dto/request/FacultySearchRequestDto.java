package by.grsu.schedule.api.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacultySearchRequestDto {
    Long id;
    String title;
}
