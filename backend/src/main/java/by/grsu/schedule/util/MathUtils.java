package by.grsu.schedule.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {
    /**
     * Calculates the Levenshtein distance between two strings.
     * <p>
     * The Levenshtein distance is the minimum number of single-character edits (insertions, deletions, substitutions) required to transform one string into another.
     * <p>
     * This function uses dynamic programming for efficient calculation.
     *
     * @param first  The first string.
     * @param second The second string.
     * @return The minimum number of edits required to transform the first string into the second string.
     * @throws IllegalArgumentException if either input string is null.
     */
    public static int levenshteinDistance(String first, String second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Strings cannot be null");
        }

        int m = first.length();
        int n = second.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,
                        Math.min(dp[i][j - 1] + 1,
                                dp[i - 1][j - 1] + cost));
            }
        }

        return dp[m][n];
    }

    public static double calculateMatchPercentage(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Strings cannot be null");
        }
        if (s1.isEmpty() && s2.isEmpty()) {
            return 100.00;
        }

        int matchingChars = 0;
        int shorterLength = Math.min(s1.length(), s2.length());
        int longerLength = Math.max(s1.length(), s2.length());

        for (int i = 0; i < shorterLength; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                matchingChars++;
            }
        }

        double similarity = ((double) matchingChars / longerLength) * 100.0;
        return Math.round(similarity * 100) / 100.0;
    }
}
