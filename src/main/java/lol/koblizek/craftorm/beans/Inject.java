package lol.koblizek.craftorm.beans;

import lol.koblizek.craftorm.util.BeanDiscoverable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

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
@WithProcessor(Inject.InjectProcessor.class)
public @interface Inject {

    class InjectProcessor implements BeanDiscoverable<Inject> {

        @Override
        public Object onApplied(BeanLoader beanLoader, Inject annotation, ElementType element, Member member) {
            if (member instanceof Field field)
                return beanLoader.getBean(field.getType());
            return null;
        }
    }
}
