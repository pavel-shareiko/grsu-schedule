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
    List<SchedulePullTaskEntity> findAllScheduledOrRunningTasks();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM schedule_pull_task WHERE status = 'PENDING' ORDER BY update_timestamp LIMIT 1"
    )
    Optional<SchedulePullTaskEntity> findNextScheduledTask();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM schedule_pull_task WHERE status = 'IN_PROGRESS'"
    )
    List<SchedulePullTaskEntity> findAllRunningTasks();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM schedule_pull_task WHERE status = 'COMPLETED' ORDER BY update_timestamp DESC LIMIT 1"
    )
    Optional<SchedulePullTaskEntity> findLatestSuccessfulTask();
}