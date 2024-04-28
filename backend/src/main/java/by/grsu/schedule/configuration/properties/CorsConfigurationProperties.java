package by.grsu.schedule.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("application.web.cors")
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CorsConfigurationProperties {
    List<String> allowedOrigins;
    List<String> allowedMethods;
    List<String> allowedHeaders;
}
