package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.annotations.FieldMeta;
import by.grsu.schedule.annotations.ResourceEntityReference;
import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.exception.analytics.RequiredPropertyMissingException;
import by.grsu.schedule.model.analytics.AbstractAnalyticsModule;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.specification.LessonSearchSpecification;
import by.grsu.schedule.util.GeoUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RotationsAnalyticsModule extends AbstractAnalyticsModule<
        RotationsAnalyticsModule.Context,
        RotationsAnalyticsModule.Result> {
    LessonRepository lessonRepository;

    @Override
    public String getDisplayName() {
        return "Анализ перемещений между корпусами";
    }

    @Override
    public String getDescription() {
        return "Модуль для анализа перемещений между корпусами";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.GROUP, ModuleScope.TEACHER);
    }

    @Override
    protected AnalysisResult<Result> perform(Context context) {
        Long teacherId = context.getTeacherId();
        Long groupId = context.getGroupId();
        LocalDate from = context.getFrom();
        LocalDate to = context.getTo();

        if (teacherId == null && groupId == null) {
            throw new RequiredPropertyMissingException("teacherId", "groupId");
        }

        // Fetch and sort the lessons
        LessonSearchSpecification specification = buildSpecification(context);
        List<LessonEntity> lessons = lessonRepository.findAll(specification);

        int totalCount = 0;
        double totalDistance = 0.0;
        List<Rotation> rotations = new ArrayList<>();

        // Iterate over the lessons
        for (int i = 0; i < lessons.size() - 1; i++) {
            LessonEntity currentLesson = lessons.get(i);
            LessonEntity nextLesson = lessons.get(i + 1);

            // Calculate the distance
            if (canCalculateDistance(currentLesson, nextLesson)) {
                Coordinate currentLessonLocation = currentLesson.getAddress().getLocation();
                Coordinate nextLessonLocation = nextLesson.getAddress().getLocation();
                Distance distance = currentLessonLocation.distanceTo(nextLessonLocation);

                if (distance.compareTo(GeoUtils.ZERO_DISTANCE) == 0) {
                    continue;
                }

                rotations.add(Rotation.builder()
                        .date(currentLesson.getDate())
                        .fromAddress(currentLesson.getAddress().getTitle())
                        .toAddress(nextLesson.getAddress().getTitle())
                        .distance(distance.getValue())
                        .timeSpent(distance.getValue() / GeoUtils.AVERAGE_WALKING_SPEED_IN_KMH)
                        .build());
                totalDistance += distance.getValue();
                totalCount++;
            }
        }

        double averageDistance = totalCount > 0 ? totalDistance / totalCount : 0;

        // Build the response
        Result result = Result.builder()
                .totalCount(totalCount)
                .totalDistance(totalDistance)
                .averageDistance(averageDistance)
                .rotations(rotations)
                .build();

        return AnalysisResult.success(
                this.getSystemName(),
                "Результаты анализа перемещений между корпусами за период [%s, %s]".formatted(from, to),
                result
        );
    }

    private static boolean canCalculateDistance(LessonEntity currentLesson, LessonEntity nextLesson) {
        return !currentLesson.isRemote() && !nextLesson.isRemote() &&
                currentLesson.getAddress() != null && nextLesson.getAddress() != null &&
                currentLesson.getAddress().getLocation() != null && nextLesson.getAddress().getLocation() != null;
    }

    private LessonSearchSpecification buildSpecification(Context context) {
        return LessonSearchSpecification.builder()
                .groupId(context.getGroupId())
                .teacherId(context.getTeacherId())
                .from(context.getFrom())
                .to(context.getTo())
                .build();
    }

    @Data
    @Builder
    public static class Context {
        @FieldMeta(label = "Преподаватель")
        @ResourceEntityReference(
                url = "/api/v1/teachers/search",
                paramName = "surname",
                displayFormat = "${application.meta.display-formats.general.teacher}"
        )
        private final Long teacherId;

        @FieldMeta(label = "Учебная группа")
        @ResourceEntityReference(
                url = "/api/v1/groups/search",
                paramName = "title",
                displayFormat = "${application.meta.display-formats.general.group}"
        )
        private final Long groupId;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @FieldMeta(label = "Начальная дата")
        private final LocalDate from;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @FieldMeta(label = "Конечная дата")
        private final LocalDate to;
    }

    @Data
    @Builder
    public static class Result {
        private final int totalCount;
        private final double totalDistance;
        private final double averageDistance;
        private final List<Rotation> rotations;
    }

    @Data
    @Builder
    public static class Rotation {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate date;
        private final String fromAddress;
        private final String toAddress;
        private final double distance;
        private final double timeSpent;
    }
}
