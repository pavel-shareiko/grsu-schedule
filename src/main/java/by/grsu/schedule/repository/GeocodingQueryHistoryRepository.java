package by.grsu.schedule.repository;

import by.grsu.schedule.domain.GeocodingQueryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GeocodingQueryHistoryRepository extends JpaRepository<GeocodingQueryHistoryEntity, UUID> {
    Optional<GeocodingQueryHistoryEntity> findByQueryIgnoreCase(String query);
}
