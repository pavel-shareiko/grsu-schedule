package by.grsu.schedule.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectCardDto {
    Long id;
    String lessonsSequence;
}
