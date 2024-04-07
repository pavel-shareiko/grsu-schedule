package by.grsu.schedule.dto;

import lombok.Data;

@Data
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
}
