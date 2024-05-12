package by.grsu.schedule.service.schedule;

import by.grsu.schedule.api.dto.*;
import by.grsu.schedule.service.*;
import by.grsu.schedule.service.gateway.grsu.GrsuApiGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SchedulePullingService {
    GrsuApiGateway grsuApiGateway;
    FacultyService facultyService;
    DepartmentService departmentService;
    GroupService groupService;
    TeacherService teacherService;
    LessonService lessonService;

    public void pull() {
        List<FacultyDto> faculties = grsuApiGateway.getAllFaculties();
        facultyService.upsert(faculties);

        List<DepartmentDto> departments = grsuApiGateway.getAllDepartments();
        departmentService.upsert(departments);

        List<TeacherDto> teachers = grsuApiGateway.getAllTeachers();
        teacherService.upsert(teachers);

        List<GroupDto> groups = grsuApiGateway.getAllGroups(
                faculties.stream().map(FacultyDto::getId).toList(),
                departments.stream().map(DepartmentDto::getId).toList());
        groupService.upsert(groups);

        List<LessonDto> lessons = grsuApiGateway.getAllLessonsForTeachers(teachers);
        lessonService.upsert(lessons);
    }
}
