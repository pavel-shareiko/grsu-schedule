package by.grsu.schedule.exception.analytics;

import java.util.Arrays;

public class RequiredPropertyMissingException extends AnalysisException {
    public RequiredPropertyMissingException(String missingPropertyKey) {
        super("Одно из обязательных свойств не было найдено: " + missingPropertyKey);
    }

    public RequiredPropertyMissingException(String... missingPropertyKeys) {
        super("Одно из обязательных свойств не было найдено: " + Arrays.toString(missingPropertyKeys));
    }
}
