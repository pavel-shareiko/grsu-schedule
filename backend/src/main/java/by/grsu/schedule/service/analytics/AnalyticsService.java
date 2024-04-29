package by.grsu.schedule.service.analytics;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.dto.ShortAnalyticsModuleInfoDto;
import by.grsu.schedule.dto.response.AnalyticsModuleSearchResponseDto;
import by.grsu.schedule.exception.AnalyticsModuleNotFoundException;
import by.grsu.schedule.mapper.AnalysisMapper;
import by.grsu.schedule.mapper.AnalyticsModuleMapper;
import by.grsu.schedule.model.analytics.*;
import by.grsu.schedule.repository.AnalysisResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsModuleFactory moduleFactory;
    private final AnalyticsModuleMapper analyticsModuleMapper;
    private final AnalysisResultRepository analysisResultRepository;
    private final AnalysisMapper analysisMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AnalysisResult perform(String moduleName, Map<String, Object> configuration) {
        AnalysisContext context = new AnalysisContext(configuration);
        AnalyticsModule analyticsModule = moduleFactory.getModuleByName(moduleName);
        if (analyticsModule == null) {
            throw new AnalyticsModuleNotFoundException(moduleName);
        }

        AnalysisResult analysisResult = analyticsModule.analyze(context);

        try {
            saveAnalysisResults(analysisResult, analyticsModule, context);
        } catch (Exception e) {
            log.error("Failed to save analysis result", e);
        }

        return analysisResult;
    }

    public AnalyticsModuleSearchResponseDto search(List<ModuleScope> scope) {
        List<ShortAnalyticsModuleInfoDto> modules = moduleFactory.getModulesByScope(scope).stream()
                .map(analyticsModuleMapper::map)
                .toList();

        List<String> moduleNames = modules.stream()
                .map(ShortAnalyticsModuleInfoDto::getSystemName)
                .toList();
        Map<String, AnalysisResultEntity> latestResults =
                analysisResultRepository.findLatestResultsForModuleNames(moduleNames)
                        .stream()
                        .collect(Collectors.toMap(AnalysisResultEntity::getModuleName, Function.identity()));

        for (ShortAnalyticsModuleInfoDto module : modules) {
            AnalysisResultEntity latestResult = latestResults.get(module.getSystemName());
            if (latestResult != null) {
                module.setLatestResult(analysisMapper.toDto(latestResult));
            }
            // FIXME: move to projection or view
            long usesCount = analysisResultRepository.countByModuleName(module.getSystemName());
            module.setUsesCount(usesCount);
        }

        return AnalyticsModuleSearchResponseDto.builder()
                .modules(modules)
                .build();
    }

    private void saveAnalysisResults(AnalysisResult analysisResult, AnalyticsModule analyticsModule, AnalysisContext context) {
        if (analysisResult.getStatus() == AnalysisStatus.SUCCESS ||
                (analysisResult.getStatus() == AnalysisStatus.ERROR && analyticsModule.saveOnFailure())) {
            AnalysisResultEntity entity = analysisMapper.toEntity(analysisResult, context.getProperties());
            analysisResultRepository.save(entity);
        }
    }
}
