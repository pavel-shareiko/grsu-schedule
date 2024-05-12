package by.grsu.schedule.mapper;

import by.grsu.schedule.api.dto.SchedulePullTaskDto;
import by.grsu.schedule.domain.SchedulePullTaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SchedulePullTaskMapper {
    SchedulePullTaskDto toDto(SchedulePullTaskEntity schedulePullTaskEntity);
}
