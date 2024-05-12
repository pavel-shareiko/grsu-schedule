package by.grsu.schedule.service;

import by.grsu.schedule.api.dto.FacultyDto;
import by.grsu.schedule.api.dto.FacultySearchItemDto;
import by.grsu.schedule.api.dto.PaginationDto;
import by.grsu.schedule.api.dto.response.FacultySearchResponseDto;
import by.grsu.schedule.domain.FacultyEntity;
import by.grsu.schedule.mapper.FacultyMapper;
import by.grsu.schedule.model.criteria.FacultySearchCriteria;
import by.grsu.schedule.repository.FacultyRepository;
import by.grsu.schedule.repository.specification.FacultySearchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        List<FacultyEntity> facultiesToSave = faculties.stream()
                .map(facultyMapper::toEntity)
                .toList();

        facultyRepository.saveAll(facultiesToSave);
    }

    public FacultySearchResponseDto searchFaculties(FacultySearchCriteria criteria, int pageIndex, int rowsPerPage) {
        FacultySearchSpecification specification = facultyMapper.toSpecification(criteria);
        Page<FacultyEntity> page = facultyRepository.findAll(specification, PageRequest.of(pageIndex, rowsPerPage));

        List<FacultySearchItemDto> faculties = page.getContent().stream()
                .map(facultyMapper::toDto)
                .toList();

        return new FacultySearchResponseDto()
                .setPayload(faculties)
                .setPagination(PaginationDto.of(page));
    }
}
