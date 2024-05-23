package by.grsu.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SchedulePullTaskNotFoundException extends RuntimeException {
    public SchedulePullTaskNotFoundException(Long taskId) {
        super("Schedule pull task with id " + taskId + " not found");
    }
}
