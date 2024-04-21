package by.grsu.schedule.service.gateway.grsu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GrsuQualifiedGroupDto {
    Long id;
    String title;
    @JsonProperty("faculty.id")
    Long facultyId;
    @JsonProperty("department.id")
    Long departmentId;
}
