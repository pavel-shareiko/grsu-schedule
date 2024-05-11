package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.GroupSearchRequestDto;
import by.grsu.schedule.api.dto.response.GroupSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/groups")
public interface GroupApi {

    @PostMapping("/search")
    ResponseEntity<GroupSearchResponseDto> searchGroups(@RequestBody GroupSearchRequestDto requestDto,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int rowsPerPage);

}
