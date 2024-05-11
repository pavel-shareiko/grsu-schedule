package by.grsu.schedule.controller;

import by.grsu.schedule.api.AnalysisResultApi;
import by.grsu.schedule.api.dto.request.AnalysisResultSearchRequestDto;
import by.grsu.schedule.api.dto.response.AnalysisResultSearchResponseDto;
import by.grsu.schedule.mapper.AnalysisResultMapper;
import by.grsu.schedule.model.criteria.AnalysisResultSearchCriteria;
import by.grsu.schedule.service.analytics.AnalysisResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalysisResultController implements AnalysisResultApi {
    private final AnalysisResultService analysisResultService;
    private final AnalysisResultMapper analysisResultMapper;

    @Override
    public ResponseEntity<AnalysisResultSearchResponseDto> searchAnalysisResults(
            AnalysisResultSearchRequestDto requestDto,
            int page,
            int rowsPerPage) {
        AnalysisResultSearchCriteria criteria = analysisResultMapper.toCriteria(requestDto);
        AnalysisResultSearchResponseDto response = analysisResultService.searchResults(criteria, page, rowsPerPage);
        return ResponseEntity.ok(response);
    }
}
