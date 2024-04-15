package by.grsu.schedule.service.analytics;

import by.grsu.schedule.model.AnalyticsModule;
import by.grsu.schedule.model.ModuleScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AnalyticsModuleFactory {
    private final Map<String, AnalyticsModule> modules;

    public AnalyticsModuleFactory(Map<String, AnalyticsModule> modules) {
        this.modules = modules;
    }

    public AnalyticsModule getModuleByName(String moduleName) {
        return modules.get(moduleName);
    }

    public List<AnalyticsModule> getModuleByScope(ModuleScope moduleScope) {
        return modules.values().stream()
                .filter(module -> module.getScope().contains(moduleScope))
                .toList();
    }
}
