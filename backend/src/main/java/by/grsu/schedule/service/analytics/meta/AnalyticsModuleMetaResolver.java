package by.grsu.schedule.service.analytics.meta;

import by.grsu.schedule.model.analytics.AnalyticsModule;
import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;

public interface AnalyticsModuleMetaResolver {
    AnalyticsModuleMeta getModuleMeta(Class<?> type, AnalyticsModule<?, ?> instance);
}
