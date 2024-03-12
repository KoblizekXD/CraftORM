package lol.koblizek.craftorm;

import lol.koblizek.craftorm.beans.Bean;
import lol.koblizek.craftorm.util.properties.ValidatedProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Bean
public final class ResourceProvider {
    public InputStream getResource(String property) {
        return getClass().getClassLoader()
                .getResourceAsStream(property);
    }

    public Properties getPropertyFile(String name) {
        Properties properties = new Properties();
        try (var stream = getResource(name)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public Map<String, ValidatedProperties.Type> getPropertyValidationFile(String name) {
        return ValidatedProperties.inputStreamToValidationKeys(getResource(name));
    }

    public ValidatedProperties getValidatedProperties(String name, boolean strict) {
        return new ValidatedProperties(getPropertyValidationFile(name.replace(".properties", ".format.properties")), getResource(name), strict);
    }
}
