package by.grsu.schedule.api.dto;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShortAnalysisHistoryDto {
    String moduleName;
    AnalysisStatus status;
    OffsetDateTime timestamp;
}
