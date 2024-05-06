package by.grsu.schedule.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResourceEntityReference {
    /**
     * API URL to send request to
     */
    String url();

    /**
     * Identifier field name of the entity
     */
    String identifier() default "id";

    /**
     * Path to the content in the response
     */
    String contentPath() default "payload";

    /**
     * Name of the parameter to search by
     */
    String paramName() default "";

    /**
     * Display format for the entity
     */
    String displayFormat() default "";
}
