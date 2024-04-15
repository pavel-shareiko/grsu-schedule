package by.grsu.schedule.service.analytics;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.exception.AnalyticsModuleNotFoundException;
import by.grsu.schedule.mapper.AnalysisMapper;
import by.grsu.schedule.model.AnalysisContext;
import by.grsu.schedule.model.AnalysisResult;
import by.grsu.schedule.model.AnalysisStatus;
import by.grsu.schedule.model.AnalyticsModule;
import by.grsu.schedule.repository.AnalysisResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsModuleFactory moduleFactory;
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

    private void saveAnalysisResults(AnalysisResult analysisResult, AnalyticsModule analyticsModule, AnalysisContext context) {
        if (analysisResult.getStatus() == AnalysisStatus.SUCCESS ||
                (analysisResult.getStatus() == AnalysisStatus.ERROR && analyticsModule.saveOnFailure())) {
            AnalysisResultEntity entity = analysisMapper.toEntity(analysisResult, context.getProperties());
            analysisResultRepository.save(entity);
        }
    }
}
