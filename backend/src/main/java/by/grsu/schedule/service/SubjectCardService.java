package by.grsu.schedule.service;

import by.grsu.schedule.domain.SubjectCardEntity;
import by.grsu.schedule.api.dto.SubjectCardDto;
import by.grsu.schedule.api.dto.request.SubjectCardCreateRequestDto;
import by.grsu.schedule.exception.SubjectNotFoundException;
import by.grsu.schedule.mapper.SubjectCardMapper;
import by.grsu.schedule.repository.SubjectCardRepository;
import by.grsu.schedule.repository.SubjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectCardService {
    SubjectRepository subjectRepository;
    SubjectCardRepository repository;
    SubjectCardMapper subjectCardMapper;

    @Transactional
    public SubjectCardDto saveOrUpdate(SubjectCardCreateRequestDto subjectCardDto) {
        subjectRepository.findById(subjectCardDto.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundException(subjectCardDto.getSubjectId()));

        Optional<SubjectCardEntity> existingCardOptional = repository.findBySubjectId(subjectCardDto.getSubjectId());
        if (existingCardOptional.isPresent()) {
            SubjectCardEntity existingCard = existingCardOptional.get();
            existingCard.setLessonsSequence(subjectCardDto.getLessonsSequence());
            return subjectCardMapper.toDto(repository.save(existingCard));
        }

        SubjectCardEntity subjectCard = repository.save(subjectCardMapper.toEntity(subjectCardDto));
        return subjectCardMapper.toDto(subjectCard);
    }

    @Transactional
    public void deleteBySubjectId(Long subjectId) {
        repository.deleteBySubjectId(subjectId);
    }
}
