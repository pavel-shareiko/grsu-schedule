package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.LessonType;
import by.grsu.schedule.dto.LessonTypeDto;
import org.mapstruct.Mapper;

@Mapper
public interface LessonTypeMapper {
    LessonType toEntity(LessonTypeDto type);
}
