package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.api.dto.AnalysisResultDto;
import by.grsu.schedule.api.dto.request.AnalysisResultSearchRequestDto;
import by.grsu.schedule.model.criteria.AnalysisResultSearchCriteria;
import by.grsu.schedule.repository.specification.AnalysisResultSearchSpecification;
import org.mapstruct.Mapper;

@Mapper
public interface AnalysisResultMapper {
    AnalysisResultSearchCriteria toCriteria(AnalysisResultSearchRequestDto requestDto);

    AnalysisResultSearchSpecification toSpecification(AnalysisResultSearchCriteria criteria);

    AnalysisResultDto toDto(AnalysisResultEntity analysisResultEntity);
}
