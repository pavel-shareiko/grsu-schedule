package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.api.dto.ShortAnalysisHistoryDto;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.AnalysisStatus;
import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = JacksonMapper.class)
public interface AnalysisMapper {
    JacksonMapper JACKSON_MAPPER = Mappers.getMapper(JacksonMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTimestamp", ignore = true)
    @Mapping(target = "result", source = "analysisResult", qualifiedByName = "extractAnalysisResult")
    @Mapping(target = "status", source = "analysisResult.status")
    @Mapping(target = "moduleName", source = "analysisResult.moduleName")
    @Mapping(target = "context", source = "context")
    @Mapping(target = "createdBy", ignore = true)
    AnalysisResultEntity toEntity(AnalysisResult analysisResult, Object context);

    @Mapping(target = "timestamp", source = "createTimestamp")
    ShortAnalysisHistoryDto toDto(AnalysisResultEntity latestResult);

    @Named("extractAnalysisResult")
    default JsonNode extractAnalysisResult(AnalysisResult analysisResult) {
        if (analysisResult.getStatus() == AnalysisStatus.SUCCESS) {
            return JACKSON_MAPPER.toJsonNode(analysisResult.getDetails());
        } else {
            return JACKSON_MAPPER.toJsonNode(analysisResult.getError());
        }
    }
}
