package by.grsu.schedule.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.support.CronTrigger;

@ConfigurationProperties("application.schedule.pulling")
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchedulePullingProperties {
    /**
     * Cron expression representing the schedule of the data collection.
     */
    CronTrigger cron;

    /**
     * Integer representing the number of days to shift the start date
     * of the schedule data collection relative to the current date.
     */
    Integer startDateOffset;

    /**
     * Integer representing the number of days to shift the end date
     * of the schedule data collection relative to the current date.
     */
    Integer endDateOffset;

    /**
     * Integer representing the number of parallel threads to use when pulling the schedule data.
     */
    Integer parallelism;
}
