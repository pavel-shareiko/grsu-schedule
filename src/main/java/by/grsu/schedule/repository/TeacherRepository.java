package by.grsu.schedule.repository;

import by.grsu.schedule.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT l.date, COUNT(l) " +
            "FROM Lesson l " +
            "JOIN l.teachers t " +
            "WHERE t.id = :teacherId AND l.date BETWEEN :from AND :to " +
            "GROUP BY l.date")
    List<Object[]> findLessonCountsByDate(@Param("teacherId") Long teacherId, @Param("from") LocalDate from, @Param("to") LocalDate to);
}
