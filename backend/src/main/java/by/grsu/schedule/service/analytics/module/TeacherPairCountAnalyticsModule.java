package by.grsu.schedule.service.analytics.module;

import by.grsu.schedule.annotations.FieldMeta;
import by.grsu.schedule.exception.analytics.AnalysisTargetNotFoundException;
import by.grsu.schedule.model.analytics.AbstractAnalyticsModule;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
public class TeacherPairCountAnalyticsModule extends AbstractAnalyticsModule<
        TeacherPairCountAnalyticsModule.Context,
        TeacherPairCountAnalyticsModule.Result> {
    public static final int MAX_ALLOWED_LESSONS_COUNT_PER_DAY = 4;

    private final TeacherRepository teacherRepository;

    @Override
    public String getDisplayName() {
        return "Анализ количества пар преподавателя";
    }

    @Override
    public String getDescription() {
        return "Модуль для анализа количества пар у преподавателя в день";
    }

    @Override
    public Set<ModuleScope> getScope() {
        return Set.of(ModuleScope.TEACHER);
    }

    @Override
    protected AnalysisResult<Result> perform(Context context) {
        Long teacherId = context.getTeacherId();
        LocalDate from = context.getFrom();
        LocalDate to = context.getTo();

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
                .filter(entry -> entry.pairCount > context.getMaxAllowedPairsCount())
                .collect(Collectors.toList());

        Result response = Result.builder()
                .teacherId(teacherId)
                .averagePairCount(averagePairCount)
                .maxPairCount(maxPairCount)
                .minPairCount(minPairCount)
                .violations(violations)
                .build();

        return AnalysisResult.success(
                this.getSystemName(),
                "Результаты анализа количества пар преподавателя за период [%s, %s]".formatted(from, to),
                response
        );
    }

    @Data
    @Builder
    public static class Context {
        @NotNull
        private final Long teacherId;
        @FieldMeta(label = "Максимальное разрешенное количество пар в день")
        private final Integer maxAllowedPairsCount = MAX_ALLOWED_LESSONS_COUNT_PER_DAY;
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
