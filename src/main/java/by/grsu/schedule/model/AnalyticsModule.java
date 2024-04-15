package by.grsu.schedule.model;

public interface AnalyticsModule {

    String getDescription();

    ModuleScope getScope();

    AnalysisResult analyze(AnalysisContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }

    default boolean saveOnFailure() {
        return false;
    }
}
