package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
private static final Properties properties = new Properties();
    
    static {
        loadProperties();
    }
    private static void loadProperties() {
    	try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            
            if (input == null) {
                System.err.println("❌ config.properties not found in classpath!");
                System.err.println("📁 Make sure the file is in: src/test/resources/config.properties");
                return;
            }
            
            properties.load(input);
            System.out.println(properties);
            System.out.println("✅ config.properties loaded successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Error loading config.properties: " + e.getMessage());
        }
    }
// 🔽🔽🔽 ADD THESE GETTER METHODS AFTER THE LOAD 🔽🔽🔽
    
    // Get string property
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    // Get string property with default value
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    // Get integer property
    public static int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }
    
    // Get boolean property
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    // 🔽🔽🔽 CONVENIENCE METHODS FOR COMMON PROPERTIES 🔽🔽🔽
    
    public static String getBrowser() {
        return getProperty("browser.name", "chrome");
    }
    
    public static boolean isHeadless() {
        return getBoolean("browser.headless");
    }
    
    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    
    public static String getUsername() {
        return getProperty("username");
    }
    
    public static String getPassword() {
        return getProperty("password");
    }
    
    public static int getTimeout() {
        return getInt("timeout");
    }
    
    // Print all properties for debugging
    public static void printAllProperties() {
        System.out.println("=== LOADED PROPERTIES ===");
        properties.forEach((key, value) -> 
            System.out.println(key + " = " + value));
    }

}
