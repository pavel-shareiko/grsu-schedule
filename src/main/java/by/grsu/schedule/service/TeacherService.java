package by.grsu.schedule.service;

import by.grsu.schedule.domain.Teacher;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.mapper.TeacherMapper;
import by.grsu.schedule.repository.TeacherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
        List<Teacher> grsuTeachers = teachers
                .stream()
                .map(teacherMapper::toEntity)
                .toList();

        teacherRepository.saveAll(grsuTeachers);
    }
}
