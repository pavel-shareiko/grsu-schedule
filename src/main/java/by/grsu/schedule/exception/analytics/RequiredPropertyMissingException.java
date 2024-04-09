package by.grsu.schedule.exception.analytics;

public class RequiredPropertyMissingException extends AnalysisException {
    public RequiredPropertyMissingException(String missingPropertyKey) {
        super("Одно из обязательных свойств не было найдено: " + missingPropertyKey);
    }
}
