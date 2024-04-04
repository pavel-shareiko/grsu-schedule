package by.grsu.schedule.service.schedule;

import by.grsu.schedule.configuration.properties.SchedulePullingProperties;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.service.FacultyService;
import by.grsu.schedule.service.gateway.GrsuApiGateway;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulePullingService {
    SchedulePullingProperties schedulePullingProperties;
    GrsuApiGateway grsuApiGateway;
    FacultyService facultyService;

    @Transactional
    public void pull() {
        List<FacultyDto> faculties = grsuApiGateway.getAllFaculties();
        facultyService.upsert(faculties);
    }
}
