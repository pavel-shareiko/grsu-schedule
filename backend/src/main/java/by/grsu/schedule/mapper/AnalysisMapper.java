package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.dto.ShortAnalysisHistoryDto;
import by.grsu.schedule.model.analytics.AnalysisResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = JacksonMapper.class)
public interface AnalysisMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTimestamp", ignore = true)
    @Mapping(target = "result", source = "analysisResult.details")
    @Mapping(target = "status", source = "analysisResult.status")
    @Mapping(target = "moduleName", source = "analysisResult.moduleName")
    @Mapping(target = "context", source = "context")
    AnalysisResultEntity toEntity(AnalysisResult analysisResult, Object context);

    @Mapping(target = "timestamp", source = "createTimestamp")
    ShortAnalysisHistoryDto toDto(AnalysisResultEntity latestResult);
}
