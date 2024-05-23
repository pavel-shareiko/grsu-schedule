package by.grsu.schedule.exception;

import by.grsu.schedule.domain.SchedulePullTaskEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaskCancellationException extends RuntimeException {
    public TaskCancellationException(SchedulePullTaskEntity.SchedulePullStatus status) {
        super("Задача в статусе '" + status + "' не может быть отменена");
    }
}
