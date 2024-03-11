package lol.koblizek.craftorm.beans;

import lol.koblizek.craftorm.ResourceProvider;
import lol.koblizek.craftorm.util.ClassUtils;
import lol.koblizek.craftorm.util.properties.ValueOf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BeanLoader {

    private final Map<Class<?>, Object> beans;

    public BeanLoader() {
        this(false);
    }

    public BeanLoader(boolean loadDefaults) {
        beans = new HashMap<>();
        if (loadDefaults) {
            load(ResourceProvider.class);
        }
    }

    public void load(Class<?>... types) {
        for (Class<?> type : types) {
            for (Method method : type.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Bean.class) && Modifier.isStatic(method.getModifiers()))
                    methodLoad(method);
            }
            for (Constructor<?> constructor : type.getDeclaredConstructors()) {
                if (constructor.isAnnotationPresent(Bean.class)
                    || (constructor.getParameterCount() == 0 && type.isAnnotationPresent(Bean.class)))
                    ctorLoad(constructor);
            }
        }
    }

    public <T> T getBean(Class<T> type) {
        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            if (entry.getKey() == type) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

    @Deprecated
    public void performFieldInjection(String packageName) {
        for (Class<?> type : ClassUtils.getClassesInPackage(this.getClass().getClassLoader(), packageName)) {
            Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Inject.class)).forEach(f -> {
                f.trySetAccessible();
                try {
                    if (Modifier.isStatic(f.getModifiers())) {
                        // System.out.println("[BeanLoader] Injecting field " + f.getName() + " in class " + type.getName());
                        Object o = getBean(f.getType());
                        if (o != null) {
                            f.set(null, o);
                        } else {
                            throw new IllegalStateException("Value injection failed for field " + f.getName() + " in class " + type.getName() + " of type " + f.getType().getName() + " because the value was not found in any of configurations.");
                        }
                    } else {
                        Object o = getBean(f.getDeclaringClass());
                        if (o != null) {
                            f.set(o, getBean(f.getType()));
                        } else {
                            throw new IllegalStateException("Value injection failed for field " + f.getName() + " in class " + type.getName() + ", because no such bean exists for non-static field. \nConsider declaring field static or converting your class to bean");
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            Arrays.stream(type.getDeclaredFields()).filter(f -> f.isAnnotationPresent(ValueOf.class)).forEach(f -> {
                f.trySetAccessible();
                try {
                    if (Modifier.isStatic(f.getModifiers())) {
                        // System.out.println("[BeanLoader] Injecting field " + f.getName() + " in class " + type.getName());
                        Object o = getBean(f.getType());
                        if (o != null) {
                            f.set(null, o);
                        } else {
                            throw new IllegalStateException("Value injection failed for field " + f.getName() + " in class " + type.getName() + " of type " + f.getType().getName() + " because the value was not found in any of configurations.");
                        }
                    } else {
                        Object o = getBean(f.getDeclaringClass());
                        if (o != null) {
                            f.set(o, getBean(f.getType()));
                        } else {
                            throw new IllegalStateException("Value injection failed for field " + f.getName() + " in class " + type.getName() + ", because no such bean exists for non-static field. \nConsider declaring field static or converting your class to bean");
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public <T> void loadVirtual(Supplier<T> supplier) {
        beans.put(supplier.get().getClass(), supplier.get());
    }

    private void methodLoad(Method method) {
        Object[] params = new Object[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i++) {
            params[i] = getBean(method.getParameterTypes()[i]);
        }
        try {
            if (method.trySetAccessible()) {
                beans.put(method.getReturnType(), method.invoke(null, params));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void ctorLoad(Constructor<?> ctor) {
        Object[] params = new Object[ctor.getParameterCount()];
        for (int i = 0; i < ctor.getParameterCount(); i++) {
            params[i] = getBean(ctor.getParameterTypes()[i]);
        }
        try {
            if (ctor.trySetAccessible()) {
                beans.put(ctor.getDeclaringClass(), ctor.newInstance(params));
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
