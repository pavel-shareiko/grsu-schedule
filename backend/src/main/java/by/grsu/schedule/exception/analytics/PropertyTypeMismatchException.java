package by.grsu.schedule.exception.analytics;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PropertyTypeMismatchException extends AnalysisException {
    public PropertyTypeMismatchException(String propertyName, Class<?> expectedType) {
        super("Свойство '" + propertyName + "' должно быть типа '" + expectedType.getSimpleName() + "'");
    }
}
