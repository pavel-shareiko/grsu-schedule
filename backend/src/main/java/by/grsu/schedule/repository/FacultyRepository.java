package by.grsu.schedule.repository;

import by.grsu.schedule.domain.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Long> {
}