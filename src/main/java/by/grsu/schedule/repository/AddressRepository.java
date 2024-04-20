package by.grsu.schedule.repository;

import by.grsu.schedule.domain.AddressEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Cacheable(value = "addressByTitle", key = "#a0", unless = "#result == null")
    Optional<AddressEntity> findByTitle(String title);
}
