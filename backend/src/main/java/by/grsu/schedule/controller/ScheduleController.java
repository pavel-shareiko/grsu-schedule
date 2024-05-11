package by.grsu.schedule.controller;

import by.grsu.schedule.api.ScheduleApi;
import by.grsu.schedule.service.schedule.SchedulePullingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {
    private final SchedulePullingService schedulePullingService;

    @Override
    public ResponseEntity<Void> pull() {
        schedulePullingService.pull();
        return ResponseEntity.ok().build();
    }
}
