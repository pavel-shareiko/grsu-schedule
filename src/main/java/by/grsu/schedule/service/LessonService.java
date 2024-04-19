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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonService {
    private static final int BATCH_SIZE = 10_000;

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

        // Fetch all groups and teachers in advance
        Set<Long> allGroupIds = lessons.stream()
                .flatMap(lesson -> lesson.getGroupIds().stream())
                .collect(Collectors.toSet());
        Map<Long, GroupEntity> allGroups = groupRepository.findAllById(allGroupIds)
                .stream()
                .collect(Collectors.toMap(GroupEntity::getId, Function.identity()));

        Set<Long> allTeacherIds = lessons.stream()
                .map(LessonDto::getTeacherId)
                .collect(Collectors.toSet());
        Map<Long, TeacherEntity> allTeachers = teacherRepository.findAllById(allTeacherIds)
                .stream()
                .collect(Collectors.toMap(TeacherEntity::getId, Function.identity()));

        for (var lesson : lessons) {
            var lessonEntity = lessonMapper.toEntity(lesson);
            populateLessonEntity(lessonEntity, lesson);
            excludeNonExistingGroups(lessonEntity, allGroups, missingGroupIds);
            excludeNonExistingTeachers(lessonEntity, allTeachers, missingTeacherIds);

            lessonsToSave.add(lessonEntity);

            // Save in batches
            if (lessonsToSave.size() >= BATCH_SIZE) {
                lessonRepository.saveAll(lessonsToSave);
                lessonsToSave.clear();
            }
        }

        // Save remaining lessons
        if (!lessonsToSave.isEmpty()) {
            lessonRepository.saveAll(lessonsToSave);
        }

        log.warn("Following groups are missing and were excluded: {}", missingGroupIds);
        log.warn("Following teachers are missing and were excluded: {}", missingTeacherIds);
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

    private void excludeNonExistingGroups(LessonEntity lessonEntity, Map<Long, GroupEntity> allGroups, Set<Long> missingGroupIds) {
        Set<GroupEntity> lessonGroups = new HashSet<>(lessonEntity.getGroups());
        lessonGroups.removeIf(group -> {
            if (!allGroups.containsKey(group.getId())) {
                missingGroupIds.add(group.getId());
                return true;
            }
            return false;
        });
        lessonEntity.setGroups(lessonGroups);
    }

    private void excludeNonExistingTeachers(LessonEntity lessonEntity, Map<Long, TeacherEntity> allTeachers, Set<Long> missingTeacherIds) {
        Set<TeacherEntity> lessonTeachers = new HashSet<>(lessonEntity.getTeachers());
        lessonTeachers.removeIf(teacher -> {
            if (!allTeachers.containsKey(teacher.getId())) {
                missingTeacherIds.add(teacher.getId());
                return true;
            }
            return false;
        });
        lessonEntity.setTeachers(lessonTeachers);
    }
}
