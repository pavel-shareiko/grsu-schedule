package by.grsu.schedule.repository;

import by.grsu.schedule.domain.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonTypeRepository extends JpaRepository<LessonType, Long> {
    Optional<LessonType> findByTitle(String title);
}