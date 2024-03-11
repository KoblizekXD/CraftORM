package lol.koblizek.craftorm.util;

import lol.koblizek.craftorm.beans.BeanLoader;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Member;

@Todo
public interface BeanDiscoverable<T extends Annotation> {
    Object onApplied(BeanLoader beanLoader, T annotation, ElementType element, Member member);
}
