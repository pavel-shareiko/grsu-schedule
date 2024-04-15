package by.grsu.schedule.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MathUtilsTest {
    @Test
    void levenshteinDistance_returnsZero_whenStringsAreEqual() {
        assertEquals(0, MathUtils.levenshteinDistance("abc", "abc"));
    }

    @Test
    void levenshteinDistance_throwsException_whenStringsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> MathUtils.levenshteinDistance(null, "abc"));
        assertThrows(IllegalArgumentException.class, () -> MathUtils.levenshteinDistance("abc", null));
    }


    @ParameterizedTest
    @MethodSource("getLevenshteinDistanceTestCases")
    void levenshteinDistance_returnsCorrectValue_whenStringsAreDifferent(String first, String second, int expectedDistance) {
        assertEquals(expectedDistance, MathUtils.levenshteinDistance(first, second));
    }

    @Test
    void calculateMatchPercentage_returns100_whenStringsAreEqual() {
        assertEquals(100.0, MathUtils.calculateMatchPercentage("abc", "abc"));
    }

    @Test
    void calculateMatchPercentage_throwsException_whenStringsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> MathUtils.calculateMatchPercentage(null, "abc"));
        assertThrows(IllegalArgumentException.class, () -> MathUtils.calculateMatchPercentage("abc", null));
    }

    @ParameterizedTest
    @MethodSource("getCalculateMatchPercentageTestCases")
    void calculateMatchPercentage_returnsCorrectValue_whenStringsAreDifferent(String s1, String s2, double expectedPercentage) {
        assertEquals(expectedPercentage, MathUtils.calculateMatchPercentage(s1, s2));
    }

    public static Stream<Arguments> getLevenshteinDistanceTestCases() {
        return Stream.of(
                Arguments.of("abc", "ab", 1),
                Arguments.of("abc", "abcd", 1),
                Arguments.of("abc", "def", 3),
                Arguments.of("abc", "abcd", 1),
                Arguments.of("abc", "acc", 1),
                Arguments.of("abcef", "", 5),
                Arguments.of("", "abcef", 5),
                Arguments.of("", "", 0)
        );
    }

    public static Stream<Arguments> getCalculateMatchPercentageTestCases() {
        return Stream.of(
                Arguments.of("", "", 100.0),
                Arguments.of("abc", "", 0.00),
                Arguments.of("", "abc", 0.00),
                Arguments.of("abcdef", "abc", 50.00),
                Arguments.of("abc", "def", 0.00)
        );
    }
}