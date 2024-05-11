package core;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ConfigReader {
    private static final Properties properties;
    private static final String CONFIG_FILE_PATH = "config.properties";

    static {
        properties = new Properties();
        ensureConfigFileExists();
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures that the `config.properties` file exists. If it doesn't, creates a new one with default values.
     */
    private static void ensureConfigFileExists() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("# Default Configuration\n");
                writer.write("browser=chrome\n");
                writer.write("environment=production\n");
                writer.write("timeout=30\n");
                log.info("Created default config.properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the property value for a specified key.
     *
     * @param key The property key to search for.
     * @return The property value associated with the key, or null if not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
