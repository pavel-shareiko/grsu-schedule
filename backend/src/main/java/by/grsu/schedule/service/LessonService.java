package by.grsu.schedule.service;

import by.grsu.schedule.api.dto.LessonDto;
import by.grsu.schedule.api.dto.PaginationDto;
import by.grsu.schedule.api.dto.response.ScheduleSearchResponseDto;
import by.grsu.schedule.domain.GroupEntity;
import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.domain.TeacherEntity;
import by.grsu.schedule.mapper.LessonMapper;
import by.grsu.schedule.mapper.LessonTypeMapper;
import by.grsu.schedule.model.criteria.LessonSearchCriteria;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.GroupRepository;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.LessonTypeRepository;
import by.grsu.schedule.repository.TeacherRepository;
import by.grsu.schedule.repository.specification.LessonSearchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    SubjectService subjectService;
    AddressService addressService;
    LessonTypeService lessonTypeService;
    LessonRepository lessonRepository;
    LessonTypeRepository lessonTypeRepository;
    GroupRepository groupRepository;
    TeacherRepository teacherRepository;
    LessonMapper lessonMapper;
    LessonTypeMapper lessonTypeMapper;

    @Lookup
    public LessonService self() {
        return null;
    }

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
                self().saveAll(lessonsToSave);
                lessonsToSave.clear();
            }
        }

        // Save remaining lessons
        if (!lessonsToSave.isEmpty()) {
            self().saveAll(lessonsToSave);
        }

        log.warn("Following groups are missing and were excluded: {}", missingGroupIds);
        log.warn("Following teachers are missing and were excluded: {}", missingTeacherIds);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<LessonEntity> lessonsToSave) {
        lessonRepository.saveAll(lessonsToSave);
    }

    public ScheduleSearchResponseDto searchLessons(LessonSearchCriteria criteria, int page, int rowsPerPage) {
        LessonSearchSpecification specification = lessonMapper.toSpecification(criteria);

        var lessons = lessonRepository.findAll(specification, PageRequest.of(page, rowsPerPage));
        return new ScheduleSearchResponseDto()
                .setPagination(PaginationDto.of(lessons))
                .setPayload(lessons.getContent().stream()
                        .map(lessonMapper::toDto)
                        .collect(Collectors.toList()));
    }

    private void populateLessonEntity(LessonEntity target, LessonDto source) {
        if (!source.isRemote()) {
            Coordinate addressLocation = addressService.getAddressLocation(source.getAddress());
            source.getAddress().setLocation(addressLocation);
        }
        var address = addressService.getAddressByTitleOrCreateNew(source.getAddress());
        target.setAddress(address);

        var subject = subjectService.getSubjectByTitleOrCreateNew(source.getTitle());
        target.setSubject(subject);

        var lessonType = lessonTypeService.findByTitle(source.getType().getTitle())
                .orElseGet(() -> lessonTypeService.save(lessonTypeMapper.toEntity(source.getType())));
        target.setType(lessonType);
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
