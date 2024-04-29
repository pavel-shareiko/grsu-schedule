package by.grsu.schedule.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalysisResult {
    private String moduleName;
    private String message;
    private AnalysisStatus status;
    private Object details;

    private AnalysisResult() {
    }

    public static AnalysisResult success(String moduleName, String message, Object details) {
        return new AnalysisResult(moduleName, message, AnalysisStatus.SUCCESS, details);
    }

    public static AnalysisResult error(String moduleName, String message, Object details) {
        return new AnalysisResult(moduleName, message, AnalysisStatus.ERROR, details);
    }

}
