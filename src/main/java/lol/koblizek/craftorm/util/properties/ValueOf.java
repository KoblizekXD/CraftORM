package lol.koblizek.craftorm.util.properties;

import lol.koblizek.craftorm.util.BeanDiscoverable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueOf {
    String value();

    class ValueOfProcessor implements BeanDiscoverable {

        @Override
        public Object onApplied(Object... annotationValues) {
            String value = (String) annotationValues[0];
            return value;
        }
    }
}
