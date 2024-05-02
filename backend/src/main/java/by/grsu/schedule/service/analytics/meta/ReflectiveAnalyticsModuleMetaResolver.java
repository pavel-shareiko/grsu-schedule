package by.grsu.schedule.service.analytics.meta;

import by.grsu.schedule.annotations.FieldMeta;
import by.grsu.schedule.model.analytics.AnalyticsModule;
import by.grsu.schedule.model.analytics.AnalyticsModuleMeta;
import by.grsu.schedule.model.analytics.FieldDefinition;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReflectiveAnalyticsModuleMetaResolver implements AnalyticsModuleMetaResolver {
    FieldTypeMapper fieldTypeMapper;

    @Override
    public AnalyticsModuleMeta getModuleMeta(Class<?> moduleType, AnalyticsModule<?, ?> instance) {
        Class<?> inputType = ResolvableType.forClass(moduleType).as(AnalyticsModule.class).getGeneric(0).resolve();
        List<FieldDefinition> inputFieldDefinitions = getFieldDefinitions(Objects.requireNonNull(inputType));

        Class<?> outputType = ResolvableType.forClass(moduleType).as(AnalyticsModule.class).getGeneric(1).resolve();
        List<FieldDefinition> outputFieldDefinitions = getFieldDefinitions(Objects.requireNonNull(outputType));

        return AnalyticsModuleMeta.builder()
                .moduleName(instance.getSystemName())
                .displayName(instance.getDisplayName())
                .input(inputFieldDefinitions)
                .output(outputFieldDefinitions)
                .build();
    }

    private List<FieldDefinition> getFieldDefinitions(Class<?> type) {
        List<FieldDefinition> result = new ArrayList<>();

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            FieldDefinition fieldDefinition = getFieldDefinition(field);
            result.add(fieldDefinition);
        }

        return result;
    }

    private FieldDefinition getFieldDefinition(Field field) {
        FieldMeta meta = AnnotationUtils.findAnnotation(field, FieldMeta.class);

        boolean isRequired = hasRequiredAnnotations(field, meta);
        String fieldName = getFieldName(field, meta);
        String label = getFieldLabel(field, meta);
        String description = getFieldDescription(meta);
        String fieldType = getFieldType(field, meta);

        return FieldDefinition.builder()
                .required(isRequired)
                .label(label)
                .key(fieldName)
                .description(description)
                .type(fieldType)
                .build();
    }

    private String getFieldLabel(Field field, FieldMeta meta) {
        if (meta != null && isNotBlank(meta.label())) {
            return meta.label();
        }

        String name = field.getName();
        String label = StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(name),
                ' '
        );
        return StringUtils.capitalize(label);
    }

    private String getFieldName(Field field, FieldMeta meta) {
        return meta != null && isNotBlank(meta.key()) ? meta.key() : field.getName();
    }

    private String getFieldType(Field field, FieldMeta meta) {
        if (meta != null && isNotBlank(meta.type())) {
            return meta.type();
        }

        return fieldTypeMapper.mapFieldType(field);
    }

    private String getFieldDescription(FieldMeta meta) {
        if (meta != null && isNotBlank(meta.description())) {
            return meta.description();
        }

        return null;
    }

    private boolean hasRequiredAnnotations(Field field, FieldMeta meta) {
        if (meta != null && meta.required() != FieldMeta.YesNo.DEFAULT) {
            return meta.required() == FieldMeta.YesNo.YES;
        }

        return AnnotationUtils.findAnnotation(field, NotNull.class) != null;
    }
}
