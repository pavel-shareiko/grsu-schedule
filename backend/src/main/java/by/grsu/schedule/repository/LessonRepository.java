package by.grsu.schedule.repository;

import by.grsu.schedule.domain.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity, Long>, JpaSpecificationExecutor<LessonEntity> {
    List<LessonEntity> findBySubjectIdAndGroupsIdAndDateBetweenOrderByDateAsc(
            Long subjectId,
            Long groupId,
            LocalDate dateStart,
            LocalDate dateEnd);
}