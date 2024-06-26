package by.grsu.schedule.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherDto {
    Long id;
    String name;
    String surname;
    String patronym;
    String post;
    String phone;
    String descr;
    String email;
    String skype;
    OffsetDateTime createTimestamp;
}
