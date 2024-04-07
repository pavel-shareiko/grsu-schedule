package by.grsu.schedule.service;

import by.grsu.schedule.domain.Group;
import by.grsu.schedule.domain.Lesson;
import by.grsu.schedule.domain.Teacher;
import by.grsu.schedule.dto.LessonDto;
import by.grsu.schedule.mapper.LessonMapper;
import by.grsu.schedule.mapper.LessonTypeMapper;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.GroupRepository;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.LessonTypeRepository;
import by.grsu.schedule.repository.TeacherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonService {
    AddressService addressService;
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    LessonTypeRepository lessonTypeRepository;
    LessonTypeMapper lessonTypeMapper;
    GroupRepository groupRepository;
    TeacherRepository teacherRepository;

    @Transactional
    public void upsert(List<LessonDto> lessons) {
        Set<Long> missingGroupIds = new HashSet<>();
        Set<Long> missingTeacherIds = new HashSet<>();
        List<Lesson> lessonsToSave = new ArrayList<>();
        for (var lesson : lessons) {
            var lessonEntity = lessonMapper.toEntity(lesson);
            if (!lesson.isRemote()) {
                Coordinate addressLocation = addressService.getAddressLocation(lesson.getAddress());
                lesson.getAddress().setLocation(addressLocation);
            }
            var address = addressService.save(lesson.getAddress());
            lessonEntity.setAddress(address);

            var lessonType = lessonTypeRepository.findByTitle(lesson.getType().getTitle())
                    .orElseGet(() -> lessonTypeRepository.save(lessonTypeMapper.toEntity(lesson.getType())));

            lessonEntity.setType(lessonType);

            missingGroupIds.addAll(excludeNonExistingGroups(lessonEntity));
            missingTeacherIds.addAll(excludeNonExistingTeachers(lessonEntity));

            lessonsToSave.add(lessonEntity);
        }

        log.warn("Following groups are missing and were excluded: {}", missingGroupIds);
        log.warn("Following teachers are missing and were excluded: {}", missingTeacherIds);

        lessonRepository.saveAll(lessonsToSave);
    }

    private List<Long> excludeNonExistingGroups(Lesson lessonEntity) {
        Set<Group> lessonGroups = lessonEntity.getGroups();
        List<Long> lessonGroupIds = new ArrayList<>(lessonGroups.stream()
                .map(Group::getId)
                .toList());
        List<Group> existingGroups = groupRepository.findAllById(lessonGroupIds);

        if (existingGroups.size() != lessonGroupIds.size()) {
            List<Long> existingGroupIds = existingGroups.stream()
                    .map(Group::getId)
                    .toList();
            lessonGroupIds.removeAll(existingGroupIds);

            log.warn("Groups with ids {} do not exist", lessonGroupIds);
            lessonGroups.removeIf(group -> lessonGroupIds.contains(group.getId()));
        }

        return lessonGroupIds;
    }

    private List<Long> excludeNonExistingTeachers(Lesson lessonEntity) {
        Set<Teacher> lessonTeachers = lessonEntity.getTeachers();
        List<Long> teacherIds = new ArrayList<>(lessonTeachers.stream()
                .map(Teacher::getId)
                .toList());
        List<Teacher> teachers = teacherRepository.findAllById(teacherIds);

        if (teachers.size() != teacherIds.size()) {
            List<Long> existingTeacherIds = teachers.stream()
                    .map(Teacher::getId)
                    .toList();
            teacherIds.removeAll(existingTeacherIds);

            log.warn("Teachers with ids {} do not exist", teacherIds);
            lessonTeachers.removeIf(teacher -> teacherIds.contains(teacher.getId()));
        }

        return teacherIds;
    }
}
