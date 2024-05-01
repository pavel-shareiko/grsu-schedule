package by.grsu.schedule.model.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalysisResult<T> {
    private String moduleName;
    private String message;
    private AnalysisStatus status;
    private Object error;
    private T details;

    private AnalysisResult() {
    }

    public static <T> AnalysisResult<T> success(String moduleName, String message, T details) {
        return new AnalysisResult<>(moduleName, message, AnalysisStatus.SUCCESS, null, details);
    }

    public static <T> AnalysisResult<T> error(String moduleName, String message, Object e) {
        return new AnalysisResult<>(moduleName, message, AnalysisStatus.ERROR, e, null);
    }

}
