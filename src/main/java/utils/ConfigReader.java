package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    public static void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Unable to load config.properties file");
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            loadProperties(); // lazy load
        }
        return properties.getProperty(key);
    }
}
