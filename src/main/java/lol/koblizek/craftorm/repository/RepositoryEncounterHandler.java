package lol.koblizek.craftorm.repository;

import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.beans.BeanLoader;
import lol.koblizek.craftorm.util.ClassScanner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class RepositoryEncounterHandler implements ClassScanner.ClassEncounter {

    private final BeanLoader beanLoader;
    private final ResourceProvider resourceProvider;

    public RepositoryEncounterHandler(BeanLoader beanLoader) {
        this.beanLoader = beanLoader;
        this.resourceProvider = beanLoader.getBean(ResourceProvider.class);
    }

    @Override
    public void encounter(Class<?> clazz) {
        if (clazz.isInterface() && clazz.getInterfaces().length > 0 && Arrays.stream(clazz.getInterfaces()).anyMatch(i -> i == Repository.class)) {
            // Handle proxying
        }
    }

    static class RepositoryInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            return null;
        }
    }
}
