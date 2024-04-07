package by.grsu.schedule.repository;

import by.grsu.schedule.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByTitle(String title);
}
