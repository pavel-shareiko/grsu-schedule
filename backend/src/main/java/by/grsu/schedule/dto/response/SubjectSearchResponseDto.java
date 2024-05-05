package by.grsu.schedule.dto.response;

import by.grsu.schedule.dto.PaginationDto;
import by.grsu.schedule.dto.SubjectDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectSearchResponseDto {
    List<SubjectDto> payload;
    PaginationDto pagination;
}
