package by.grsu.schedule.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("jwt.auth.converter")
public class JwtConverterProperties {
    private String resourceId;
    private String principalAttribute;
}
