package by.grsu.schedule.controller;

import by.grsu.schedule.api.ScheduleApi;
import by.grsu.schedule.api.dto.SchedulePullTaskDto;
import by.grsu.schedule.api.dto.request.ScheduleSearchRequestDto;
import by.grsu.schedule.api.dto.response.ScheduleSearchResponseDto;
import by.grsu.schedule.mapper.LessonMapper;
import by.grsu.schedule.model.criteria.LessonSearchCriteria;
import by.grsu.schedule.service.LessonService;
import by.grsu.schedule.service.schedule.SchedulePullingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {
    private final SchedulePullingService schedulePullingService;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @Override
    public ResponseEntity<SchedulePullTaskDto> pull() {
        SchedulePullTaskDto task = schedulePullingService.schedulePulling(SchedulePullTaskDto.PullTaskTriggerDto.MANUAL);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<SchedulePullTaskDto> getLatestResult() {
        return ResponseEntity.ok(schedulePullingService.getLatestTask());
    }

    @Override
    public ResponseEntity<SchedulePullTaskDto> getLatestSuccessfulResult() {
        return ResponseEntity.ok(schedulePullingService.getLatestSuccessfulResult());
    }

    @Override
    public ResponseEntity<SchedulePullTaskDto> cancelTask(Long taskId) {
        return ResponseEntity.ok(schedulePullingService.cancelTask(taskId));
    }

    @Override
    public ResponseEntity<ScheduleSearchResponseDto> search(ScheduleSearchRequestDto request, int page,
                                                            int rowsPerPage) {
        LessonSearchCriteria criteria = lessonMapper.toCriteria(request);
        ScheduleSearchResponseDto response = lessonService.searchLessons(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
