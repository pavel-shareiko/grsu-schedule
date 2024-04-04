package by.grsu.schedule.service;

import by.grsu.schedule.domain.Faculty;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.mapper.FacultyMapper;
import by.grsu.schedule.repository.FacultyRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacultyService {
    FacultyRepository facultyRepository;
    FacultyMapper facultyMapper;

    @Transactional
    public void upsert(List<FacultyDto> faculties) {
        Map<Long, FacultyDto> facultyExternalIdToFaculty = faculties.stream()
                .collect(Collectors.toMap(FacultyDto::getId, f -> f)); // faculties from api do not have extId set

        List<Faculty> facultiesForUpdate = facultyRepository.findAllByExternalIdIn(facultyExternalIdToFaculty.keySet());
        List<Faculty> updatedFaculties = updateExistingFaculties(facultiesForUpdate, facultyExternalIdToFaculty);
        facultyRepository.saveAll(updatedFaculties);
    }

    private List<Faculty> updateExistingFaculties(
            List<Faculty> facultiesForUpdate,
            Map<Long, FacultyDto> facultyExternalIdToFaculty) {
        List<Faculty> result = new ArrayList<>();
        for (var faculty : facultiesForUpdate) {
            if (!facultyExternalIdToFaculty.containsKey(faculty.getExternalId())) {
                continue;
            }

            FacultyDto newFaculty = facultyExternalIdToFaculty.get(faculty.getExternalId());
            Faculty updatedFaculty = facultyMapper.merge(faculty, newFaculty);
            result.add(updatedFaculty);
        }
        return result;
    }
}
