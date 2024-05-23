package by.grsu.schedule.api.dto.request;

import by.grsu.schedule.api.dto.request.enums.SubjectCardPresenceDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectSearchRequestDto {
    Long id;
    String title;
    SubjectCardPresenceDto subjectCardPresence;
}
