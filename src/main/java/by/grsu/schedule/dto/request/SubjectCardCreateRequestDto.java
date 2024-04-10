package by.grsu.schedule.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectCardCreateRequestDto {
    Long subjectId;
    String lessonsSequence;
}
