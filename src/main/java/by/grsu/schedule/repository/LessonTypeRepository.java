package by.grsu.schedule.repository;

import by.grsu.schedule.domain.LessonTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonTypeRepository extends JpaRepository<LessonTypeEntity, Long> {
    Optional<LessonTypeEntity> findByTitle(String title);
}