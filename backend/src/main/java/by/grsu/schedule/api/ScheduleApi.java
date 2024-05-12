package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.SchedulePullTaskDto;
import by.grsu.schedule.api.dto.request.ScheduleSearchRequestDto;
import by.grsu.schedule.api.dto.response.ScheduleSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/schedule")
public interface ScheduleApi {

    @PostMapping("/pull")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> pull();

    @GetMapping("/pull/latest")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<SchedulePullTaskDto> getLatestResult();

    @PostMapping("/search")
    ResponseEntity<ScheduleSearchResponseDto> search(@RequestBody ScheduleSearchRequestDto request,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "rowsPerPage", defaultValue = "10") int size);

}
