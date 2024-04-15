package by.grsu.schedule.model;

import by.grsu.schedule.exception.analytics.AnalysisException;

public abstract class AbstractAnalyticsModule implements AnalyticsModule {
    @Override
    public final AnalysisResult analyze(AnalysisContext context) {
        try {
            return perform(context);
        } catch (AnalysisException e) {
            return AnalysisResult.error(this.getName(), e.getMessage(), e);
        } catch (Exception e) {
            return AnalysisResult.error(this.getName(), "Во время выполнения анализа произошла неизвестная ошибка", e);
        }
    }

    protected abstract AnalysisResult perform(AnalysisContext context);
}
