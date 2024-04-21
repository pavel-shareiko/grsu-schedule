package by.grsu.schedule.model;

import java.util.Set;

public interface AnalyticsModule {

    String getDescription();

    Set<ModuleScope> getScope();

    AnalysisResult analyze(AnalysisContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }

    default boolean saveOnFailure() {
        return false;
    }
}
