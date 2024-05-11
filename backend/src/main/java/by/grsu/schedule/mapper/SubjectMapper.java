package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.SubjectEntity;
import by.grsu.schedule.api.dto.SubjectDto;
import by.grsu.schedule.api.dto.request.SubjectSearchRequestDto;
import by.grsu.schedule.model.criteria.SubjectSearchCriteria;
import by.grsu.schedule.repository.specification.SubjectSearchSpecification;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {
    SubjectSearchCriteria toCriteria(SubjectSearchRequestDto requestDto);

    SubjectSearchSpecification toSpecification(SubjectSearchCriteria criteria);

    SubjectDto toDto(SubjectEntity subjectEntity);
}
