package by.grsu.schedule.controller;

import by.grsu.schedule.api.AddressApi;
import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController implements AddressApi {
    private final AddressService addressService;

    @Override
    public List<AddressDto> forceUpdate(List<Long> ids) {
        return addressService.forceUpdate(ids);
    }
}
