package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.Address;
import by.grsu.schedule.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
    @Mapping(target = "updateTimestamp", ignore = true)
    @Mapping(target = "createTimestamp", ignore = true)
    Address toEntity(AddressDto address);
}
