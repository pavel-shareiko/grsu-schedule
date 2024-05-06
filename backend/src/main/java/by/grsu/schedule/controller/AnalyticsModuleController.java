package by.grsu.schedule.controller;

import by.grsu.schedule.dto.request.AnalyticsModuleSearchRequestDto;
import by.grsu.schedule.dto.response.AnalyticsModuleSearchResponseDto;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.service.analytics.AnalyticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics-modules")
@RequiredArgsConstructor
public class AnalyticsModuleController {
    private final AnalyticsService analyticsService;

    @PostMapping("/{moduleName}/perform")
    public ResponseEntity<AnalysisResult<?>> performSingle(@PathVariable String moduleName,
                                                           @RequestBody Object configuration) {
        AnalysisResult<?> result = analyticsService.perform(moduleName, configuration);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    public ResponseEntity<AnalyticsModuleSearchResponseDto> search(@RequestBody @Valid AnalyticsModuleSearchRequestDto request) {
        AnalyticsModuleSearchResponseDto result = analyticsService.search(request.getScope());
        return ResponseEntity.ok(result);
    }
}
