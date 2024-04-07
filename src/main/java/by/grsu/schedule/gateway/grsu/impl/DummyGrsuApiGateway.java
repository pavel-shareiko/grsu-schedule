package by.grsu.schedule.gateway.grsu.impl;

import by.grsu.schedule.aop.annotation.Loggable;
import by.grsu.schedule.dto.*;
import by.grsu.schedule.gateway.grsu.GrsuApiGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnMissingBean(GrsuApiGateway.class)
public class DummyGrsuApiGateway implements GrsuApiGateway {
    @Override
    public List<FacultyDto> getAllFaculties() {
        return List.of(
                new FacultyDto().setId(3952L).setTitle("Военный факультет"),
                new FacultyDto().setId(4908L).setTitle("Инженерный факультет"),
                new FacultyDto().setId(3601L).setTitle("Институт повышения квалификации и переподготовки кадров"),
                new FacultyDto().setId(11L).setTitle("Педагогический факультет"),
                new FacultyDto().setId(1L).setTitle("Учреждение"),
                new FacultyDto().setId(6L).setTitle("Факультет биологии и экологии"),
                new FacultyDto().setId(4217L).setTitle("Факультет искусств и дизайна"),
                new FacultyDto().setId(5211L).setTitle("Факультет истории, коммуникации и туризма"),
                new FacultyDto().setId(3L).setTitle("Факультет математики и информатики"),
                new FacultyDto().setId(2280L).setTitle("Факультет психологии"),
                new FacultyDto().setId(2L).setTitle("Факультет физической культуры"),
                new FacultyDto().setId(30L).setTitle("Факультет экономики и управления"),
                new FacultyDto().setId(4673L).setTitle("Физико-технический факультет"),
                new FacultyDto().setId(8L).setTitle("Филологический факультет"),
                new FacultyDto().setId(5555L).setTitle("Центр доуниверситетского образования"),
                new FacultyDto().setId(10L).setTitle("Юридический факультет")
        );
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return List.of(
                new DepartmentDto().setId(2L).setTitle("дневная форма"),
                new DepartmentDto().setId(3L).setTitle("заочная форма"),
                new DepartmentDto().setId(4L).setTitle("вечерняя форма"),
                new DepartmentDto().setId(5L).setTitle("дистанционная форма"),
                new DepartmentDto().setId(6L).setTitle("соискательство")
        );
    }

    @Override
    public List<GroupDto> getAllGroups(List<Long> facultyIds, List<Long> departmentIds) {
        return List.of(
                new GroupDto().setId(1L).setTitle("МАТ-201").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(2L).setTitle("МАТ-202").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(3L).setTitle("УИР-201").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(4L).setTitle("УИР-202").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(5L).setTitle("КБ-201").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(6L).setTitle("КБ-201").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(7L).setTitle("КБ-202").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(8L).setTitle("КБ-203").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(9L).setTitle("КБ-204").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(10L).setTitle("КБ-205").setFacultyId(3L).setDepartmentId(2L),
                new GroupDto().setId(11L).setTitle("ВИ-111").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(12L).setTitle("ВИ-112").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(13L).setTitle("ВИ-113").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(14L).setTitle("ВИ-114").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(15L).setTitle("ВИ-115").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(16L).setTitle("ВИ-116").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(17L).setTitle("ВИ-117").setFacultyId(3952L).setDepartmentId(3L),
                new GroupDto().setId(18L).setTitle("ВИ-118").setFacultyId(3952L).setDepartmentId(3L)
        );
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return List.of(
                new TeacherDto().setId(1L).setName("Иванов Иван Иванович"),
                new TeacherDto().setId(2L).setName("Петров Петр Петрович"),
                new TeacherDto().setId(3L).setName("Сидоров Сидор Сидорович"),
                new TeacherDto().setId(4L).setName("Александров Александр Александрович"),
                new TeacherDto().setId(5L).setName("Александрова Александра Александровна"),
                new TeacherDto().setId(6L).setName("Иванова Ивана Ивановна"),
                new TeacherDto().setId(7L).setName("Петрова Петра Петровна"),
                new TeacherDto().setId(8L).setName("Сидорова Сидора Сидоровна"),
                new TeacherDto().setId(9L).setName("Александрова Александра Александровна"),
                new TeacherDto().setId(10L).setName("Александрова Александра Александровна"),
                new TeacherDto().setId(11L).setName("Иванова Ивана Ивановна"),
                new TeacherDto().setId(12L).setName("Петрова Петра Петровна"),
                new TeacherDto().setId(13L).setName("Сидорова Сидора Сидоровна"),
                new TeacherDto().setId(14L).setName("Александрова Александра Александровна"),
                new TeacherDto().setId(15L).setName("Александрова Александра Александровна"),
                new TeacherDto().setId(16L).setName("Иванова Ивана Ивановна"),
                new TeacherDto().setId(17L).setName("Петрова Петра Петровна"),
                new TeacherDto().setId(18L).setName("Сидорова Сидора Сидоровна")
        );
    }

    @Override
    @Loggable
    public List<LessonDto> getAllLessonsForTeachers(List<TeacherDto> teachers) {
        LessonDto lesson = new LessonDto();
        lesson.setId(1L);
        lesson.setTitle("Математика");
        lesson.setTeacherId(1L);
        lesson.setGroupIds(List.of(1L, 2L));

        LessonTypeDto lessonType = new LessonTypeDto();
        lessonType.setId(1L);
        lessonType.setTitle("Лекция");
        lesson.setType(lessonType);

        AddressDto address = new AddressDto();
        address.setCountry("Беларусь");
        address.setCity("Гродно");
        address.setStreet("Ожешко");
        address.setHouse("22");
        address.setTitle("Ожешко, 22");
        lesson.setAddress(address);

        lesson.setRoom("101");
        lesson.setTimeStart(LocalTime.parse("08:30"));
        lesson.setTimeEnd(LocalTime.parse("10:00"));
        lesson.setLabel("");

        return List.of(lesson);
    }

}
