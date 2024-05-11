package by.grsu.schedule.mapper;

import by.grsu.schedule.domain.AddressEntity;
import by.grsu.schedule.api.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
    @Mapping(target = "updateTimestamp", ignore = true)
    @Mapping(target = "createTimestamp", ignore = true)
    AddressEntity toEntity(AddressDto address);

    AddressDto toDto(AddressEntity address);
}
