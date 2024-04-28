package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.exception.analytics.AnalysisTargetNotFoundException;
import by.grsu.schedule.model.analytics.AbstractAnalyticsModule;
import by.grsu.schedule.model.analytics.AnalysisContext;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeacherPairCountAnalyticsModule extends AbstractAnalyticsModule {
    public static final String TEACHER_ID = "teacherId";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final int MAX_ALLOWED_LESSONS_COUNT_PER_DAY = 4;

    private final TeacherRepository teacherRepository;

    @Override
    public String getDescription() {
        return "Модуль для анализа количества пар у преподавателя в день";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.TEACHER);
    }

    @Override
    protected AnalysisResult perform(AnalysisContext context) {
        Long teacherId = context.getProperty(TEACHER_ID, Long.class);
        LocalDate from = context.getProperty(FROM, LocalDate.class);
        LocalDate to = context.getProperty(TO, LocalDate.class);

        teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AnalysisTargetNotFoundException(teacherId, "Преподаватель"));

        List<Object[]> pairCountsByDate = teacherRepository.findLessonCountsByDate(teacherId, from, to);
        Map<LocalDate, Long> pairCountByDate = pairCountsByDate.stream()
                .collect(Collectors.toMap(
                        data -> (LocalDate) data[0],
                        data -> (Long) data[1]
                ));

        double averagePairCount = pairCountByDate.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);

        int maxPairCount = pairCountByDate.values().stream()
                .mapToInt(Long::intValue)
                .max()
                .orElse(0);

        int minPairCount = pairCountByDate.values().stream()
                .mapToInt(Long::intValue)
                .min()
                .orElse(0);

        List<PairCountByDate> violations = pairCountByDate.entrySet().stream()
                .map(entry -> PairCountByDate.builder()
                        .date(entry.getKey())
                        .pairCount(entry.getValue().intValue())
                        .build())
                .filter(entry -> entry.pairCount > MAX_ALLOWED_LESSONS_COUNT_PER_DAY)
                .collect(Collectors.toList());

        TeacherPairCountAnalyticsModuleResponse response = TeacherPairCountAnalyticsModuleResponse.builder()
                .teacherId(teacherId)
                .averagePairCount(averagePairCount)
                .maxPairCount(maxPairCount)
                .minPairCount(minPairCount)
                .violations(violations)
                .build();

        return AnalysisResult.success(
                this.getName(),
                "Результаты анализа количества пар преподавателя за период [%s, %s]".formatted(from, to),
                response
        );
    }

    @Data
    @Builder
    public static class TeacherPairCountAnalyticsModuleResponse {
        private final Long teacherId;
        private final double averagePairCount;
        private final int maxPairCount;
        private final int minPairCount;
        private final List<PairCountByDate> violations;

    }

    @Data
    @Builder
    public static class PairCountByDate {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate date;
        private final int pairCount;
    }
}
