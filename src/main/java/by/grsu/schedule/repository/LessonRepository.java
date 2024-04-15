package by.grsu.schedule.repository;

import by.grsu.schedule.domain.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
}