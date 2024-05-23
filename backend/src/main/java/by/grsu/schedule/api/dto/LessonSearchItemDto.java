package by.grsu.schedule.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonSearchItemDto {
    Long id;
    LocalDate date;
    LocalTime timeStart;
    LocalTime timeEnd;
    List<TeacherFullNameDto> teachers;
    List<GroupTitleDto> groups;
    LessonTypeDto type;
    String label;
    String title;
    AddressDto address;
    String room;
   