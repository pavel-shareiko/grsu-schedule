package by.grsu.schedule.service.gateway.grsu.dto;

import lombok.Data;

@Data
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
