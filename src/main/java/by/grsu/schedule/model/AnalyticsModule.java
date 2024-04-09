package by.grsu.schedule.model;

public interface AnalyticsModule {

    String getDescription();

    ModuleScope getScope();

    default String getName() {
        return this.getClass().getSimpleName();
    }

    AnalysisResult analyze(AnalysisContext context);
}
