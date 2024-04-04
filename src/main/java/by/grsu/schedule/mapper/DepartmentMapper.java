package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Department;
import by.grsu.schedule.dto.DepartmentDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuDepartmentDto;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentDto toDto(GrsuDepartmentDto grsuDepartmentDto);

    Department toEntity(DepartmentDto department);
}
