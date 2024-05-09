package by.grsu.schedule.service.analytics;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.dto.ShortAnalyticsModuleInfoDto;
import by.grsu.schedule.dto.response.AnalyticsModuleSearchResponseDto;
import by.grsu.schedule.exception.AnalyticsModuleNotFoundException;
import by.grsu.schedule.mapper.AnalysisMapper;
import by.grsu.schedule.mapper.AnalyticsModuleMapper;
import by.grsu.schedule.model.analytics.AnalysisResult;
import by.grsu.schedule.model.analytics.AnalysisStatus;
import by.grsu.schedule.model.analytics.AnalyticsModule;
import by.grsu.schedule.model.analytics.ModuleScope;
import by.grsu.schedule.repository.AnalysisResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.core.ResolvableType;
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
    private final ObjectMapper objectMapper;

    @Lookup
    public AnalyticsService self() {
        return null;
    }

    public AnalysisResult<?> perform(String moduleName, Object configuration) {
        AnalyticsModule analyticsModule = moduleFactory.getModuleByName(moduleName);
        if (analyticsModule == null) {
            throw new AnalyticsModuleNotFoundException(moduleName);
        }

        Object context = convertConfigurationToExpectedType(configuration, analyticsModule);
        AnalysisResult<?> analysisResult = analyticsModule.analyze(context);

        self().saveAnalysisResult(context, analysisResult, analyticsModule);
        return analysisResult;
    }

    private Object convertConfigurationToExpectedType(Object configuration, AnalyticsModule<?, ?> analyticsModule) {
        Class<?> inputType = resolveInputType(analyticsModule);
        return objectMapper.convertValue(configuration, inputType);
    }

    private static Class<?> resolveInputType(AnalyticsModule<?, ?> analyticsModule) {
        Class<?> moduleClass = AopUtils.getTargetClass(analyticsModule);
        ResolvableType resolvableType = ResolvableType.forClass(moduleClass).as(AnalyticsModule.class);
        return resolvableType.getGeneric(0).resolve();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAnalysisResult(Object configuration,
                                   AnalysisResult<?> analysisResult,
                                   AnalyticsModule<?, ?> analyticsModule) {
        try {
            saveAnalysisResult(analysisResult, analyticsModule, configuration);
        } catch (Exception e) {
            log.error("Failed to save analysis result", e);
        }
    }

    private void saveAnalysisResult(AnalysisResult<?> analysisResult, AnalyticsModule<?, ?> analyticsModule, Object context) {
        if (analysisResult.getStatus() == AnalysisStatus.SUCCESS ||
                (analysisResult.getStatus() == AnalysisStatus.ERROR && analyticsModule.saveOnFailure())) {
            AnalysisResultEntity entity = analysisMapper.toEntity(analysisResult, context);
            analysisResultRepository.save(entity);
        }
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
}
