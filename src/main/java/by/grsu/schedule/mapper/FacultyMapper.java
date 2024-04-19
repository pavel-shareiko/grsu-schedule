package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.FacultyEntity;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuFacultyDto;
import org.mapstruct.Mapper;

@Mapper
public interface FacultyMapper {
    FacultyEntity toEntity(FacultyDto facultyDto);

    FacultyDto toDto(GrsuFacultyDto grsuFacultyDto);
}
