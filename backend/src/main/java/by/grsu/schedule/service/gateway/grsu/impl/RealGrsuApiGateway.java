package by.grsu.schedule.service.gateway.grsu.impl;

import by.grsu.schedule.aop.annotation.Loggable;
import by.grsu.schedule.api.dto.*;
import by.grsu.schedule.configuration.properties.GrsuApiProperties;
import by.grsu.schedule.configuration.properties.SchedulePullingProperties;
import by.grsu.schedule.mapper.*;
import by.grsu.schedule.service.gateway.grsu.GrsuApiGateway;
import by.grsu.schedule.service.gateway.grsu.dto.*;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static by.grsu.schedule.util.RestUtils.buildUri;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.services.grsu.api.enabled", havingValue = "true")
public class RealGrsuApiGateway implements GrsuApiGateway {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int MIN_COURSE = 1;
    private static final int MAX_COURSE = 5;

    private final SchedulePullingProperties schedulePullingProperties;
    private final RestTemplate restTemplate;
    private final GrsuApiProperties apiProperties;
    private final FacultyMapper facultyMapper;
    private final DepartmentMapper departmentMapper;
    private final GroupMapper groupMapper;
    private final TeacherMapper teacherMapper;
    private final LessonMapper lessonMapper;

    @Override
    public List<FacultyDto> getAllFaculties() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getFaculties"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuFacultyDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving faculties returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(facultyMapper::toDto)
                .toList();
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getDepartments"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuDepartmentDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving departments returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(departmentMapper::toDto)
                .toList();
    }

    @Override
    public List<GroupDto> getAllGroups(List<Long> facultyIds, List<Long> departmentIds) {
        List<GroupDto> groups = new ArrayList<>();

        // TODO: тянуть не все?
        for (int course = MIN_COURSE; course <= MAX_COURSE; course++) {
            for (var facultyId : facultyIds) {
                for (var departmentId : departmentIds) {
                    Map<String, String> requestParams = Map.of(
                            "facultyId", String.valueOf(facultyId),
                            "departmentId", String.valueOf(departmentId),
                            "course", String.valueOf(course)
                    );
                    var response = restTemplate.exchange(
                            buildRequestUrl("/getGroups", requestParams),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuGroupDto>>>() {
                            }
                    );

                    if (response.getBody() == null || response.getBody().getItems() == null) {
                        log.warn("API for receiving groups returned empty body with parameters: {}", requestParams);
                        continue;
                    }

                    groups.addAll(response.getBody().getItems().stream()
                            .map(grsuGroupDto -> groupMapper.toDto(grsuGroupDto, facultyId, departmentId))
                            .toList());
                }
            }
        }

        return groups;
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getTeachers"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuTeacherDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving teachers returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    @Override
    @Loggable
    public List<LessonDto> getAllLessonsForTeachers(List<TeacherDto> teachers) {
        ConcurrentLinkedQueue<LessonDto> lessons = new ConcurrentLinkedQueue<>();
        Pair<String, String> dateRange = getSchedulePullingDateRange();

        int numberOfThreads = schedulePullingProperties.getParallelism();
        @Cleanup ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        int partitionSize = (int) Math.ceil((double) teachers.size() / numberOfThreads);
        List<List<TeacherDto>> partitions = IntStream.range(0, numberOfThreads)
                .mapToObj(i -> teachers.subList(i * partitionSize, Math.min((i + 1) * partitionSize, teachers.size())))
                .toList();

        List<Future<?>> futures = partitions.stream()
                .map(partition -> executorService.submit(() -> {
                    for (TeacherDto teacher : partition) {
                        Map<String, String> requestParams = Map.of(
                                "teacherId", String.valueOf(teacher.getId()),
                                "dateStart", dateRange.getLeft(),
                                "dateEnd", dateRange.getRight()
                        );
                        var response = restTemplate.exchange(
                                buildRequestUrl("/getTeacherSchedule", requestParams),
                                HttpMethod.GET,
                                null,
                                GrsuTeacherScheduleResponseDto.class
                        );

                        if (response.getBody() == null || response.getBody() == null) {
                            log.warn("API for receiving lessons for teacher with id {} returned empty body", teacher.getId());
                            return;
                        }

                        GrsuTeacherScheduleResponseDto teacherSchedule = response.getBody();
                        teacherSchedule.getDays().forEach(day -> day.getLessons().stream()
                                .map(lesson -> lessonMapper.toDto(lesson, day.getDate(), teacher.getId()))
                                .forEach(lessons::add));
                    }
                }))
                .collect(Collectors.toList());

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error occurred while executing thread", e);
            }
        });

        return new ArrayList<>(lessons);
    }

    private Pair<String, String> getSchedulePullingDateRange() {
        OffsetDateTime from = OffsetDateTime.now().plusDays(schedulePullingProperties.getStartDateOffset());
        OffsetDateTime to = OffsetDateTime.now().plusDays(schedulePullingProperties.getEndDateOffset());

        return Pair.of(from.format(DATE_FORMATTER), to.format(DATE_FORMATTER));
    }

    private URI buildRequestUrl(String path) {
        return buildUri(apiProperties.getUrl() + path, Map.of("lang", apiProperties.getLangKey()));
    }

    private URI buildRequestUrl(String path, Map<String, String> params) {
        if (params == null) {
            return buildUri(apiProperties.getUrl() + path);
        }

        Map<String, String> finalParams = new HashMap<>(params);
        finalParams.put("lang", apiProperties.getLangKey());

        return buildUri(apiProperties.getUrl() + path, finalParams);
    }
}
