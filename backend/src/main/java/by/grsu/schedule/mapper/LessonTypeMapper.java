package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.LessonTypeEntity;
import by.grsu.schedule.api.dto.LessonTypeDto;
import org.mapstruct.Mapper;

@Mapper
public interface LessonTypeMapper {
    LessonTypeEntity toEntity(LessonTypeDto type);
}
