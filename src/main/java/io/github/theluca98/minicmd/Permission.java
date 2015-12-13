package io.github.theluca98.minicmd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate {@link Command} methods that require a specific permission in order to be executed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {

    /**
     * Permission node required to execute the command.
     *
     * @return The permission node.
     */
    String value();

}
