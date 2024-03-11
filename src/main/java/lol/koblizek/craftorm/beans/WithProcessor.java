package lol.koblizek.craftorm.beans;

import lol.koblizek.craftorm.util.BeanDiscoverable;

import java.lang.annotation.*;

/**
 * Annotation that specifies a processor to be used for the injecting annotations.
 * The processor must implement the {@link BeanDiscoverable} interface.
 * If this annotation is not present on the injecting annotation, the default processor will be used.
 * @see BeanDiscoverable
 * @see InjectionScanner
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithProcessor {

    /**
     * @return The processor to be used for the injecting annotation.
     */
    Class<? extends BeanDiscoverable> value();
}
