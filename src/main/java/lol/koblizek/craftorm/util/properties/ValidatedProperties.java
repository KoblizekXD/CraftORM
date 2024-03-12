package lol.koblizek.craftorm.util.properties;

import lol.koblizek.craftorm.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ValidatedProperties extends Properties {

    public ValidatedProperties(Map<String, Type> validationKeys, InputStream values, boolean strict) {
        try {
            load(values);
            for (Map.Entry<Object, Object> entry : entrySet()) {
                if (strict) {
                    if (!validationKeys.containsKey(entry.getKey())) {
                        throw new IllegalArgumentException("Invalid key: " + entry.getKey());
                    } else {
                        Type type = validationKeys.get(entry.getKey());
                        if (!type.getTest().test((String) entry.getValue())) {
                            throw new IllegalArgumentException("Invalid value for key: " + entry.getKey() + ", required type: " + type);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getClass(String key) {
        try {
            return Class.forName(getProperty(key));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Type> inputStreamToValidationKeys(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
            return properties.entrySet().stream().map(e -> {
                Type type = Type.find(e.getValue().toString());
                if (type == null) {
                    throw new IllegalArgumentException("Invalid type: " + e.getValue());
                }
                return Map.entry(e.getKey().toString(), type);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Type {
        STRING(s -> true),
        INTEGER(ClassUtils::tryParseInt),
        FLOAT(ClassUtils::tryParseFloat),
        BOOLEAN(ClassUtils::tryParseBoolean),
        CLASS(ClassUtils::exists);

        private final Predicate<String> test;

        Type(Predicate<String> test) {
            this.test = test;
        }

        public Predicate<String> getTest() {
            return test;
        }

        public static Type find(String s) {
            return Arrays.stream(values()).filter(t -> t.toString().equals(s)).findFirst().orElse(null);
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
