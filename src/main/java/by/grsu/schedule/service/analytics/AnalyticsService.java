package by.grsu.schedule.service.analytics;

import by.grsu.schedule.exception.AnalyticsModuleNotFoundException;
import by.grsu.schedule.model.AnalysisContext;
import by.grsu.schedule.model.AnalysisResult;
import by.grsu.schedule.model.AnalyticsModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsModuleFactory moduleFactory;

    public AnalysisResult perform(String moduleName, Map<String, Object> configuration) {
        AnalysisContext context = new AnalysisContext(configuration);
        AnalyticsModule analyticsModule = moduleFactory.getModuleByName(moduleName);
        if (analyticsModule == null) {
            throw new AnalyticsModuleNotFoundException(moduleName);
        }

        return analyticsModule.analyze(context);
    }
}
