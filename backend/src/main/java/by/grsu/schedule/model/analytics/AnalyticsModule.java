package by.grsu.schedule.model.analytics;

import java.util.Set;

public interface AnalyticsModule<I, O> {

    String getDescription();

    Set<ModuleScope> getScope();

    default String getDisplayName() {
        return getSystemName();
    }

    default String getSystemName() {
        String className = this.getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    AnalysisResult<O> analyze(I input);

    default boolean saveOnFailure() {
        return true;
    }
}
