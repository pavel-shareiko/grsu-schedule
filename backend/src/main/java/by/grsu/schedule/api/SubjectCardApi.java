package by.grsu.schedule.api;

import by.grsu.schedule.api.dto.SubjectCardDto;
import by.grsu.schedule.api.dto.request.SubjectCardCreateRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/subject-cards")
public interface SubjectCardApi {

    @PostMapping
    ResponseEntity<SubjectCardDto> save(@Valid @RequestBody SubjectCardCreateRequestDto createRequest);

    @DeleteMapping("/{subjectId}")
    void delete(@PathVariable("subjectId") Long subjectId);

}
