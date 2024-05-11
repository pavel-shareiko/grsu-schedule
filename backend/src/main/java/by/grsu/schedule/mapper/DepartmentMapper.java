package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.DepartmentEntity;
import by.grsu.schedule.api.dto.DepartmentDto;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuDepartmentDto;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentDto toDto(GrsuDepartmentDto grsuDepartmentDto);

    DepartmentEntity toEntity(DepartmentDto department);
}
