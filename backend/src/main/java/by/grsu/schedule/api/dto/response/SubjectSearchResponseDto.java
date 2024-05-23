package by.grsu.schedule.api.dto.response;

import by.grsu.schedule.api.dto.PaginationDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectSearchResponseDto {
    List<SubjectSearchItemDto> payload;
    PaginationDto pagination;
}
