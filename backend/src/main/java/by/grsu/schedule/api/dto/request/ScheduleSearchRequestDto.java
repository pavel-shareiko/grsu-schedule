package by.grsu.schedule.api.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleSearchRequestDto {
    Long teacherId;
    Long groupId;
}
