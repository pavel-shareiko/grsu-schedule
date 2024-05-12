package by.grsu.schedule.service.schedule;

import by.grsu.schedule.api.dto.*;
import by.grsu.schedule.api.dto.SchedulePullTaskDto.PullTaskTriggerDto;
import by.grsu.schedule.domain.SchedulePullTaskEntity;
import by.grsu.schedule.mapper.SchedulePullTaskMapper;
import by.grsu.schedule.repository.SchedulePullTaskEntityRepository;
import by.grsu.schedule.service.*;
import by.grsu.schedule.service.gateway.grsu.GrsuApiGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SchedulePullingService {
    GrsuApiGateway grsuApiGateway;
    FacultyService facultyService;
    DepartmentService departmentService;
    GroupService groupService;
    TeacherService teacherService;
    LessonService lessonService;
    SchedulePullTaskEntityRepository schedulePullTaskEntityRepository;
    SchedulePullTaskMapper schedulePullTaskMapper;

    public SchedulePullTaskDto pull(PullTaskTriggerDto trigger) {
        validateNoTasksRunning();

        SchedulePullTaskEntity savedTask = schedulePullTaskEntityRepository.save(createPullingTask(trigger));
        CompletableFuture.runAsync(() -> this.performPulling(savedTask));

        return schedulePullTaskMapper.toDto(savedTask);
    }

    public void failAllStartedTasks() {
        List<SchedulePullTaskEntity> pendingTasks = schedulePullTaskEntityRepository.findAllStartedTasks();
        pendingTasks.forEach(task -> {
            task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.FAILED);
            schedulePullTaskEntityRepository.save(task);
        });
    }

    private SchedulePullTaskEntity createPullingTask(PullTaskTriggerDto trigger) {
        return new SchedulePullTaskEntity()
                .setStatus(SchedulePullTaskEntity.SchedulePullStatus.PENDING)
                .setTrigger(SchedulePullTaskEntity.PullTaskTrigger.valueOf(trigger.name()));
    }

    private void performPulling(SchedulePullTaskEntity task) {
        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.IN_PROGRESS);
        schedulePullTaskEntityRepository.save(task);

        List<FacultyDto> faculties;
        try {
            faculties = grsuApiGateway.getAllFaculties();
            facultyService.upsert(faculties);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        List<DepartmentDto> departments;
        try {
            departments = grsuApiGateway.getAllDepartments();
            departmentService.upsert(departments);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        List<TeacherDto> teachers;
        try {
            teachers = grsuApiGateway.getAllTeachers();
            teacherService.upsert(teachers);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        try {
            List<GroupDto> groups = grsuApiGateway.getAllGroups(
                    faculties.stream().map(FacultyDto::getId).toList(),
                    departments.stream().map(DepartmentDto::getId).toList());
            groupService.upsert(groups);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        try {
            List<LessonDto> lessons = grsuApiGateway.getAllLessonsForTeachers(teachers);
            lessonService.upsert(lessons);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.COMPLETED);
        schedulePullTaskEntityRepository.save(task);
    }

    private void failTask(SchedulePullTaskEntity task) {
        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.FAILED);
        schedulePullTaskEntityRepository.save(task);
    }

    private void validateNoTasksRunning() {
        Optional<SchedulePullTaskEntity> latestTask = schedulePullTaskEntityRepository.findLatestTask();
        if (latestTask.isEmpty()) {
            return;
        }

        SchedulePullTaskEntity.SchedulePullStatus status = latestTask.get().getStatus();
        if (status == SchedulePullTaskEntity.SchedulePullStatus.IN_PROGRESS || status == SchedulePullTaskEntity.SchedulePullStatus.PENDING) {
            throw new IllegalStateException("Another task is already running");
        }
    }

    public SchedulePullTaskDto getLatestResult() {
        return schedulePullTaskEntityRepository.findLatestTask()
                .map(schedulePullTaskMapper::toDto)
                .orElse(null);
    }
}
