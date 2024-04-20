package by.grsu.schedule.repository;

import by.grsu.schedule.domain.AnalysisResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResultEntity, Long> {
}