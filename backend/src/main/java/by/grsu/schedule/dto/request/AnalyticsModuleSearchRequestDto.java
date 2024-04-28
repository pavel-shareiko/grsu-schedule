package by.grsu.schedule.dto.request;

import by.grsu.schedule.model.analytics.ModuleScope;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsModuleSearchRequestDto {
    @NotNull
    List<ModuleScope> scope;
}
