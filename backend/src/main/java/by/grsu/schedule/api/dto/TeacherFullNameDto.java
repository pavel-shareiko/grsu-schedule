package by.grsu.schedule.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TeacherFullNameDto {
    Long id;
    String fullName;
}
