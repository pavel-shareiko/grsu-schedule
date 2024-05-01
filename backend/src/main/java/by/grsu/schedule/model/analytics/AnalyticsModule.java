package by.grsu.schedule.model.analytics;

import java.util.Set;

public interface AnalyticsModule<I, O> {

    String getDescription();

    Set<ModuleScope> getScope();

    default String getDisplayName() {
        return getSystemName();
    }

    default String getSystemName() {
        return this.getClass().getSimpleName();
    }

    AnalysisResult<O> analyze(I input);

    default boolean saveOnFailure() {
        return true;
    }
}
