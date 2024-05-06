package by.grsu.schedule.dto;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultDto {
    Long id;
    String moduleName;
    JsonNode context;
    JsonNode result;
    AnalysisStatus status;
    OffsetDateTime createTimestamp;
    String createdBy;
}
