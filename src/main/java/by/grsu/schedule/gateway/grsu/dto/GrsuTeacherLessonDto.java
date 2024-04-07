package by.grsu.schedule.gateway.grsu.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GrsuTeacherLessonDto {
    Long id;
    LocalTime timeStart;
    LocalTime timeEnd;
    List<GrsuQualifiedGroupDto> groups;
    String label;
    String type;
    String title;
    String address;
    String room;
}
