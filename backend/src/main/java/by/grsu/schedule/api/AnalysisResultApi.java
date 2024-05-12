package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.request.AnalysisResultSearchRequestDto;
import by.grsu.schedule.api.dto.response.AnalysisResultSearchResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/analysis-results")
public interface AnalysisResultApi {

    @PostMapping("/search")
    ResponseEntity<AnalysisResultSearchResponseDto> searchAnalysisResults(
            @RequestBody AnalysisResultSearchRequestDto requestDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int rowsPerPage);

}
