package by.grsu.schedule.controller;

import by.grsu.schedule.service.schedule.SchedulePullingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class ScheduleController {
    SchedulePullingService schedulePullingService;

    @PostMapping("/pull")
    public ResponseEntity<Void> pull() {
        log.info("Request to manually update schedule");
        schedulePullingService.pull();
        return ResponseEntity.ok().build();
    }
}
