package by.grsu.schedule.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonSearchCriteria {
    Long teacherId;
    Long groupId;
    LocalDate from;
    LocalDate to;
}
