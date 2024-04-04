package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Faculty;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuFacultyDto;
import org.mapstruct.Mapper;

@Mapper
public interface FacultyMapper {
    Faculty toEntity(FacultyDto facultyDto);

    FacultyDto toDto(GrsuFacultyDto grsuFacultyDto);
}
