package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.GroupEntity;
import by.grsu.schedule.api.dto.GroupDto;
import by.grsu.schedule.api.dto.request.GroupSearchRequestDto;
import by.grsu.schedule.model.criteria.GroupSearchCriteria;
import by.grsu.schedule.repository.specification.GroupSearchSpecification;
import by.grsu.schedule.service.gateway.grsu.dto.GrsuGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GroupMapper {
    GroupDto toDto(GrsuGroupDto grsuGroupDto, Long facultyId, Long departmentId);

    @Mapping(target = "facultyId", source = "faculty.id")
    @Mapping(target = "departmentId", source = "department.id")
    GroupDto toDto(GroupEntity grsuGroupDto);

    @Mapping(target = "faculty.id", source = "facultyId")
    @Mapping(target = "department.id", source = "departmentId")
    GroupEntity toEntity(GroupDto groupDto);

    GroupSearchCriteria toCriteria(GroupSearchRequestDto requestDto);

    GroupSearchSpecification toSpecification(GroupSearchCriteria criteria);
}
