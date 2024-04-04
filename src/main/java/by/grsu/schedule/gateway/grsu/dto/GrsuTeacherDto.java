package by.grsu.schedule.gateway.grsu.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GrsuTeacherDto {
    Long id;
    String name;
    String surname;
    String patronym;
    String post;
    String phone;
    String descr;
    String email;
    String skype;
}
