package by.grsu.schedule.repository;

import by.grsu.schedule.domain.SubjectCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectCardRepository extends JpaRepository<SubjectCard, Long> {
}