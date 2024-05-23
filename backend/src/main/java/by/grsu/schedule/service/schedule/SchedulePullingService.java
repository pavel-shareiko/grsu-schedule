package by.grsu.schedule.service.schedule;

import by.grsu.schedule.api.dto.DepartmentDto;
import by.grsu.schedule.api.dto.FacultyDto;
import by.grsu.schedule.api.dto.SchedulePullTaskDto;
import by.grsu.schedule.api.dto.SchedulePullTaskDto.PullTaskTriggerDto;
import by.grsu.schedule.domain.SchedulePullTaskEntity;
import by.grsu.schedule.exception.SchedulePullTaskNotFoundException;
import by.grsu.schedule.exception.TaskCancellationException;
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
    private static final List<SchedulePullTaskEntity.SchedulePullStatus> CANCELLABLE_STATUSES = List.of(
            SchedulePullTaskEntity.SchedulePullStatus.PENDING
    );

    GrsuApiGateway grsuApiGateway;
    FacultyService facultyService;
    DepartmentService departmentService;
    GroupService groupService;
    TeacherService teacherService;
    LessonService lessonService;
    SchedulePullTaskEntityRepository schedulePullTaskEntityRepository;
    SchedulePullTaskMapper schedulePullTaskMapper;

    public SchedulePullTaskDto schedulePulling(PullTaskTriggerDto trigger) {
        boolean hasScheduledTasks = hasAnyScheduledOrRunningTasks();
        SchedulePullTaskEntity savedTask = schedulePullTaskEntityRepository.save(createPullingTask(trigger));

        if (hasScheduledTasks) {
            return schedulePullTaskMapper.toDto(savedTask);
        }

        performPullingAsync(savedTask);
        return schedulePullTaskMapper.toDto(savedTask);
    }

    public void failAllStartedTasks() {
        List<SchedulePullTaskEntity> pendingTasks = getAllRunningTasks();
        pendingTasks.forEach(task -> {
            task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.FAILED);
            schedulePullTaskEntityRepository.save(task);
        });
    }

    public void runNextScheduledTask() {
        Optional<SchedulePullTaskEntity> nextTask = schedulePullTaskEntityRepository.findNextScheduledTask();

        if (nextTask.isEmpty()) {
            return;
        }

        performPullingAsync(nextTask.get());
    }

    public SchedulePullTaskDto getLatestSuccessfulResult() {
        return schedulePullTaskEntityRepository.findLatestSuccessfulTask()
                .map(schedulePullTaskMapper::toDto)
                .orElse(null);
    }

    public SchedulePullTaskDto cancelTask(Long taskId) {
        Optional<SchedulePullTaskEntity> task = schedulePullTaskEntityRepository.findById(taskId);

        if (task.isEmpty()) {
            throw new SchedulePullTaskNotFoundException(taskId);
        }

        SchedulePullTaskEntity scheduledTask = task.get();
        if (!CANCELLABLE_STATUSES.contains(scheduledTask.getStatus())) {
            throw new TaskCancellationException(scheduledTask.getStatus());
        }

        scheduledTask.setStatus(SchedulePullTaskEntity.SchedulePullStatus.CANCELLED);
        schedulePullTaskEntityRepository.save(scheduledTask);

        return schedulePullTaskMapper.toDto(scheduledTask);
    }

    private SchedulePullTaskEntity createPullingTask(PullTaskTriggerDto trigger) {
        return new SchedulePullTaskEntity()
                .setStatus(SchedulePullTaskEntity.SchedulePullStatus.PENDING)
                .setTrigger(SchedulePullTaskEntity.PullTaskTrigger.valueOf(trigger.name()));
    }

    private void performPullingAsync(SchedulePullTaskEntity nextTask) {
        CompletableFuture.runAsync(() -> this.performPulling(nextTask));
    }

    public void performPulling(SchedulePullTaskEntity task) {
        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.IN_PROGRESS);
        schedulePullTaskEntityRepository.saveAndFlush(task);

        try {
            var faculties = grsuApiGateway.getAllFaculties();
            facultyService.upsert(faculties);

            var departments = grsuApiGateway.getAllDepartments();
            departmentService.upsert(departments);

            var teachers = grsuApiGateway.getAllTeachers();
            teacherService.upsert(teachers);

            var groups = grsuApiGateway.getAllGroups(
                    faculties.stream().map(FacultyDto::getId).toList(),
                    departments.stream().map(DepartmentDto::getId).toList());
            groupService.upsert(groups);

            var lessons = grsuApiGateway.getAllLessonsForTeachers(teachers);
            lessonService.upsert(lessons);
        } catch (Exception e) {
            failTask(task);
            throw e;
        }

        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.COMPLETED);
        schedulePullTaskEntityRepository.save(task);
    }

    public List<SchedulePullTaskEntity> getAllRunningTasks() {
        return schedulePullTaskEntityRepository.findAllRunningTasks();
    }

    public List<SchedulePullTaskEntity> getAllScheduledOrRunningTasks() {
        return schedulePullTaskEntityRepository.findAllScheduledOrRunningTasks();
    }

    public boolean hasAnyScheduledOrRunningTasks() {
        return !getAllScheduledOrRunningTasks().isEmpty();
    }

    private void failTask(SchedulePullTaskEntity task) {
        task.setStatus(SchedulePullTaskEntity.SchedulePullStatus.FAILED);
        schedulePullTaskEntityRepository.save(task);
    }

    public SchedulePullTaskDto getLatestTask() {
        return schedulePullTaskEntityRepository.findLatestTask()
                .map(schedulePullTaskMapper::toDto)
                .orElse(null);
    }
}
