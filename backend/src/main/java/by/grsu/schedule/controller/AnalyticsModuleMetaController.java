package by.grsu.schedule.controller;

import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;
import by.grsu.schedule.service.analytics.AnalyticsModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics-modules-meta")
@RequiredArgsConstructor
public class AnalyticsModuleMetaController {
    private final AnalyticsModuleService service;

    @GetMapping("/{moduleName}")
    public ResponseEntity<AnalyticsModuleMeta> performSingle(@PathVariable String moduleName) {
        AnalyticsModuleMeta meta = service.getModuleMeta(moduleName);
        return ResponseEntity.ok(meta);
    }
}
