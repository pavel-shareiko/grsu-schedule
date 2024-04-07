package by.grsu.schedule.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LessonDto {
    Long id;
    LocalDate date;
    LocalTime timeStart;
    LocalTime timeEnd;
    Long teacherId;
    List<Long> groupIds;
    String label;
    LessonTypeDto type;
    String title;
    AddressDto address;
    String room;

    public boolean isRemote() {
        return room.isBlank() || "-".equals(room) || address == null || address.getTitle() == null;
    }
}
