package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.model.AnalysisResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(uses = JacksonMapper.class)
public interface AnalysisMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTimestamp", ignore = true)
    @Mapping(target = "result", source = "analysisResult.details")
    @Mapping(target = "status", source = "analysisResult.status")
    @Mapping(target = "moduleName", source = "analysisResult.moduleName")
    AnalysisResultEntity toEntity(AnalysisResult analysisResult, Map<String, Object> context);
}
