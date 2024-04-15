package by.grsu.schedule.service;

import by.grsu.schedule.domain.LessonEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonTypeService {
    private static final Map<List<String>, Character> SIGN_TO_CODE = Map.of(
            List.of("лек.", "лекция"), 'Л',
            List.of("практ.", "практическое"), 'П',
            List.of("лаб.", "лабороторное"), 'Б'
    );


    /**
     * Generates sequence of lesson type codes for the given lessons
     *
     * @param lessons list of lessons
     * @return sequence of lesson type codes
     */
    public String getLessonsSequence(List<LessonEntity> lessons) {
        return lessons.stream()
                .map(l -> getCodeFromTitle(l.getType().getTitle()))
                .filter(Objects::nonNull)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /**
     * Generates one-letter code for the lesson type
     * <h2>Mapping logic: </h2>
     * <ul>
     *     <li>лекция (ауд.) - Л</li>
     *     <li>лекция (уср) - л</li>
     *     <li>практика (ауд.) - П</li>
     *     <li>практика (уср) - п</li>
     *     <li>лабораторная (ауд.) - Б</li>
     *     <li>лабораторная (уср) - б</li>
     *     <li>other - null</li>
     * </ul>
     *
     * @return one-letter code
     */
    public Character getCodeFromTitle(String title) {
        if (title == null) {
            return null;
        }

        String trimmedTitle = title.toLowerCase().trim();
        boolean isSse = trimmedTitle.contains("уср");
        for (var entry : SIGN_TO_CODE.entrySet()) {
            if (entry.getKey().stream().anyMatch(trimmedTitle::contains)) {
                if (isSse) {
                    return Character.toLowerCase(entry.getValue());
                }
                return entry.getValue();
            }
        }

        log.info("Unable to resolve lesson type {} to one-letter descriptor", title);
        return null;
    }
}
