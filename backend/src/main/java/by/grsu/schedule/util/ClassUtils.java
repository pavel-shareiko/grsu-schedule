package by.grsu.schedule.util;

import by.grsu.schedule.annotations.ResourceEntityReference;

import java.lang.reflect.Field;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class ClassUtils {
    public static boolean isCollection(Class<?> type) {
        return type.isArray() || Iterable.class.isAssignableFrom(type);
    }

    public static boolean isDate(Class<?> type) {
        return TemporalAccessor.class.isAssignableFrom(type)
                || Date.class.isAssignableFrom(type);
    }

    public static boolean isNumber(Class<?> type) {
        return Number.class.isAssignableFrom(type);
    }

    public static boolean isEnum(Class<?> type) {
        return type.isEnum();
    }

    public static boolean isReference(Field type) {
        return type.isAnnotationPresent(ResourceEntityReference.class);
    }

    public static boolean isBoolean(Class<?> type) {
        return Boolean.class.isAssignableFrom(type) || type == boolean.class;
    }
}
