package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.domain.SubjectCardEntity;
import by.grsu.schedule.exception.analytics.AnalysisTargetNotFoundException;
import by.grsu.schedule.model.analytics.AbstractAnalyticsModule;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.repository.GroupRepository;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.SubjectCardRepository;
import by.grsu.schedule.service.LessonTypeService;
import by.grsu.schedule.util.MathUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupSubjectCardAnalyticsModule extends AbstractAnalyticsModule<
        GroupSubjectCardAnalyticsModule.Context,
        GroupSubjectCardAnalyticsModule.Result> {
    SubjectCardRepository subjectCardRepository;
    GroupRepository groupRepository;
    LessonRepository lessonRepository;
    LessonTypeService lessonTypeService;

    @Override
    protected AnalysisResult<Result> perform(Context context) {
        Long subjectId = context.getSubjectId();
        Long groupId = context.getGroupId();
        LocalDate from = context.getFrom();
        LocalDate to = context.getTo();

        SubjectCardEntity subjectCard = subjectCardRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new AnalysisTargetNotFoundException(subjectId, "Методическая карта"));
        groupRepository.findById(groupId)
                .orElseThrow(() -> new AnalysisTargetNotFoundException(groupId, "Учебная группа"));

        List<LessonEntity> lessons = lessonRepository.findBySubjectIdAndGroupsIdAndDateBetweenOrderByDateAsc(
                subjectId, groupId, from, to);

        String actualSequence = lessonTypeService.getLessonsSequence(lessons);
        String expectedSequence = subjectCard.getLessonsSequence();

        if (actualSequence.length() < expectedSequence.length()) {
            expectedSequence = expectedSequence.substring(0, actualSequence.length());
        }

        int distance = MathUtils.levenshteinDistance(expectedSequence, actualSequence);
        double matchPercentage = MathUtils.calculateMatchPercentage(expectedSequence, actualSequence);
        Result result = Result.builder()
                .levenshteinDistance(distance)
                .matchPercentage(matchPercentage)
                .build();
        return AnalysisResult.success(
                getSystemName(),
                "Результат расчета соответствия расписания заявленному для учебной группы (%s) по предмету %s за период [%s, %s]".formatted(groupId, subjectId, from, to),
                result
        );
    }

    @Override
    public String getDescription() {
        return "Модуль расчета соответствия расписания заявленному для учебной группы";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.GROUP, ModuleScope.SUBJECT);
    }

    @Data
    @Builder
    public static class Context {
        @NotNull
        private final Long subjectId;
        @NotNull
        private final Long groupId;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate from;
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate to;
    }

    @Data
    @Builder
    public static class Result {
        private double matchPercentage;
        private int levenshteinDistance;

    }
}
