package by.grsu.schedule.gateway.grsu;

import by.grsu.schedule.configuration.properties.GrsuApiProperties;
import by.grsu.schedule.dto.DepartmentDto;
import by.grsu.schedule.dto.FacultyDto;
import by.grsu.schedule.dto.GroupDto;
import by.grsu.schedule.dto.TeacherDto;
import by.grsu.schedule.gateway.grsu.dto.*;
import by.grsu.schedule.mapper.DepartmentMapper;
import by.grsu.schedule.mapper.FacultyMapper;
import by.grsu.schedule.mapper.GroupMapper;
import by.grsu.schedule.mapper.TeacherMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.grsu.schedule.util.RestUtils.buildUri;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class GrsuApiGateway {
    static final int MIN_COURSE = 1;
    static final int MAX_COURSE = 5;

    RestTemplate restTemplate;
    GrsuApiProperties apiProperties;
    FacultyMapper facultyMapper;
    DepartmentMapper departmentMapper;
    GroupMapper groupMapper;
    TeacherMapper teacherMapper;

    public List<FacultyDto> getAllFaculties() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getFaculties"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuFacultyDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving faculties returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(facultyMapper::toDto)
                .toList();
    }

    public List<DepartmentDto> getAllDepartments() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getDepartments"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuDepartmentDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving departments returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(departmentMapper::toDto)
                .toList();
    }

    public List<GroupDto> getAllGroups(List<Long> facultyIds, List<Long> departmentIds) {
        List<GroupDto> groups = new ArrayList<>();

        // TODO: тянуть не все?
        for (int course = MIN_COURSE; course <= MAX_COURSE; course++) {
            for (var facultyId : facultyIds) {
                for (var departmentId : departmentIds) {
                    Map<String, String> requestParams = Map.of(
                            "facultyId", String.valueOf(facultyId),
                            "departmentId", String.valueOf(departmentId),
                            "course", String.valueOf(course)
                    );
                    var response = restTemplate.exchange(
                            buildRequestUrl("/getGroups", requestParams),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuGroupDto>>>() {
                            }
                    );

                    if (response.getBody() == null || response.getBody().getItems() == null) {
                        log.warn("API for receiving groups returned empty body with parameters: {}", requestParams);
                        continue;
                    }

                    groups.addAll(response.getBody().getItems().stream()
                            .map(grsuGroupDto -> groupMapper.toDto(grsuGroupDto, facultyId, departmentId))
                            .toList());
                }
            }
        }

        return groups;
    }

    public List<TeacherDto> getAllTeachers() {
        var response = restTemplate.exchange(
                buildRequestUrl("/getTeachers"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<GrsuResponseWrapper<List<GrsuTeacherDto>>>() {
                }
        );

        if (response.getBody() == null) {
            log.warn("API for receiving teachers returned empty body");
            return List.of();
        }

        return response.getBody().getItems().stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    private URI buildRequestUrl(String path) {
        return buildUri(apiProperties.getUrl() + path, Map.of("lang", apiProperties.getLangKey()));
    }

    private URI buildRequestUrl(String path, Map<String, String> params) {
        if (params == null) {
            return buildUri(apiProperties.getUrl() + path);
        }

        Map<String, String> finalParams = new HashMap<>(params);
        finalParams.put("lang", apiProperties.getLangKey());

        return buildUri(apiProperties.getUrl() + path, finalParams);
    }
}
