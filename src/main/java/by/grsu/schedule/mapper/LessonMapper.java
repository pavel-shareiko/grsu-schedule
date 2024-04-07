package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Group;
import by.grsu.schedule.domain.Lesson;
import by.grsu.schedule.domain.Teacher;
import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.dto.LessonDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuQualifiedGroupDto;
import by.grsu.schedule.gateway.grsu.dto.GrsuTeacherLessonDto;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface LessonMapper {
    @Mapping(target = "groupIds", source = "lesson.groups", qualifiedByName = "mapGroupIds")
    @Mapping(target = "type.title", source = "lesson.type")
    @Mapping(target = "address.title", source = "lesson.address")
    LessonDto toDto(GrsuTeacherLessonDto lesson, LocalDate date, Long teacherId);

    @Named("mapGroupIds")
    default List<Long> mapGroupIds(List<GrsuQualifiedGroupDto> groups) {
        return groups.stream()
                .map(GrsuQualifiedGroupDto::getId)
                .toList();
    }

    @AfterMapping
    default void fillAddress(@MappingTarget AddressDto address, GrsuTeacherLessonDto source) {
        address.setCountry("Беларусь");
        address.setCity("Гродно");

        String addressTitle = source.getAddress();
        if (addressTitle.isBlank() || "-".equals(addressTitle)) {
            return;
        }

        String[] addressParts = addressTitle.split(",");
        if (addressParts.length == 2) {
            address.setStreet(addressParts[0].trim());
            address.setHouse(addressParts[1].trim());
        }
    }

    List<Lesson> toEntity(List<LessonDto> lessons);

    @Mapping(target = "createTimestamp", ignore = true)
    @Mapping(target = "updateTimestamp", ignore = true)
    @Mapping(target = "teachers", source = "teacherId", qualifiedByName = "mapTeacher")
    @Mapping(target = "groups", source = "groupIds", qualifiedByName = "mapGroup")
    Lesson toEntity(LessonDto lessons);

    @Named("mapTeacher")
    default Set<Teacher> mapTeacher(Long teacherId) {
        return Set.of(new Teacher().setId(teacherId));
    }

    @Named("mapGroup")
    default Set<Group> mapGroup(List<Long> groupIds) {
        return groupIds.stream()
                .map(id -> new Group().setId(id))
                .collect(Collectors.toSet());
    }
}
