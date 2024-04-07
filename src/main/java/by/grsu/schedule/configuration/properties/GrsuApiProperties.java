package by.grsu.schedule.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.services.grsu.api")
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GrsuApiProperties {

    /**
     * Whether to use real GRSU API or not
     */
    Boolean enabled;

    /**
     * URL for GRSU API
     */
    String url;

    /**
     * Language key for data pulling
     */
    String langKey;
}
