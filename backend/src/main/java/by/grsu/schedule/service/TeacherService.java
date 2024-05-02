package by.grsu.schedule.service;

import by.grsu.schedule.domain.TeacherEntity;
import by.grsu.schedule.dto.PaginationDto;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.dto.response.TeacherSearchResponseDto;
import by.grsu.schedule.mapper.TeacherMapper;
import by.grsu.schedule.model.TeacherSearchCriteria;
import by.grsu.schedule.repository.TeacherRepository;
import by.grsu.schedule.repository.specification.TeacherSearchSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TeacherService {
    TeacherMapper teacherMapper;
    TeacherRepository teacherRepository;

    @Transactional
    public void upsert(List<TeacherDto> teachers) {
        List<TeacherEntity> grsuTeachers = teachers
                .stream()
                .map(teacherMapper::toEntity)
                .toList();

        teacherRepository.saveAll(grsuTeachers);
    }

    public TeacherSearchResponseDto searchTeachers(TeacherSearchCriteria criteria, int page, int rowsPerPage) {
        TeacherSearchSpecification specification = teacherMapper.toSpecification(criteria);
        Page<TeacherEntity> teachersPage = teacherRepository.findAll(specification, PageRequest.of(page, rowsPerPage));

        List<TeacherDto> teacherDtos = teachersPage
                .stream()
                .map(teacherMapper::toDto)
                .toList();

        return new TeacherSearchResponseDto()
                .setPayload(teacherDtos)
                .setPagination(
                        PaginationDto.builder()
                                .page(page)
                                .rowsPerPage(rowsPerPage)
                                .totalPages(teachersPage.getTotalPages())
                                .totalElements(teachersPage.getTotalElements())
                                .build()
                );
    }
}
