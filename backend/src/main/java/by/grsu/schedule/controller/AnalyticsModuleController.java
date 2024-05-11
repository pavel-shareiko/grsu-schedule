package by.grsu.schedule.controller;

import by.grsu.schedule.api.AnalyticsModuleApi;
import by.grsu.schedule.dto.request.AnalyticsModuleSearchRequestDto;
import by.grsu.schedule.dto.response.AnalyticsModuleSearchResponseDto;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.service.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsModuleController implements AnalyticsModuleApi {
    private final AnalyticsService analyticsService;

    @Override
    public ResponseEntity<AnalysisResult<?>> performSingle(String moduleName,
                                                           Object configuration) {
        AnalysisResult<?> result = analyticsService.perform(moduleName, configuration);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<AnalyticsModuleSearchResponseDto> search(AnalyticsModuleSearchRequestDto request) {
        AnalyticsModuleSearchResponseDto result = analyticsService.search(request.getScope());
        return ResponseEntity.ok(result);
    }
}
