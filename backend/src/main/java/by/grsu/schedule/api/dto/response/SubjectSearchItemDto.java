package by.grsu.schedule.api.dto.response;

import by.grsu.schedule.api.dto.SubjectCardDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectSearchItemDto {
    Long id;
    String title;
    OffsetDateTime createTimestamp;
    SubjectCardDto subjectCard;
}
