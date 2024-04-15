package by.grsu.schedule.repository;

import by.grsu.schedule.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
