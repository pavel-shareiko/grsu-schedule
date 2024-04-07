package by.grsu.schedule.gateway.grsu.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GrsuTeacherDayScheduleDto {
    LocalDate date;
    List<GrsuTeacherLessonDto> lessons;
}
