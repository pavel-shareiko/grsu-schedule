package by.grsu.schedule.repository;

import by.grsu.schedule.domain.SubjectCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectCardRepository extends JpaRepository<SubjectCardEntity, Long> {
    Optional<SubjectCardEntity> findBySubjectId(Long subjectId);

    void deleteBySubjectId(Long subjectId);
}