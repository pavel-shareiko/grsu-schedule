package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.TeacherEntity;
import by.grsu.schedule.api.dto.TeacherDto;
import by.grsu.schedule.api.dto.request.TeacherSearchRequestDto;
import by.grsu.schedule.model.criteria.TeacherSearchCriteria;
import by.grsu.schedule.repository.specification.TeacherSearchSpecification;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuTeacherDto;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper {
    TeacherDto toDto(GrsuTeacherDto grsuTeacherDto);

    TeacherDto toDto(TeacherEntity entity);

    TeacherEntity toEntity(TeacherDto teacherDto);

    TeacherSearchCriteria toCriteria(TeacherSearchRequestDto requestDto);

    TeacherSearchSpecification toSpecification(TeacherSearchCriteria criteria);
}
