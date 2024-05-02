package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.annotations.FieldMeta;
import by.grsu.schedule.annotations.ResourceEntityReference;
import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.exception.analytics.RequiredPropertyMissingException;
import by.grsu.schedule.model.analytics.AbstractAnalyticsModule;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.specification.LessonSearchSpecification;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduleWindowsAnalyticsModule extends AbstractAnalyticsModule<
        ScheduleWindowsAnalyticsModule.Context,
        ScheduleWindowsAnalyticsModule.Result> {
    LessonRepository lessonRepository;

    @Override
    public String getDisplayName() {
        return "Анализ количества форточек";
    }

    @Override
    public String getDescription() {
        return "Модуль анализа количества форточек в расписании";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.GROUP, ModuleScope.TEACHER);
    }

    @Override
    protected AnalysisResult<Result> perform(Context context) {
        LessonSearchSpecification specification = buildSpecification(context);
        if (specification.getTeacherId() == null && specification.getGroupId() == null) {
            throw new RequiredPropertyMissingException("teacherId", "groupId");
        }

        // Filter out the lessons that are of type "УСР"
        List<LessonEntity> lessons = lessonRepository.findAll(specification).stream()
                .filter(lesson -> !lesson.getType().getTitle().toLowerCase().contains("уср"))
                .toList();

        int windowCount = 0;

        // Iterate over the sorted lessons
        for (int i = 0; i < lessons.size() - 1; i++) {
            LessonEntity currentLesson = lessons.get(i);
            LessonEntity nextLesson = lessons.get(i + 1);

            // Only calculate the difference in minutes for lessons on the same day
            if (currentLesson.getDate().equals(nextLesson.getDate())) {
                // Calculate the difference in minutes between the end of the current lesson and the start of the next lesson
                long diffInMinutes = ChronoUnit.MINUTES.between(currentLesson.getTimeEnd(), nextLesson.getTimeStart());

                // If the difference is more than 31 minutes, increment the counter
                if (diffInMinutes > 31) {
                    windowCount++;
                }
            }
        }

        // Get the unique dates from the lessons list
        Set<LocalDate> uniqueLessonDates = lessons.stream()
                .map(LessonEntity::getDate)
                .collect(Collectors.toSet());

        // Calculate the average windows count per day
        int daysWithLessonsCount = uniqueLessonDates.size();
        double averageWindowsCountPerDay = (double) windowCount / daysWithLessonsCount;

        // Build the response
        Result response = Result.builder()
                .windowCount(windowCount)
                .averageWindowsCountPerDay(averageWindowsCountPerDay)
                .daysWithLessons(daysWithLessonsCount)
                .build();

        return AnalysisResult.success(
                this.getSystemName(),
                "Результаты анализа количества форточек в расписании за период [%s, %s]".formatted(
                        specification.getFrom(),
                        specification.getTo()
                ),
                response
        );
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
        @FieldMeta(label = "Учебная группа")
        @ResourceEntityReference(
                url = "/api/v1/groups/search",
                paramName = "title",
                displayFormat = "${application.meta.display-formats.schedule-windows-analytics-module.group-id}"
        )
        private final Long groupId;
        @ResourceEntityReference(
                url = "/api/v1/teachers/search",
                paramName = "surname",
                displayFormat = "${application.meta.display-formats.schedule-windows-analytics-module.teacher-id}"
        )
        @FieldMeta(label = "Преподаватель")
        private final Long teacherId;
        @NotNull
        @FieldMeta(label = "Начальная дата")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate from;
        @NotNull
        @FieldMeta(label = "Конечная дата")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate to;
    }

    @Data
    @Builder
    public static class Result {
        private final int windowCount;
        private final double averageWindowsCountPerDay;
        private final double daysWithLessons;
    }
}
