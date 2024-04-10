package by.grsu.schedule.service;

import by.grsu.schedule.domain.Subject;
import by.grsu.schedule.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional
    public Subject getSubjectByTitleOrCreateNew(String title) {
        return subjectRepository.findByTitleIgnoreCase(title.trim())
                .orElseGet(() -> subjectRepository.save(Subject.builder().title(title).build()));
    }
}
