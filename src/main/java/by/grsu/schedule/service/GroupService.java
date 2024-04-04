package by.grsu.schedule.service;

import by.grsu.schedule.domain.Group;
import by.grsu.schedule.dto.GroupDto;
import by.grsu.schedule.mapper.GroupMapper;
import by.grsu.schedule.repository.GroupRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
        List<Group> groupsToSave = groups.stream()
                .map(groupMapper::toEntity)
                .toList();
        groupRepository.saveAll(groupsToSave);
    }
}
