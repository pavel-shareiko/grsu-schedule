package by.grsu.schedule.controller;

import by.grsu.schedule.dto.request.TeacherSearchRequestDto;
import by.grsu.schedule.dto.response.TeacherSearchResponseDto;
import by.grsu.schedule.mapper.TeacherMapper;
import by.grsu.schedule.model.criteria.TeacherSearchCriteria;
import by.grsu.schedule.service.TeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TeacherController {
    TeacherService teacherService;
    TeacherMapper teacherMapper;

    @PostMapping("/search")
    public ResponseEntity<TeacherSearchResponseDto> searchTeachers(@RequestBody TeacherSearchRequestDto requestDto,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int rowsPerPage) {
        TeacherSearchCriteria criteria = teacherMapper.toCriteria(requestDto);
        TeacherSearchResponseDto response = teacherService.searchTeachers(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
