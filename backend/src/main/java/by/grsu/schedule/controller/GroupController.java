package by.grsu.schedule.controller;

import by.grsu.schedule.api.GroupApi;
import by.grsu.schedule.api.dto.request.GroupSearchRequestDto;
import by.grsu.schedule.api.dto.response.GroupSearchResponseDto;
import by.grsu.schedule.mapper.GroupMapper;
import by.grsu.schedule.model.criteria.GroupSearchCriteria;
import by.grsu.schedule.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController implements GroupApi {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Override
    public ResponseEntity<GroupSearchResponseDto> searchGroups(GroupSearchRequestDto requestDto,
                                                               int page,
                                                               int rowsPerPage) {
        GroupSearchCriteria criteria = groupMapper.toCriteria(requestDto);
        GroupSearchResponseDto response = groupService.searchGroups(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
