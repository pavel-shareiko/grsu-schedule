package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.exception.analytics.RequiredPropertyMissingException;
import by.grsu.schedule.model.AbstractAnalyticsModule;
import by.grsu.schedule.model.AnalysisContext;
import by.grsu.schedule.model.AnalysisResult;
import by.grsu.schedule.model.ModuleScope;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.specification.LessonSearchSpecification;
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
public class ScheduleWindowsAnalyticsModule extends AbstractAnalyticsModule {
    LessonRepository lessonRepository;

    @Override
    public String getDescription() {
        return "Модуль анализа количества форточек в расписании для группы";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.GROUP, ModuleScope.TEACHER);
    }

    @Override
    protected AnalysisResult perform(AnalysisContext context) {
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
        GroupScheduleWindowsAnalyticsModuleResponse response = GroupScheduleWindowsAnalyticsModuleResponse.builder()
                .windowCount(windowCount)
                .averageWindowsCountPerDay(averageWindowsCountPerDay)
                .daysWithLessons(daysWithLessonsCount)
                .build();

        return AnalysisResult.success(
                this.getName(),
                "Результаты анализа количества форточек в расписании за период [%s, %s]".formatted(
                        specification.getFrom(),
                        specification.getTo()
                ),
                response
        );
    }

    private LessonSearchSpecification buildSpecification(AnalysisContext context) {
        return LessonSearchSpecification.builder()
                .groupId(context.getProperty("groupId", null, Long.class))
                .teacherId(context.getProperty("teacherId", null, Long.class))
                .from(context.getProperty("from", LocalDate.class))
                .to(context.getProperty("to", LocalDate.class))
                .build();
    }

    @Data
    @Builder
    public static class GroupScheduleWindowsAnalyticsModuleResponse {
        private final int windowCount;
        private final double averageWindowsCountPerDay;
        private final double daysWithLessons;
    }
}
