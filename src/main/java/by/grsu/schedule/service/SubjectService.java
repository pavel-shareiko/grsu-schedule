package by.grsu.schedule.service;

import by.grsu.schedule.domain.SubjectEntity;
import by.grsu.schedule.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional
    public SubjectEntity getSubjectByTitleOrCreateNew(String title) {
        return subjectRepository.findByTitleIgnoreCase(title.trim())
                .orElseGet(() -> subjectRepository.save(SubjectEntity.builder().title(title).build()));
    }
}
