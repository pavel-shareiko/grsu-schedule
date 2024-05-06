package by.grsu.schedule.service.analytics;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.dto.AnalysisResultDto;
import by.grsu.schedule.dto.PaginationDto;
import by.grsu.schedule.dto.response.AnalysisResultSearchResponseDto;
import by.grsu.schedule.mapper.AnalysisResultMapper;
import by.grsu.schedule.model.criteria.AnalysisResultSearchCriteria;
import by.grsu.schedule.repository.AnalysisResultRepository;
import by.grsu.schedule.repository.specification.AnalysisResultSearchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalysisResultService {
    AnalysisResultRepository analysisResultRepository;
    AnalysisResultMapper analysisResultMapper;

    public AnalysisResultSearchResponseDto searchResults(AnalysisResultSearchCriteria criteria, int page, int rowsPerPage) {
        AnalysisResultSearchSpecification specification = analysisResultMapper.toSpecification(criteria);

        Page<AnalysisResultEntity> analysisResultsPage =
                analysisResultRepository.findAll(specification, PageRequest.of(page, rowsPerPage));

        List<AnalysisResultDto> analysisResultDtos = analysisResultsPage.stream()
                .map(analysisResultMapper::toDto)
                .toList();

        return new AnalysisResultSearchResponseDto()
                .setPagination(PaginationDto.of(analysisResultsPage))
                .setPayload(analysisResultDtos);
    }
}
