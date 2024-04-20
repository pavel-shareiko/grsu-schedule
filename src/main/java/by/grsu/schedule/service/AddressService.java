package by.grsu.schedule.service;

import by.grsu.schedule.domain.AddressEntity;
import by.grsu.schedule.domain.GeocodingQueryHistoryEntity;
import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.mapper.AddressMapper;
import by.grsu.schedule.persistence.Coordinate;
import by.grsu.schedule.repository.AddressRepository;
import by.grsu.schedule.service.gateway.geo.GeoApiGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddressService {
    GeoApiGateway geoApiGateway;
    AddressRepository addressRepository;
    GeocodingQueryHistoryService geocodingQueryHistoryService;
    AddressMapper addressMapper;

    @Lookup
    public AddressService self() {
        return null;
    }

    @Cacheable(value = "addressLocation", key = "#addressQuery.toLowerCase()", unless = "#result == null")
    public Coordinate getAddressLocation(String addressQuery) {
        return geocodingQueryHistoryService.findByQueryIgnoreCase(addressQuery)
                .map(GeocodingQueryHistoryEntity::getLocation)
                .orElseGet(() -> geoApiGateway.getAddressLocation(addressQuery));
    }

    @Cacheable(value = "addressLocation", key = "#address.title?.toLowerCase()", unless = "#result == null")
    public Coordinate getAddressLocation(AddressDto address) {
        if (address.getLocation() == null) {
            return findAddressByTitle(address)
                    .map(AddressEntity::getLocation)
                    .orElseGet(() -> geoApiGateway.getAddressLocation(address));
        }
        return address.getLocation();
    }

    public AddressEntity getAddressByTitleOrCreateNew(AddressDto address) {
        return findAddressByTitle(address).orElseGet(() -> self().saveAddress(address));
    }

    public Optional<AddressEntity> findAddressByTitle(AddressDto address) {
        return addressRepository.findByTitle(address.getTitle());
    }

    @Transactional
    public AddressEntity saveAddress(AddressDto address) {
        return addressRepository.save(addressMapper.toEntity(address));
    }

    @Transactional
    public List<AddressDto> forceUpdate(List<Long> ids) {
        List<AddressEntity> updatedAddresses = addressRepository.findAllById(ids).stream()
                .map(addressMapper::toDto)
                .peek(address -> address.setLocation(geoApiGateway.getAddressLocation(address)))
                .map(addressMapper::toEntity)
                .toList();

        return addressRepository.saveAll(updatedAddresses)
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }
}
