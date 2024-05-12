package by.grsu.schedule.service;

import by.grsu.schedule.domain.GroupEntity;
import by.grsu.schedule.api.dto.GroupDto;
import by.grsu.schedule.api.dto.PaginationDto;
import by.grsu.schedule.api.dto.response.GroupSearchResponseDto;
import by.grsu.schedule.mapper.GroupMapper;
import by.grsu.schedule.model.criteria.GroupSearchCriteria;
import by.grsu.schedule.repository.GroupRepository;
import by.grsu.schedule.repository.specification.GroupSearchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class GroupService {
    GroupRepository groupRepository;
    GroupMapper groupMapper;

    @Transactional
    public void upsert(List<GroupDto> groups) {
        List<GroupEntity> groupsToSave = groups.stream()
                .map(groupMapper::toEntity)
                .toList();
        groupRepository.saveAll(groupsToSave);
    }

    public GroupSearchResponseDto searchGroups(GroupSearchCriteria criteria, int page, int rowsPerPage) {
        GroupSearchSpecification specification = groupMapper.toSpecification(criteria);

        Page<GroupEntity> groupsPage = groupRepository.findAll(specification, PageRequest.of(page, rowsPerPage));

        List<GroupDto> groupDtos = groupsPage.stream()
                .map(groupMapper::toDto)
                .toList();

        return new GroupSearchResponseDto()
                .setPagination(PaginationDto.of(groupsPage))
                .setPayload(groupDtos);
    }
}
