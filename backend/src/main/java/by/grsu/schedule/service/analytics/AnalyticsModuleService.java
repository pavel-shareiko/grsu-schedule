package by.grsu.schedule.service.analytics;

import by.grsu.schedule.exception.AnalyticsModuleNotFoundException;
import by.grsu.schedule.model.analytics.AnalyticsModule;
import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;
import by.grsu.schedule.service.analytics.meta.AnalyticsModuleMetaResolver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticsModuleService {
    AnalyticsModuleFactory analyticsModuleFactory;
    AnalyticsModuleMetaResolver metaResolver;

    @Cacheable(cacheNames = "moduleMeta", key = "#moduleName")
    public AnalyticsModuleMeta getModuleMeta(String moduleName) {
        AnalyticsModule<?, ?> analyticsModule = analyticsModuleFactory.findModuleByName(moduleName)
                .orElseThrow(() -> new AnalyticsModuleNotFoundException(moduleName));

        Class<?> moduleClass = AopUtils.getTargetClass(analyticsModule);
        return metaResolver.getModuleMeta(moduleClass, analyticsModule);
    }
}
