package by.grsu.schedule.model.criteria;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherSearchCriteria {
    Long id;
    String surname;
    String name;
    String patronym;
    String post;
    String email;
}
