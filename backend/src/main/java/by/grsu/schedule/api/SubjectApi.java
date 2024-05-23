package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.SubjectSearchRequestDto;
import by.grsu.schedule.api.dto.response.SubjectSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/subjects")
public interface SubjectApi {
    @PostMapping("/search")
    ResponseEntity<SubjectSearchResponseDto> searchSubjects(@RequestBody SubjectSearchRequestDto requestDto,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int rowsPerPage);
}
