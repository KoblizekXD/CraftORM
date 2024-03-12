package lol.koblizek.craftorm.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

    GenerationType generationType() default GenerationType.AUTO;

    enum GenerationType {
        AUTO,
        UUID,
        INCREMENT
    }
}
