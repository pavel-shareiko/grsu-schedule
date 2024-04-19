package by.grsu.schedule.service;

import by.grsu.schedule.domain.SubjectEntity;
import by.grsu.schedule.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

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
}
