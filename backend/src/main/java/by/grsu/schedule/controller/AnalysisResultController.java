package by.grsu.schedule.controller;

import by.grsu.schedule.dto.request.AnalysisResultSearchRequestDto;
import by.grsu.schedule.dto.response.AnalysisResultSearchResponseDto;
import by.grsu.schedule.mapper.AnalysisResultMapper;
import by.grsu.schedule.model.criteria.AnalysisResultSearchCriteria;
import by.grsu.schedule.service.analytics.AnalysisResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analysis-results")
@RequiredArgsConstructor
public class AnalysisResultController {
    private final AnalysisResultService analysisResultService;
    private final AnalysisResultMapper analysisResultMapper;

    @PostMapping("/search")
    public ResponseEntity<AnalysisResultSearchResponseDto> searchAnalysisResults(
            @RequestBody AnalysisResultSearchRequestDto requestDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int rowsPerPage) {
        AnalysisResultSearchCriteria criteria = analysisResultMapper.toCriteria(requestDto);
        AnalysisResultSearchResponseDto response = analysisResultService.searchResults(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
