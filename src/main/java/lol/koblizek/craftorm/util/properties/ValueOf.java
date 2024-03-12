package lol.koblizek.craftorm.util.properties;

import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.beans.BeanLoader;
import lol.koblizek.craftorm.beans.WithProcessor;
import lol.koblizek.craftorm.util.BeanDiscoverable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.util.Properties;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@WithProcessor(ValueOf.ValueOfProcessor.class)
public @interface ValueOf {
    String value();
    String resource() default "database.properties";

    class ValueOfProcessor implements BeanDiscoverable<ValueOf> {

        @Override
        public Object onApplied(BeanLoader beanLoader, ValueOf annotation, ElementType element, Member member) {
            Properties properties = beanLoader.getBean(ResourceProvider.class).getPropertyFile(annotation.resource());
            String property = properties.getProperty(annotation.value());
            if (property == null)
                throw new IllegalArgumentException("Property " + annotation.value() + " not found in " + annotation.resource() + " file.");
            return property;
        }
    }
}
