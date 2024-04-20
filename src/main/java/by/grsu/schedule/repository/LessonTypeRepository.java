package by.grsu.schedule.repository;

import by.grsu.schedule.domain.LessonTypeEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonTypeRepository extends JpaRepository<LessonTypeEntity, Long> {
    @Cacheable(value = "lessonTypeByTitle", key = "#a0", unless = "#result == null")
    Optional<LessonTypeEntity> findByTitle(String title);
}