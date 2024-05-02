package by.grsu.schedule.dto.response;

import by.grsu.schedule.dto.PaginationDto;
import by.grsu.schedule.dto.TeacherDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherSearchResponseDto {
    List<TeacherDto> payload;
    PaginationDto pagination;
}
