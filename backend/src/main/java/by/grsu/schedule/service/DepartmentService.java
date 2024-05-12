package by.grsu.schedule.service;

import by.grsu.schedule.domain.DepartmentEntity;
import by.grsu.schedule.api.dto.DepartmentDto;
import by.grsu.schedule.mapper.DepartmentMapper;
import by.grsu.schedule.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DepartmentService {
    DepartmentMapper departmentMapper;
    DepartmentRepository departmentRepository;

    @Transactional
    public void upsert(List<DepartmentDto> departments) {
        List<DepartmentEntity> departmentsToSave = departments.stream()
                .map(departmentMapper::toEntity)
                .toList();

        departmentRepository.saveAll(departmentsToSave);
    }
}
