package by.grsu.schedule.repository;

import by.grsu.schedule.domain.SchedulePullTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchedulePullTaskEntityRepository extends JpaRepository<SchedulePullTaskEntity, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM schedule_pull_task ORDER BY update_timestamp DESC LIMIT 1"
    )
    Optional<SchedulePullTaskEntity> findLatestTask();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM schedule_pull_task WHERE status = 'PENDING' or status = 'IN_PROGRESS'"
    )
    List<SchedulePullTaskEntity> findAllStartedTasks();
}