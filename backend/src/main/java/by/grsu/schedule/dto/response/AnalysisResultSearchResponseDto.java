package by.grsu.schedule.dto.response;

import by.grsu.schedule.dto.AnalysisResultDto;
import by.grsu.schedule.dto.PaginationDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultSearchResponseDto {
    List<AnalysisResultDto> payload;
    PaginationDto pagination;
}
