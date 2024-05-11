package by.grsu.schedule.api;

import by.grsu.schedule.dto.SubjectCardDto;
import by.grsu.schedule.dto.request.SubjectCardCreateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/subject-cards")
public interface SubjectCardApi {

    @PostMapping
    ResponseEntity<SubjectCardDto> save(@RequestBody SubjectCardCreateRequestDto createRequest);

}
