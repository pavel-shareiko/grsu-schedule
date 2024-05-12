package by.grsu.schedule.controller;

import by.grsu.schedule.api.FacultyApi;
import by.grsu.schedule.api.dto.request.FacultySearchRequestDto;
import by.grsu.schedule.api.dto.response.FacultySearchResponseDto;
import by.grsu.schedule.mapper.FacultyMapper;
import by.grsu.schedule.model.criteria.FacultySearchCriteria;
import by.grsu.schedule.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FacultyController implements FacultyApi {
    private final FacultyService facultyService;
    private final FacultyMapper facultyMapper;

    @Override
    public ResponseEntity<FacultySearchResponseDto> searchFaculties(FacultySearchRequestDto requestDto, int page, int rowsPerPage) {
        FacultySearchCriteria criteria = facultyMapper.toCriteria(requestDto);
        FacultySearchResponseDto response = facultyService.searchFaculties(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
