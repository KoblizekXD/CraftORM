package lol.koblizek.craftorm.util.properties;

import lol.koblizek.craftorm.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

public final class ValidatedProperties extends Properties {

    private final Properties validKeys;

    public ValidatedProperties(InputStream keys, InputStream values) {
        try {
            load(values);
            validKeys = new Properties();
            validKeys.load(keys);
            // Perform check for valid types
            for (Object value : validKeys.values()) {
                if (Arrays.stream(Type.values()).noneMatch(t -> t.toString().equals(value))) {
                    throw new IllegalStateException("Invalid type: " + value);
                }
            }
            for (Map.Entry<Object, Object> entry : entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (!Type.find(validKeys.getProperty(key)).getTest().test(value)) {
                    throw new IllegalStateException("Invalid value type for key " + key + ": " + value);
                }
            }
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
