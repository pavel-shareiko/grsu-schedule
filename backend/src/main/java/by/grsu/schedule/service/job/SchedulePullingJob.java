package by.grsu.schedule.service.job;

import by.grsu.schedule.api.dto.SchedulePullTaskDto;
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
public class SchedulePullingJob {
    SchedulePullingService schedulePullingService;

    @Scheduled(cron = "${grsu.schedule.pullingCron}")
    public void pullSchedules() {
        try {
            log.info("Schedule pulling job started");
            schedulePullingService.pull(SchedulePullTaskDto.PullTaskTriggerDto.SCHEDULED);
            log.info("Schedule pulling job finished");
        } catch (Exception e) {
            log.error("An error occurred during pulling schedule data from api", e);
        }
    }
}
