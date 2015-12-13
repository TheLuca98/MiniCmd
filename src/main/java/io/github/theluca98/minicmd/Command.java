package io.github.theluca98.minicmd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate methods that represent a command.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * List of aliases the command will be registered under.
     *
     * @return List of aliases.
     */
    String[] value();

    /**
     * Description of the command to display in the usage message. Can be more than one line.
     *
     * @return Description of the command.
     */
    String[] description() default {};

    /**
     * List of arguments to display in the usage message. Use the standard Minecraft notation.
     * Example: &lt;required argument&gt; [optional argument]
     *
     * @return List of arguments.
     */
    String[] usage() default {};

}