package by.grsu.schedule.service;

import by.grsu.schedule.domain.SubjectEntity;
import by.grsu.schedule.dto.PaginationDto;
import by.grsu.schedule.dto.SubjectDto;
import by.grsu.schedule.dto.response.SubjectSearchResponseDto;
import by.grsu.schedule.mapper.SubjectMapper;
import by.grsu.schedule.model.criteria.SubjectSearchCriteria;
import by.grsu.schedule.repository.SubjectRepository;
import by.grsu.schedule.repository.specification.SubjectSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Lookup
    public SubjectService self() {
        return null;
    }

    public SubjectEntity getSubjectByTitleOrCreateNew(String title) {
        return self().findByTitle(title).orElseGet(() -> self().saveSubject(title));
    }

    @Cacheable(value = "subjectByTitle", key = "#title", unless = "#result == null")
    public Optional<SubjectEntity> findByTitle(String title) {
        return subjectRepository.findByTitleIgnoreCase(title.trim());
    }

    @Transactional
    @Cacheable(value = "subjectByTitle", key = "#title")
    public SubjectEntity saveSubject(String title) {
        return subjectRepository.save(SubjectEntity.builder().title(title).build());
    }

    public SubjectSearchResponseDto searchSubjects(SubjectSearchCriteria criteria, int page, int rowsPerPage) {
        SubjectSearchSpecification specification = subjectMapper.toSpecification(criteria);

        Page<SubjectEntity> subjectsPage = subjectRepository.findAll(specification, PageRequest.of(page, rowsPerPage));

        List<SubjectDto> subjectDtos = subjectsPage.stream()
                .map(subjectMapper::toDto)
                .toList();

        return new SubjectSearchResponseDto()
                .setPagination(PaginationDto.of(subjectsPage))
                .setPayload(subjectDtos);
    }
}
