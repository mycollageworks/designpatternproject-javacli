package util;

import java.util.Properties;
import java.io.InputStream;

public class Config {
  private static Config instance;
  private Properties props;

  private Config() {
    props = new Properties();
    try (InputStream input = getClass().getResourceAsStream("/.env")) {
      if (input != null) {
        // print a message to indicate that the .env file is being loaded
        System.out.println("🔍 Loading configuration from .env file...");

        // print input stream information
        System.out.println("InputStream: " + input.getClass().getName());
        props.load(input);
      } else {
        System.err.println("⚠️ .env file not found in the classpath.");
      }
    } catch (Exception e) {
      System.err.println("⚠️ Failed to load .env: " + e.getMessage());
    }
  }

  public static Config getInstance() {
    System.err.println("🔍 Getting Config instance...");
    if (instance == null) {
      System.out.println("🔍 Creating new Config instance...");
      instance = new Config();
    }
    return instance;
  }

  public String get(String key, String defaultValue) {
    // print all keys and values in the .env file
    if (props.isEmpty()) {
      System.out.println("⚠️ No configuration found in .env file.");
      return defaultValue;
    }
    System.out.println("🔍 Available configuration:");

    return props.getProperty(key, defaultValue);
  }
}
