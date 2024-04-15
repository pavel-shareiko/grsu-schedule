package by.grsu.schedule.repository;

import by.grsu.schedule.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByTitle(String title);
}
