package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.FacultySearchRequestDto;
import by.grsu.schedule.api.dto.response.FacultySearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/faculties")
public interface FacultyApi {

    @PostMapping("/search")
    ResponseEntity<FacultySearchResponseDto> searchFaculties(@RequestBody FacultySearchRequestDto requestDto,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int rowsPerPage);

}
