package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.domain.SubjectCardEntity;
import by.grsu.schedule.exception.analytics.AnalysisTargetNotFoundException;
import by.grsu.schedule.model.AbstractAnalyticsModule;
import by.grsu.schedule.model.AnalysisContext;
import by.grsu.schedule.model.AnalysisResult;
import by.grsu.schedule.model.ModuleScope;
import by.grsu.schedule.repository.GroupRepository;
import by.grsu.schedule.repository.LessonRepository;
import by.grsu.schedule.repository.SubjectCardRepository;
import by.grsu.schedule.service.LessonTypeService;
import by.grsu.schedule.util.MathUtils;
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
public class GroupSubjectCardAnalyticsModule extends AbstractAnalyticsModule {
    SubjectCardRepository subjectCardRepository;
    GroupRepository groupRepository;
    LessonRepository lessonRepository;
    LessonTypeService lessonTypeService;

    @Override
    protected AnalysisResult perform(AnalysisContext context) {
        Long subjectId = context.getProperty("subjectId", Long.class);
        Long groupId = context.getProperty("groupId", Long.class);
        LocalDate from = context.getProperty("from", LocalDate.class);
        LocalDate to = context.getProperty("to", LocalDate.class);

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
        GroupSubjectCardAnalyticsModuleResponse result = GroupSubjectCardAnalyticsModuleResponse.builder()
                .levenshteinDistance(distance)
                .matchPercentage(matchPercentage)
                .build();
        return AnalysisResult.success(
                getName(),
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
    public static class GroupSubjectCardAnalyticsModuleResponse {
        double matchPercentage;
        int levenshteinDistance;
    }
}