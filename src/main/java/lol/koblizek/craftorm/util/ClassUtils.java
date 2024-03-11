package lol.koblizek.craftorm.util;

import lol.koblizek.craftorm.beans.Inject;
import lol.koblizek.craftorm.util.properties.ValueOf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    public static Set<Class<?>> getClassesInPackage(ClassLoader loader, String packageName) {
        InputStream stream = loader
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(loader, line, packageName))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getClass(ClassLoader loader, String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')),
                    true, loader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean tryParseInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean tryParseFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean tryParseBoolean(String s) {
        try {
            Boolean.parseBoolean(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
