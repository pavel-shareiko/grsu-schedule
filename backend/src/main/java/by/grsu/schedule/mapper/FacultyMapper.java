package by.grsu.schedule.mapper;

import by.grsu.schedule.api.dto.FacultyDto;
import by.grsu.schedule.api.dto.FacultySearchItemDto;
import by.grsu.schedule.api.dto.request.FacultySearchRequestDto;
import by.grsu.schedule.domain.FacultyEntity;
import by.grsu.schedule.model.criteria.FacultySearchCriteria;
import by.grsu.schedule.repository.specification.FacultySearchSpecification;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuFacultyDto;
import org.mapstruct.Mapper;

@Mapper
public interface FacultyMapper {
    FacultyEntity toEntity(FacultyDto facultyDto);

    FacultyDto toDto(GrsuFacultyDto grsuFacultyDto);

    FacultySearchCriteria toCriteria(FacultySearchRequestDto requestDto);

    FacultySearchSpecification toSpecification(FacultySearchCriteria criteria);

    FacultySearchItemDto toDto(FacultyEntity entity);
}
