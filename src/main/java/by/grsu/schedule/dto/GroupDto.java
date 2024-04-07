package by.grsu.schedule.dto;

import lombok.Data;

@Data
public class GroupDto {
    Long id;
    String title;
    Long facultyId;
    Long departmentId;
}
