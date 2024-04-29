package by.grsu.schedule.repository;

import by.grsu.schedule.domain.AnalysisResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResultEntity, Long> {
    @Query(nativeQuery = true,
            value = "SELECT DISTINCT ON (module_name) * " +
                    "FROM analysis_result " +
                    "WHERE module_name IN (:moduleNames) " +
                    "ORDER BY module_name, create_timestamp DESC")
    List<AnalysisResultEntity> findLatestResultsForModuleNames(List<String> moduleNames);

    long countByModuleName(String moduleName);
}