package by.grsu.schedule.service.integrity;

import by.grsu.schedule.service.schedule.SchedulePullingService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class StartupTasksCleaner implements ApplicationRunner {
    SchedulePullingService schedulePullingService;

    @Override
    public void run(ApplicationArguments args) {
        schedulePullingService.failAllStartedTasks();
    }
}
