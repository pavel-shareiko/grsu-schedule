package by.grsu.schedule.api.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectCardCreateRequestDto {
    Long subjectId;
    @Pattern(regexp = "^[ЛПБлпб]+$")
    String lessonsSequence;
}
