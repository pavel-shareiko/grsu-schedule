package by.grsu.schedule.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectSearchCriteria {
    Long id;
    String title;
    SubjectCardPresence subjectCardPresence;
}
