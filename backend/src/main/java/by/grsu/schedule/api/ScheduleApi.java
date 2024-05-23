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
    ResponseEntity<SchedulePullTaskDto> pull();

    @GetMapping("/pull/latest")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<SchedulePullTaskDto> getLatestResult();

    @GetMapping("/pull/latest-successful")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<SchedulePullTaskDto> getLatestSuccessfulResult();

    @PostMapping("/pull/{taskId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<SchedulePullTaskDto> cancelTask(@PathVariable("taskId") Long taskId);

    @PostMapping("/search")
    ResponseEntity<ScheduleSearchResponseDto> search(@RequestBody ScheduleSearchRequestDto request,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "rowsPerPage", defaultValue = "10") int size);

}
