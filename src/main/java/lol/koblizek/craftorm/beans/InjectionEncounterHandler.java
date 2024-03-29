package lol.koblizek.craftorm.beans;

import lol.koblizek.craftorm.util.ClassScanner;
import lol.koblizek.craftorm.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class InjectionEncounterHandler implements ClassScanner.ClassEncounter {

    private final BeanLoader beanLoader;
    private final Class<? extends Annotation>[] annotations;

    @SafeVarargs
    public InjectionEncounterHandler(BeanLoader beanLoader, Class<? extends Annotation>... annotations) {
        this.beanLoader = beanLoader;
        this.annotations = annotations;
    }

    @Override
    public void encounter(Class<?> type) {
        for (Class<? extends Annotation> annotation : annotations) {
            Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotation)).forEach(f -> {
                f.trySetAccessible();
                try {
                    if (annotation.isAnnotationPresent(WithProcessor.class)) {
                        if (Modifier.isStatic(f.getModifiers())) {
                            f.set(null, ClassUtils.newInstance(annotation.getAnnotation(WithProcessor.class).value())
                                    .onApplied(beanLoader, f.getAnnotation(annotation), ElementType.FIELD, f));
                        } else {
                            f.set(beanLoader.getBean(type), ClassUtils.newInstance(annotation.getAnnotation(WithProcessor.class).value())
                                    .onApplied(beanLoader, f.getAnnotation(annotation), ElementType.FIELD, f));
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
