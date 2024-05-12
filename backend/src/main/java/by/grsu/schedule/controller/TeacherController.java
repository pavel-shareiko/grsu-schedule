package by.grsu.schedule.controller;

import by.grsu.schedule.api.TeacherApi;
import by.grsu.schedule.api.dto.request.TeacherSearchRequestDto;
import by.grsu.schedule.api.dto.response.TeacherSearchResponseDto;
import by.grsu.schedule.mapper.TeacherMapper;
import by.grsu.schedule.model.criteria.TeacherSearchCriteria;
import by.grsu.schedule.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherApi {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @Override
    public ResponseEntity<TeacherSearchResponseDto> searchTeachers(TeacherSearchRequestDto requestDto,
                                                                   int page,
                                                                   int rowsPerPage) {
        TeacherSearchCriteria criteria = teacherMapper.toCriteria(requestDto);
        TeacherSearchResponseDto response = teacherService.searchTeachers(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
