package lol.koblizek.craftorm.util;

import lol.koblizek.craftorm.beans.BeanLoader;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Member;

/**
 * Interface used to process annotation on during injection process
 * @param <T> Type of annotation which is this processor bound to
 */
public interface BeanDiscoverable<T extends Annotation> {

    /**
     * @param beanLoader BeanLoader instance subjected to this processor
     * @param annotation Instance of applying annotation
     * @param element Element type of member on which annotation is applied
     * @param member Member on which annotation is applied
     * @return Object which will be set to the member
     */
    Object onApplied(BeanLoader beanLoader, T annotation, ElementType element, Member member);
}
