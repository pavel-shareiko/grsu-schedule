package by.grsu.schedule.controller;

import by.grsu.schedule.dto.request.GroupSearchRequestDto;
import by.grsu.schedule.dto.response.GroupSearchResponseDto;
import by.grsu.schedule.mapper.GroupMapper;
import by.grsu.schedule.model.GroupSearchCriteria;
import by.grsu.schedule.service.GroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GroupController {
    GroupService groupService;
    GroupMapper groupMapper;

    @PostMapping("/search")
    public ResponseEntity<GroupSearchResponseDto> searchGroups(@RequestBody GroupSearchRequestDto requestDto,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int rowsPerPage) {
        GroupSearchCriteria criteria = groupMapper.toCriteria(requestDto);
        GroupSearchResponseDto response = groupService.searchGroups(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
