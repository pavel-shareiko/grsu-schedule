package by.grsu.schedule.api;

import by.grsu.schedule.dto.request.TeacherSearchRequestDto;
import by.grsu.schedule.dto.response.TeacherSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/teachers")
public interface TeacherApi {

    @PostMapping("/search")
    ResponseEntity<TeacherSearchResponseDto> searchTeachers(@RequestBody TeacherSearchRequestDto requestDto,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int rowsPerPage);

}
