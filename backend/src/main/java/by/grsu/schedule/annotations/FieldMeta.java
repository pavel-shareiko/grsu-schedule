package by.grsu.schedule.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldMeta {
    String key() default "";

    String label() default "";

    String type() default "";

    String description() default "";

    YesNo required() default YesNo.DEFAULT;

    enum YesNo {
        YES, NO, DEFAULT
    }
}
