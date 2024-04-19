package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.TeacherEntity;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuTeacherDto;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper {
    TeacherDto toDto(GrsuTeacherDto grsuTeacherDto);

    TeacherEntity toEntity(TeacherDto teacherDto);
}
