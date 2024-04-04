package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Teacher;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuTeacherDto;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper {
    TeacherDto toDto(GrsuTeacherDto grsuTeacherDto);

    Teacher toEntity(TeacherDto teacherDto);
}
