package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Faculty;
import by.grsu.schedule.dto.FacultyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface FacultyMapper {
    @Mapping(target = "externalId", source = "id")
    Faculty toEntity(FacultyDto facultyDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "title")
    Faculty merge(@MappingTarget Faculty faculty, FacultyDto newFaculty);
}
