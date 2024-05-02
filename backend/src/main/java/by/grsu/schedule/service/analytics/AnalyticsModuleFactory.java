package by.grsu.schedule.service.analytics;

import by.grsu.schedule.model.analytics.AnalyticsModule;
import by.grsu.schedule.model.analytics.ModuleScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AnalyticsModuleFactory {
    private final Map<String, AnalyticsModule<?, ?>> modules;

    public AnalyticsModuleFactory(Map<String, AnalyticsModule<?, ?>> modules) {
        this.modules = modules;
    }

    public Optional<AnalyticsModule<?, ?>> findModuleByName(String moduleName) {
        return Optional.ofNullable(getModuleByName(moduleName));
    }

    public AnalyticsModule<?, ?> getModuleByName(String moduleName) {
        return modules.get(moduleName);
    }

    public List<AnalyticsModule<?, ?>> getModulesByScope(List<ModuleScope> scope) {
        return modules.values().stream()
                .filter(module -> module.getScope().containsAll(scope))
                .toList();
    }
}
