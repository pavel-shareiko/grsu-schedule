package by.grsu.schedule.service;

import by.grsu.schedule.domain.AddressEntity;
import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.mapper.AddressMapper;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.AddressRepository;
import by.grsu.schedule.service.gateway.geo.GeoApiGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddressService {
    GeoApiGateway geoApiGateway;
    AddressRepository addressRepository;
    AddressMapper addressMapper;

    @Cacheable(value = "addressLocation", key = "#address.title", unless = "#result == null")
    public Coordinate getAddressLocation(AddressDto address) {
        if (address.getLocation() == null) {
            return addressRepository.findByTitle(address.getTitle())
                    .map(AddressEntity::getLocation)
                    .orElseGet(() -> geoApiGateway.getAddressLocation(address));
        }
        return null;
    }

    @Transactional
    public AddressEntity save(AddressDto address) {
        return addressRepository.findByTitle(address.getTitle())
                .orElseGet(() -> addressRepository.save(addressMapper.toEntity(address)));
    }
}
