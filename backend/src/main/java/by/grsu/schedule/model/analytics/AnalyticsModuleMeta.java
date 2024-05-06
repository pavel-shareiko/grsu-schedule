package by.grsu.schedule.model.analytics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AnalyticsModuleMeta {
    String moduleName;
    String displayName;
    List<FieldDefinition> input;
    List<FieldDefinition> output;
}
