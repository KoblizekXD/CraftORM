package lol.koblizek.craftorm.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method or constructor as a candidate for dependency injection.
 * <p>
 *     Annotating such element will attempt to inject all of its parameters with
 *     beans from the existing container. If the container does not contain such bean
 *     an exception will be thrown.
 * </p>
 *
 * @author KoblizekXD
 * @see Bean
 * @see BeanLoader
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Inject {

}
