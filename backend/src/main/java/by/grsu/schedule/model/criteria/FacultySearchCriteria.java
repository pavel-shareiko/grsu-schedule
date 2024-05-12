package by.grsu.schedule.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacultySearchCriteria {
    Long id;
    String title;
}
