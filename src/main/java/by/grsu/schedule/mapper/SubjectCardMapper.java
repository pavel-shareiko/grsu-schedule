package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.SubjectCardEntity;
import by.grsu.schedule.dto.SubjectCardDto;
import by.grsu.schedule.dto.request.SubjectCardCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubjectCardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject.id", source = "subjectId")
    SubjectCardEntity toEntity(SubjectCardCreateRequestDto dto);

    SubjectCardDto toDto(SubjectCardEntity subjectCard);
}
