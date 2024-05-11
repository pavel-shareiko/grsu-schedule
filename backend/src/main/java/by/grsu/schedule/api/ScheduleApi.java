package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.ScheduleSearchRequestDto;
import by.grsu.schedule.api.dto.response.ScheduleSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/schedule")
public interface ScheduleApi {

    @PostMapping("/pull")
    ResponseEntity<Void> pull();

    @PostMapping("/search")
    ResponseEntity<ScheduleSearchResponseDto> search(@RequestBody ScheduleSearchRequestDto request,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size);

}
