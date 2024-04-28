package by.grsu.schedule.model.analytics;

import java.util.Set;

public interface AnalyticsModule {

    String getDescription();

    Set<ModuleScope> getScope();

    default String getSystemName() {
        return this.getClass().getSimpleName();
    }

    default String getDisplayName() {
        return getSystemName();
    }

    AnalysisResult analyze(AnalysisContext context);

    default boolean saveOnFailure() {
        return false;
    }
}
