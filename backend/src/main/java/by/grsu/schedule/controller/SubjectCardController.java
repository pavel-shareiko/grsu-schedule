package by.grsu.schedule.controller;

import by.grsu.schedule.dto.SubjectCardDto;
import by.grsu.schedule.dto.request.SubjectCardCreateRequestDto;
import by.grsu.schedule.service.SubjectCardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subject-cards")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectCardController {
    SubjectCardService subjectCardService;

    @PostMapping
    public ResponseEntity<SubjectCardDto> save(@RequestBody SubjectCardCreateRequestDto createRequest) {
        return ResponseEntity.ok(subjectCardService.save(createRequest));
    }
}
