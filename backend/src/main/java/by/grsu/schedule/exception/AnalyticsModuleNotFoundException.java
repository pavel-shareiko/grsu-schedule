package by.grsu.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnalyticsModuleNotFoundException extends RuntimeException {
    public AnalyticsModuleNotFoundException(String moduleName) {
        super("Модуль аналитики с названием '" + moduleName + "' не найден");
    }
}
