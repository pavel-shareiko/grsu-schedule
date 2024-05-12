package by.grsu.schedule.api.dto.response;

import by.grsu.schedule.api.dto.ShortAnalyticsModuleInfoDto;
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
