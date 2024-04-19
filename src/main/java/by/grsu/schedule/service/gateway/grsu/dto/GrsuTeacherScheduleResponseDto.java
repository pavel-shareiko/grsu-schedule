package by.grsu.schedule.service.gateway.grsu.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GrsuTeacherScheduleResponseDto {
    int count;
    List<GrsuTeacherDayScheduleDto> days;
}
