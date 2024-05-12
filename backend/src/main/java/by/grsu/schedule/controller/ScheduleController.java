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
    public ResponseEntity<Void> pull() {
        schedulePullingService.pull();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SchedulePullTaskDto> getLatestResult() {
        return ResponseEntity.ok(schedulePullingService.getLatestResult());
    }

    @Override
    public ResponseEntity<ScheduleSearchResponseDto> search(ScheduleSearchRequestDto request, int page,
                                                            int rowsPerPage) {
        LessonSearchCriteria criteria = lessonMapper.toCriteria(request);
        ScheduleSearchResponseDto response = lessonService.searchLessons(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
