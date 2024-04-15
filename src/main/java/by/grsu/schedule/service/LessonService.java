package by.grsu.schedule.service;

import by.grsu.schedule.domain.GroupEntity;
import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.domain.TeacherEntity;
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
    SubjectService subjectService;

    @Transactional
    public void upsert(List<LessonDto> lessons) {
        Set<Long> missingGroupIds = new HashSet<>();
        Set<Long> missingTeacherIds = new HashSet<>();
        List<LessonEntity> lessonsToSave = new ArrayList<>();
        for (var lesson : lessons) {
            var lessonEntity = lessonMapper.toEntity(lesson);
            populateLessonEntity(lessonEntity, lesson);

            missingGroupIds.addAll(excludeNonExistingGroups(lessonEntity));
            missingTeacherIds.addAll(excludeNonExistingTeachers(lessonEntity));

            lessonsToSave.add(lessonEntity);
        }

        log.warn("Following groups are missing and were excluded: {}", missingGroupIds);
        log.warn("Following teachers are missing and were excluded: {}", missingTeacherIds);

        lessonRepository.saveAll(lessonsToSave);
    }

    private void populateLessonEntity(LessonEntity lessonEntity, LessonDto source) {
        if (!source.isRemote()) {
            Coordinate addressLocation = addressService.getAddressLocation(source.getAddress());
            source.getAddress().setLocation(addressLocation);
        }
        var address = addressService.save(source.getAddress());
        lessonEntity.setAddress(address);

        var subject = subjectService.getSubjectByTitleOrCreateNew(source.getTitle());
        lessonEntity.setSubject(subject);

        var lessonType = lessonTypeRepository.findByTitle(source.getType().getTitle())
                .orElseGet(() -> lessonTypeRepository.save(lessonTypeMapper.toEntity(source.getType())));
        lessonEntity.setType(lessonType);
    }

    private List<Long> excludeNonExistingGroups(LessonEntity lessonEntity) {
        Set<GroupEntity> lessonGroups = lessonEntity.getGroups();
        List<Long> lessonGroupIds = new ArrayList<>(lessonGroups.stream()
                .map(GroupEntity::getId)
                .toList());
        List<GroupEntity> existingGroups = groupRepository.findAllById(lessonGroupIds);

        if (existingGroups.size() != lessonGroupIds.size()) {
            List<Long> existingGroupIds = existingGroups.stream()
                    .map(GroupEntity::getId)
                    .toList();
            lessonGroupIds.removeAll(existingGroupIds);

            log.warn("Groups with ids {} do not exist", lessonGroupIds);
            lessonGroups.removeIf(group -> lessonGroupIds.contains(group.getId()));
        }

        return lessonGroupIds;
    }

    private List<Long> excludeNonExistingTeachers(LessonEntity lessonEntity) {
        Set<TeacherEntity> lessonTeachers = lessonEntity.getTeachers();
        List<Long> teacherIds = new ArrayList<>(lessonTeachers.stream()
                .map(TeacherEntity::getId)
                .toList());
        List<TeacherEntity> teachers = teacherRepository.findAllById(teacherIds);

        if (teachers.size() != teacherIds.size()) {
            List<Long> existingTeacherIds = teachers.stream()
                    .map(TeacherEntity::getId)
                    .toList();
            teacherIds.removeAll(existingTeacherIds);

            log.warn("Teachers with ids {} do not exist", teacherIds);
            lessonTeachers.removeIf(teacher -> teacherIds.contains(teacher.getId()));
        }

        return teacherIds;
    }
}
