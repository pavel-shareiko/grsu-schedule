package by.grsu.schedule.model.analytics;

import by.grsu.schedule.exception.analytics.AnalysisException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractAnalyticsModule implements AnalyticsModule {
    @Override
    public final AnalysisResult analyze(AnalysisContext context) {
        try {
            return perform(context);
        } catch (AnalysisException e) {
            log.error("An error occurred while analyzing data", e);
            return AnalysisResult.error(this.getName(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unknown error occurred while analyzing data", e);
            throw e;
        }
    }

    protected abstract AnalysisResult perform(AnalysisContext context);
}
