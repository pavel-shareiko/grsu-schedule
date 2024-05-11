package by.grsu.schedule.api;

import by.grsu.schedule.dto.AddressDto;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/addresses")
public interface AddressApi {

    @PatchMapping("/force")
    List<AddressDto> forceUpdate(@RequestBody List<Long> ids);

}
