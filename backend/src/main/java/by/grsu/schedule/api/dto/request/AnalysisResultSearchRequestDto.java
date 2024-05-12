package by.grsu.schedule.api.dto.request;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultSearchRequestDto {
    Long id;
    String moduleName;
    AnalysisStatus status;
    Map<String, String> context;
}
