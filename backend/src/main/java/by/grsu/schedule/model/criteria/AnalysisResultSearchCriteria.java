package by.grsu.schedule.model.criteria;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultSearchCriteria {
    Long id;
    String moduleName;
    AnalysisStatus status;
    Map<String, String> context;
}
