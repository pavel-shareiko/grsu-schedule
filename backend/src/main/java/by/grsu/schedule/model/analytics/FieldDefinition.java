package by.grsu.schedule.model.analytics;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FieldDefinition {
    String key;
    String label;
    String type;
    String description;
    boolean required;
}
