package by.grsu.schedule.service;

import by.grsu.schedule.domain.GeocodingQueryHistoryEntity;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.GeocodingQueryHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GeocodingQueryHistoryService {
    GeocodingQueryHistoryRepository repository;

    public Optional<GeocodingQueryHistoryEntity> findByQueryIgnoreCase(String addressQuery) {
        if (addressQuery == null) {
            return Optional.empty();
        }
        return repository.findByQueryIgnoreCase(addressQuery.toLowerCase().trim());
    }

    @Transactional
    public GeocodingQueryHistoryEntity saveQueryHistory(String addressQuery, Coordinate coordinate) {
        GeocodingQueryHistoryEntity queryHistory = new GeocodingQueryHistoryEntity();
        queryHistory.setQuery(addressQuery.toLowerCase().trim());
        queryHistory.setLocation(coordinate);
        return repository.save(queryHistory);
    }
}
