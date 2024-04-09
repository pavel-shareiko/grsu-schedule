package by.grsu.schedule.controller;

import by.grsu.schedule.model.AnalysisResult;
import by.grsu.schedule.service.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @PostMapping("/{moduleName}/perform")
    public ResponseEntity<AnalysisResult> performSingle(@PathVariable String moduleName,
                                                        @RequestBody Map<String, Object> configuration) {
        AnalysisResult result = analyticsService.perform(moduleName, configuration);
        return ResponseEntity.ok(result);
    }
}
