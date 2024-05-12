package by.grsu.schedule.controller;

import by.grsu.schedule.api.SubjectCardApi;
import by.grsu.schedule.api.dto.SubjectCardDto;
import by.grsu.schedule.api.dto.request.SubjectCardCreateRequestDto;
import by.grsu.schedule.service.SubjectCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubjectCardController implements SubjectCardApi {
    private final SubjectCardService subjectCardService;

    @Override
    public ResponseEntity<SubjectCardDto> save(SubjectCardCreateRequestDto createRequest) {
        return ResponseEntity.ok(subjectCardService.save(createRequest));
    }
}
