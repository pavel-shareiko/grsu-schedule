package by.grsu.schedule.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/schedule")
public interface ScheduleApi {

    @PostMapping("/pull")
    ResponseEntity<Void> pull();

}
