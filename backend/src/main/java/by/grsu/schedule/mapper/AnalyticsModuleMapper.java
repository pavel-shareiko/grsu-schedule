package by.grsu.schedule.mapper;

import by.grsu.schedule.api.dto.ShortAnalyticsModuleInfoDto;
import by.grsu.schedule.model.analytics.AnalyticsModule;
import org.mapstruct.Mapper;

@Mapper
public interface AnalyticsModuleMapper {
    ShortAnalyticsModuleInfoDto map(AnalyticsModule module);
}
