package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.AnalyticsModuleSearchRequestDto;
import by.grsu.schedule.api.dto.response.AnalyticsModuleSearchResponseDto;
import by.grsu.schedule.model.analytics.AnalysisResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/analytics-modules")
public interface AnalyticsModuleApi {

    @PostMapping("/{moduleName}/perform")
    ResponseEntity<AnalysisResult<?>> performSingle(@PathVariable String moduleName,
                                                    @RequestBody Object configuration);

    @PostMapping("/search")
    ResponseEntity<AnalyticsModuleSearchResponseDto> search(@RequestBody @Valid AnalyticsModuleSearchRequestDto request);

}
