package by.grsu.schedule.service.schedule;

import by.grsu.schedule.dto.DepartmentDto;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.dto.GroupDto;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.gateway.grsu.GrsuApiGateway;
import by.grsu.schedule.service.DepartmentService;
import by.grsu.schedule.service.FacultyService;
import by.grsu.schedule.service.GroupService;
import by.grsu.schedule.service.TeacherService;
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

    public void pull() {
        List<FacultyDto> faculties = grsuApiGateway.getAllFaculties();
        facultyService.upsert(faculties);

        List<DepartmentDto> departments = grsuApiGateway.getAllDepartments();
        departmentService.upsert(departments);

        List<GroupDto> groups = grsuApiGateway.getAllGroups(
                faculties.stream().map(FacultyDto::getId).toList(),
                departments.stream().map(DepartmentDto::getId).toList());
        groupService.upsert(groups);

        List<TeacherDto> teachers = grsuApiGateway.getAllTeachers();
        teacherService.upsert(teachers);
    }
}
