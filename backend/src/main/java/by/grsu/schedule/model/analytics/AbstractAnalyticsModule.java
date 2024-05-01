package by.grsu.schedule.model.analytics;

import by.grsu.schedule.exception.analytics.AnalysisException;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Set;

@Slf4j
public abstract class AbstractAnalyticsModule<I, O> implements AnalyticsModule<I, O> {
    @Override
    public final AnalysisResult<O> analyze(I input) {
        validateInput(input);

        try {
            return perform(input);
        } catch (AnalysisException e) {
            log.error("An error occurred while analyzing data", e);
            return AnalysisResult.error(this.getSystemName(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unknown error occurred while analyzing data", e);
            throw e;
        }
    }

    protected void validateInput(I input) {
        Validator validator = getValidator();
        Set<ConstraintViolation<I>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return new SpringValidatorAdapter(validator);
    }

    protected abstract AnalysisResult<O> perform(I input);
}
