package by.grsu.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalysisResult {
    private String moduleName;
    private String message;
    private AnalyticsStatus status;
    private Object details;

    private AnalysisResult() {
    }

    public static AnalysisResult success(String moduleName, String message, Object details) {
        return new AnalysisResult(moduleName, message, AnalyticsStatus.SUCCESS, details);
    }

    public static AnalysisResult error(String moduleName, String message, Object details) {
        return new AnalysisResult(moduleName, message, AnalyticsStatus.ERROR, details);
    }

    public enum AnalyticsStatus {
        SUCCESS,
        ERROR
    }
}
