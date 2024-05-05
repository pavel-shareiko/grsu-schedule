package by.grsu.schedule.controller;

import by.grsu.schedule.dto.request.SubjectSearchRequestDto;
import by.grsu.schedule.dto.response.SubjectSearchResponseDto;
import by.grsu.schedule.mapper.SubjectMapper;
import by.grsu.schedule.model.SubjectSearchCriteria;
import by.grsu.schedule.service.SubjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SubjectController {
    SubjectService subjectService;
    SubjectMapper subjectMapper;

    @PostMapping("/search")
    public ResponseEntity<SubjectSearchResponseDto> searchTeachers(@RequestBody SubjectSearchRequestDto requestDto,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int rowsPerPage) {
        SubjectSearchCriteria criteria = subjectMapper.toCriteria(requestDto);
        SubjectSearchResponseDto response = subjectService.searchSubjects(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
