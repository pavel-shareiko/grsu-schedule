package by.grsu.schedule.gateway.grsu;

import by.grsu.schedule.dto.*;

import java.util.List;

public interface GrsuApiGateway {
    List<FacultyDto> getAllFaculties();

    List<DepartmentDto> getAllDepartments();

    List<GroupDto> getAllGroups(List<Long> facultyIds, List<Long> departmentIds);

    List<TeacherDto> getAllTeachers();

    List<LessonDto> getAllLessonsForTeachers(List<TeacherDto> teachers);
}
