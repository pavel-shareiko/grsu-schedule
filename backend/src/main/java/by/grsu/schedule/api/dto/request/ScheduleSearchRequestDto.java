package by.grsu.schedule.api.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleSearchRequestDto {
    Long teacherId;
    Long groupId;
    LocalDate from;
    LocalDate to;
}
