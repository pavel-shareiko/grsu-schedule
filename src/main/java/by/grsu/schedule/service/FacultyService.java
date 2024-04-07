package by.grsu.schedule.service;

import by.grsu.schedule.domain.Faculty;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.mapper.FacultyMapper;
import by.grsu.schedule.repository.FacultyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FacultyService {
    FacultyRepository facultyRepository;
    FacultyMapper facultyMapper;

    @Transactional
    public void upsert(List<FacultyDto> faculties) {
        List<Faculty> facultiesToSave = faculties.stream()
                .map(facultyMapper::toEntity)
                .toList();

        facultyRepository.saveAll(facultiesToSave);
    }
}
