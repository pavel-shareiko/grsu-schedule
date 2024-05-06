package by.grsu.schedule.model.criteria;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisResultSearchCriteria {
    String moduleName;
    AnalysisStatus status;
}
