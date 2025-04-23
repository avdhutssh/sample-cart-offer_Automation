package com.automation.SampleCartOffer.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final Logger log = LogManager.getLogger(PropertyReader.class);
    private static final Properties properties = new Properties();
    private static PropertyReader instance = null;

    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/application.properties";

    private PropertyReader() {
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream fileInput = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInput);
            log.info("Successfully loaded properties from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Failed to load properties file: {}", e.getMessage(), e);
        }
    }

    public static synchronized PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}