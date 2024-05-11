package by.grsu.schedule.controller;

import by.grsu.schedule.api.SubjectApi;
import by.grsu.schedule.dto.request.SubjectSearchRequestDto;
import by.grsu.schedule.dto.response.SubjectSearchResponseDto;
import by.grsu.schedule.mapper.SubjectMapper;
import by.grsu.schedule.model.criteria.SubjectSearchCriteria;
import by.grsu.schedule.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubjectController implements SubjectApi {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @Override
    public ResponseEntity<SubjectSearchResponseDto> searchTeachers(SubjectSearchRequestDto requestDto,
                                                                   int page,
                                                                   int rowsPerPage) {
        SubjectSearchCriteria criteria = subjectMapper.toCriteria(requestDto);
        SubjectSearchResponseDto response = subjectService.searchSubjects(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
