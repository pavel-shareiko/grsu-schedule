package by.grsu.schedule.dto.request;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultSearchRequestDto {
    String moduleName;
    AnalysisStatus status;
}
