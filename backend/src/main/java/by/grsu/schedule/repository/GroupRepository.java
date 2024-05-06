package by.grsu.schedule.repository;

import by.grsu.schedule.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity> {
}
