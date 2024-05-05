package by.grsu.schedule.repository;

import by.grsu.schedule.domain.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>, JpaSpecificationExecutor<SubjectEntity> {
    Optional<SubjectEntity> findByTitleIgnoreCase(String title);
}
