package by.grsu.schedule.service.gateway;

import by.grsu.schedule.configuration.properties.SchedulePullingProperties;
import by.grsu.schedule.dto.FacultyDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static by.grsu.schedule.util.RestUtils.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class GrsuApiGateway {
    RestTemplate restTemplate;
    SchedulePullingProperties pullingProperties;

    public List<FacultyDto> getAllFaculties() {
        ResponseEntity<FacultyDto[]> response = restTemplate.getForEntity(
                buildUri(pullingProperties.getApiUrl() + "/getFaculties"),
                FacultyDto[].class
        );

        if (response.getBody() == null) {
            log.warn("API for receiving faculties returned empty body");
            return List.of();
        }

        return List.of(response.getBody());
    }
}
