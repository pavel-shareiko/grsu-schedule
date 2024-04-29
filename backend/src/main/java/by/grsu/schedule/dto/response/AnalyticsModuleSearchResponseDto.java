package by.grsu.schedule.dto.response;

import by.grsu.schedule.dto.ShortAnalyticsModuleInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticsModuleSearchResponseDto {
    List<ShortAnalyticsModuleInfoDto> modules;
}
