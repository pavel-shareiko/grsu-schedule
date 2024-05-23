package by.grsu.schedule.service.job;

import by.grsu.schedule.service.schedule.SchedulePullingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SchedulePullTaskRunnerJob {
    SchedulePullingService schedulePullingService;

    @Scheduled(cron = "${application.job.scheduled-task-runner.cron}")
    public void runScheduledTask() {
        try {
            log.info("Schedule pulling task runner job started");
            schedulePullingService.runNextScheduledTask();
            log.info("Schedule pulling task runner job finished");
        } catch (Exception e) {
            log.error("An error occurred during pulling schedule data from api", e);
        }
    }
}