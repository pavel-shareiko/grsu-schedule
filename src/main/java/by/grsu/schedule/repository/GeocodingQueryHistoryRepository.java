package by.grsu.schedule.repository;

import by.grsu.schedule.domain.GeocodingQueryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GeocodingQueryHistoryRepository extends JpaRepository<GeocodingQueryHistory, UUID> {
    Optional<GeocodingQueryHistory> findByQueryIgnoreCase(String query);
}
