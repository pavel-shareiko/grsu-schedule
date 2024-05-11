package by.grsu.schedule.api;

import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/analytics-modules-meta")
public interface AnalyticsModuleMetaApi {

    @GetMapping("/{moduleName}")
    ResponseEntity<AnalyticsModuleMeta> performSingle(@PathVariable String moduleName);

}
