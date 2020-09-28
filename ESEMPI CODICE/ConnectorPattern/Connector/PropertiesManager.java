//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.core.properties;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class PropertiesManager {
    private static final String ITEMS_KEY = "items";
    private static final String DEFAULT_KEY = "default";
    @Autowired
    private ConfigurableEnvironment environment;

    public PropertiesManager() {
    }

    public String get(String key, String defaultValue) {
        return (String)this.environment.getProperty(key, String.class, defaultValue);
    }

    public String get(String key) {
        return (String)this.environment.getProperty(key, String.class);
    }

    public Integer get(String key, Integer defaultValue) {
        return (Integer)this.environment.getProperty(key, Integer.class, defaultValue);
    }

    public Long get(String key, Long defaultValue) {
        return (Long)this.environment.getProperty(key, Long.class, defaultValue);
    }

    public Float get(String key, Float defaultValue) {
        return (Float)this.environment.getProperty(key, Float.class, defaultValue);
    }

    public Boolean get(String key, Boolean defaultValue) {
        return (Boolean)this.environment.getProperty(key, Boolean.class, defaultValue);
    }

    public <T> T get(String key, Class<T> targetClass, T defaultValue) {
        return this.environment.getProperty(key, targetClass, defaultValue);
    }

    public <T> T get(String key, Class<T> targetClass) {
        return this.environment.getProperty(key, targetClass);
    }

    public boolean containsProperty(String key) {
        return this.environment.containsProperty(key);
    }

    public <T> T getConnectorProperty(String connectorType, String className, String propertyName, Class<T> targetClass) {
        String key = this.derivePropertyName(connectorType, className, propertyName);
        if (this.environment.containsProperty(key)) {
            return this.environment.getProperty(key, targetClass);
        } else {
            key = this.deriveDefaultPropertyName(connectorType, propertyName);
            return this.environment.getProperty(key, targetClass);
        }
    }

    public <T> T getConnectorProperty(String connectorType, String className, String propertyName, Class<T> targetClass, T defaultValue) {
        String key = this.derivePropertyName(connectorType, className, propertyName);
        if (this.environment.containsProperty(key)) {
            return this.environment.getProperty(key, targetClass);
        } else {
            key = this.deriveDefaultPropertyName(connectorType, propertyName);
            return this.environment.getProperty(key, targetClass, defaultValue);
        }
    }

    public boolean containsConnectorProperty(String connectorType, String className, String propertyName) {
        String key = this.derivePropertyName(connectorType, className, propertyName);
        return this.environment.containsProperty(key);
    }

    public <T> List<T> getConnectorPropertyList(String connectorType, String className, String propertyName, Class<T> targetClass) {
        String key = this.derivePropertyName(connectorType, className, propertyName);
        return this.getAsList(key, targetClass);
    }

    public <T> List<T> getAsList(String key, Class<T> targetClass) {
        List<T> returnList = new ArrayList();
        int i = 0;

        for(String indexedKey = key + "[" + i + "]"; this.environment.containsProperty(indexedKey); indexedKey = key + "[" + i + "]") {
            returnList.add(this.environment.getProperty(indexedKey, targetClass));
            ++i;
        }

        return returnList;
    }

    private String derivePropertyName(String connectorType, String className, String propertyName) {
        return String.format("%s.%s.%s.%s", connectorType, "items", className, propertyName);
    }

    private String deriveDefaultPropertyName(String connectorType, String propertyName) {
        return String.format("%s.%s.%s", connectorType, "default", propertyName);
    }
}
