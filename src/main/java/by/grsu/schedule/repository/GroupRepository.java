package by.grsu.schedule.repository;

import by.grsu.schedule.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
