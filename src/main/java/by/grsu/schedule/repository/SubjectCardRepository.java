package by.grsu.schedule.repository;

import by.grsu.schedule.domain.SubjectCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectCardRepository extends JpaRepository<SubjectCardEntity, Long> {
}