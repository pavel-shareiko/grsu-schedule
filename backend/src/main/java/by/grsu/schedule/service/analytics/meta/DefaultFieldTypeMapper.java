package by.grsu.schedule.service.analytics.meta;

import by.grsu.schedule.annotations.ResourceEntityReference;
import by.grsu.schedule.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultFieldTypeMapper implements FieldTypeMapper {

    public static final String DEFAULT_TYPE = "default";
    public static final String IDENTIFIER = "id";
    public static final String NAME = "name";
    public static final String CUSTOM_VALUES_SEPARATOR = "$$";
    public static final String TYPE_COLLECTION = "collection";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_BOOLEAN = "boolean";

    private final ConfigurableBeanFactory beanFactory;

    @Override
    public String mapFieldType(Field field) {
        Class<?> type = field.getType();
        if (ClassUtils.isReference(field)) {
            return buildReference(field);
        }
        if (ClassUtils.isCollection(type)) {
            return TYPE_COLLECTION;
        }
        if (ClassUtils.isDate(type)) {
            return TYPE_DATE;
        }
        if (ClassUtils.isNumber(type)) {
            return TYPE_NUMBER;
        }
        if (ClassUtils.isBoolean(type)) {
            return TYPE_BOOLEAN;
        }
        if (ClassUtils.isEnum(type)) {
            return buildEnumValues(type);
        }
        return DEFAULT_TYPE;
    }

    private String buildEnumValues(Class<?> type) {
        StringBuilder result = new StringBuilder("enum" + CUSTOM_VALUES_SEPARATOR + "values=");
        Object[] enumConstants = type.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            result.append(enumConstant).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private String buildReference(Field field) {
        ResourceEntityReference resourceEntityReference = field.getAnnotation(ResourceEntityReference.class);
        if (resourceEntityReference == null) {
            return DEFAULT_TYPE;
        }

        return buildReferenceString(resourceEntityReference, field);
    }

    private String buildReferenceString(ResourceEntityReference resourceEntityReference, Field field) {
        var searchResourceApiUrl = resourceEntityReference.url();
        var contentPath = resourceEntityReference.contentPath();
        var paramName = valueOrElse(resourceEntityReference.paramName(), NAME);
        var identifierField = valueOrElse(resourceEntityReference.identifier(), IDENTIFIER);
        var displayFormat = getDisplayFormat(resourceEntityReference, paramName);

        return "reference" + CUSTOM_VALUES_SEPARATOR +
                "url=" + searchResourceApiUrl + CUSTOM_VALUES_SEPARATOR +
                "identifier=" + identifierField + CUSTOM_VALUES_SEPARATOR +
                "contentPath=" + contentPath + CUSTOM_VALUES_SEPARATOR +
                "paramName=" + paramName + CUSTOM_VALUES_SEPARATOR +
                "multiselect=" + ClassUtils.isCollection(field.getType()) + CUSTOM_VALUES_SEPARATOR +
                "displayFormat=" + displayFormat;
    }

    private String getDisplayFormat(ResourceEntityReference resourceEntityReference, String paramName) {
        String displayFormat = valueOrElse(resourceEntityReference.displayFormat(), paramName);
        try {
            return beanFactory.resolveEmbeddedValue(displayFormat);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to resolve display format: {}", displayFormat);
            return displayFormat;
        }
    }

    private String valueOrElse(String val, String defaultValue) {
        return (val == null || val.isBlank()) ? defaultValue : val;
    }
}
