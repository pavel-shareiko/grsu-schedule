package by.grsu.schedule.controller;

import by.grsu.schedule.dto.AddressDto;
import by.grsu.schedule.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddressController {
    AddressService addressService;

    @PatchMapping("/force")
    public List<AddressDto> forceUpdate(@RequestBody List<Long> ids) {
        return addressService.forceUpdate(ids);
    }
}
