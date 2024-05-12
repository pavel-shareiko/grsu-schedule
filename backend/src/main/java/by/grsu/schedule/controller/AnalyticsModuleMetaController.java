package by.grsu.schedule.controller;

import by.grsu.schedule.api.AnalyticsModuleMetaApi;
import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;
import by.grsu.schedule.service.analytics.AnalyticsModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsModuleMetaController implements AnalyticsModuleMetaApi {
    private final AnalyticsModuleService service;

    @Override
    public ResponseEntity<AnalyticsModuleMeta> performSingle(String moduleName) {
        AnalyticsModuleMeta meta = service.getModuleMeta(moduleName);
        return ResponseEntity.ok(meta);
    }
}
