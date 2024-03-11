package lol.koblizek.craftorm.util.properties;

import lol.koblizek.craftorm.beans.BeanLoader;
import lol.koblizek.craftorm.beans.WithProcessor;
import lol.koblizek.craftorm.util.BeanDiscoverable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@WithProcessor(ValueOf.ValueOfProcessor.class)
public @interface ValueOf {
    String value();

    class ValueOfProcessor implements BeanDiscoverable<ValueOf> {

        @Override
        public Object onApplied(BeanLoader beanLoader, ValueOf annotation, ElementType element, Member member) {
            return annotation.value();
        }
    }
}
