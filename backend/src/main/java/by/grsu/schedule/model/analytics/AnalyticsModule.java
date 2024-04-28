package by.grsu.schedule.model.analytics;

import java.util.Set;

public interface AnalyticsModule {

    String getDescription();

    Set<ModuleScope> getScope();

    default String getName() {
        return this.getClass().getSimpleName();
    }

    AnalysisResult analyze(AnalysisContext context);

    default boolean saveOnFailure() {
        return false;
    }
}
