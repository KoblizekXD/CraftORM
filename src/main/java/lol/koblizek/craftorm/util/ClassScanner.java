package lol.koblizek.craftorm.util;

public class ClassScanner {

    @FunctionalInterface
    public interface ClassEncounter {
        void encounter(Class<?> clazz);
    }

    public void scanPackage(ClassLoader classLoader, String packageName, ClassEncounter encounter) {
        ClassUtils.getClassesInPackage(classLoader, packageName).forEach(encounter::encounter);
    }
}
