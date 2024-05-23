package by.grsu.schedule.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchedulePullTaskDto {
    Long id;
    SchedulePullStatusDto status;
    PullTaskTriggerDto trigger;
    String details;
    OffsetDateTime createTimestamp;
    OffsetDateTime updateTimestamp;
    String createdBy;

    public enum SchedulePullStatusDto {
        PENDING,
        IN_PROGRESS,
        CANCELLED,
        COMPLETED,
        FAILED
    }

    public enum PullTaskTriggerDto {
        MANUAL,
        SCHEDULED
    }
}
