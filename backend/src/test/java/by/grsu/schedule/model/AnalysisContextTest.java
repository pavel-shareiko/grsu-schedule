package by.grsu.schedule.model;

import by.grsu.schedule.exception.analytics.RequiredPropertyMissingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnalysisContextTest {
    private AnalysisContext context;

    @BeforeEach
    void setUp() {
        context = new AnalysisContext(Map.of(
                "stringKey", "stringValue",
                "dateKey", LocalDate.of(2022, 1, 1)
        ));
    }

    @Test
    void getProperty_returnsCorrectValue_whenKeyExists() {
        assertEquals("stringValue", context.getProperty("stringKey"));
        assertEquals(LocalDate.of(2022, 1, 1), context.getProperty("dateKey"));
    }

    @Test
    void getProperty_throwsException_whenKeyDoesNotExist() {
        assertThrows(RequiredPropertyMissingException.class, () -> context.getProperty("nonexistentKey"));
    }

    @Test
    void getProperty_returnsDefaultValue_whenKeyDoesNotExist() {
        assertEquals("default", context.getProperty("nonexistentKey", "default"));
    }

    @Test
    void getPropertyWithType_mapsResponseToRequiredType_whenKeyExists() {
        assertEquals("stringValue", context.getProperty("stringKey", String.class));
        assertEquals(LocalDate.of(2022, 1, 1), context.getProperty("dateKey", LocalDate.class));
    }

    @Test
    void getPropertyWithType_throwsException_whenKeyDoesNotExist() {
        assertThrows(RequiredPropertyMissingException.class, () -> context.getProperty("nonexistentKey", String.class));
    }

    @Test
    void getPropertyWithType_returnsDefaultValue_whenKeyDoesNotExist() {
        assertEquals("default", context.getProperty("nonexistentKey", "default", String.class));
    }
}