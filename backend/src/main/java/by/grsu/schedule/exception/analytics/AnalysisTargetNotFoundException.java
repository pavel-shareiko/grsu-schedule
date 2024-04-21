package by.grsu.schedule.exception.analytics;

import lombok.Getter;

@Getter
public class AnalysisTargetNotFoundException extends AnalysisException {
    private final Object id;
    private final String target;

    public AnalysisTargetNotFoundException(Object id, String target) {
        super("Цель для анализа (%s) с id = %s не найдена".formatted(target, id));
        this.id = id;
        this.target = target;
    }
}
