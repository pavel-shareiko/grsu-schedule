package by.grsu.schedule.api.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherSearchRequestDto {
    Long id;
    String surname;
    String name;
    String patronym;
    String post;
    String email;
}
