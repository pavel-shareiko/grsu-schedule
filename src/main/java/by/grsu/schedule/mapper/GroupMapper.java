package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Group;
import by.grsu.schedule.dto.GroupDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GroupMapper {
    GroupDto toDto(GrsuGroupDto grsuGroupDto, Long facultyId, Long departmentId);

    @Mapping(target = "faculty.id", source = "facultyId")
    @Mapping(target = "department.id", source = "departmentId")
    Group toEntity(GroupDto groupDto);
}
