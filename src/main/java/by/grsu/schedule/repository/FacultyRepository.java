package by.grsu.schedule.repository;

import by.grsu.schedule.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByExternalIdIn(Collection<Long> externalIds);
}