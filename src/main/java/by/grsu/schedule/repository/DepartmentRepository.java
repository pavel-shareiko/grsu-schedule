package by.grsu.schedule.repository;

import by.grsu.schedule.domain.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}