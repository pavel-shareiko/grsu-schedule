package by.grsu.schedule.model;

import by.grsu.schedule.exception.analytics.PropertyTypeMismatchException;
import by.grsu.schedule.exception.analytics.RequiredPropertyMissingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
public class AnalysisContext {
    private final Map<String, Object> properties;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final ObjectMapper objectMapper;

    public AnalysisContext(Map<String, Object> properties) {
        this.properties = new HashMap<>(properties);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Object getProperty(String key) {
        if (!properties.containsKey(key)) {
            throw new RequiredPropertyMissingException(key);
        }
        return properties.get(key);
    }

    public Object getProperty(String key, Object defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public <T> T getProperty(String key, Class<? extends T> propertyClass) {
        if (!properties.containsKey(key)) {
            throw new RequiredPropertyMissingException(key);
        }

        try {
            return objectMapper.convertValue(properties.get(key), propertyClass);
        } catch (IllegalArgumentException e) {
            throw new PropertyTypeMismatchException(key, propertyClass);
        }
    }

    public <T> T getProperty(String key, T defaultValue, Class<? extends T> propertyClass) {
        Object value = properties.getOrDefault(key, defaultValue);
        try {
            return objectMapper.convertValue(value, propertyClass);
        } catch (IllegalArgumentException e) {
            throw new PropertyTypeMismatchException(key, propertyClass);
        }
    }

    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }
}
