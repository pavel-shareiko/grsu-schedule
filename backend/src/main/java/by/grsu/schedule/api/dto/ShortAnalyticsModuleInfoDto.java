package by.grsu.schedule.api.dto;

import by.grsu.schedule.model.analytics.ModuleScope;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShortAnalyticsModuleInfoDto {
    String systemName;
    String displayName;
    String description;
    long usesCount;
    Set<ModuleScope> scope;
    ShortAnalysisHistoryDto latestResult;
}
