//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.config.http;

import com.intesasanpaolo.bear.core.properties.PropertiesManager;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConnectionPoolConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionPoolConfiguration.class);
    public static final String DEFAULT_PREFIX = "connectionPool";
    private Integer readTimeout;
    private Integer connectTimeout;
    private Integer connectRequestTimeout;
    private Integer idleTimeout;
    private Integer connectionSweeperInterval;
    private Integer maxConnections;
    private boolean expectContinue = true;
    private boolean cookieManagementDisabled = false;
    private Boolean enabled;
    private String connectorClassName;

    public HttpConnectionPoolConfiguration() {
    }

    public static HttpConnectionPoolConfiguration loadFromProperties(PropertiesManager propertiesManager, String connectorType, String className) {
        return loadFromProperties(propertiesManager, connectorType, className, "connectionPool");
    }

    public static HttpConnectionPoolConfiguration loadFromProperties(PropertiesManager propertiesManager, String connectorType, String className, String prefix) {
        return loadFromProperties(propertiesManager, connectorType, className, prefix, true);
    }

    public static HttpConnectionPoolConfiguration loadFromProperties(PropertiesManager propertiesManager, String connectorType, String className, String prefix, boolean nullOnDisabled) {
        HttpConnectionPoolConfiguration configuration = new HttpConnectionPoolConfiguration();

        try {
            boolean isDefault = true;
            Field[] var7 = HttpConnectionPoolConfiguration.class.getDeclaredFields();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object value = propertiesManager.getConnectorProperty(connectorType, className, prefix + "." + field.getName(), field.getType());
                    if (value != null) {
                        field.set(configuration, value);
                        isDefault = false;
                    }
                }
            }

            configuration.connectorClassName = className;
            if (isDefault) {
                configuration = makeDefaultConfig(className);
            } else {
                if (configuration.getEnabled() != null && !configuration.getEnabled() && nullOnDisabled) {
                    return null;
                }

                configuration.setEnabled(configuration.getEnabled() != null && configuration.getEnabled());
            }

            Integer connectorTimeout = (Integer)propertiesManager.getConnectorProperty(connectorType, className, "timeout", Integer.class);
            if (connectorTimeout != null && (configuration.getReadTimeout() == null || isDefault)) {
                configuration.setReadTimeout(connectorTimeout);
            }

            return configuration;
        } catch (IllegalAccessException var12) {
            logger.error("Error while trying to populate HTTP connection pool configuration from properties: ", var12);
            return null;
        }
    }

    public static HttpConnectionPoolConfiguration makeDefaultConfig() {
        return makeDefaultConfig((String)null);
    }

    public static HttpConnectionPoolConfiguration makeDefaultConfig(String className) {
        HttpConnectionPoolConfiguration configuration = new HttpConnectionPoolConfiguration();
        configuration.setReadTimeout(5000);
        configuration.setConnectTimeout(5000);
        configuration.setConnectRequestTimeout(5000);
        configuration.setIdleTimeout(60000);
        configuration.setConnectionSweeperInterval(5000);
        configuration.setMaxConnections(20);
        configuration.setExpectContinue(true);
        configuration.setCookieManagementDisabled(false);
        configuration.setEnabled(true);
        configuration.setConnectorClassName(className);
        return configuration;
    }

    public Integer getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getConnectRequestTimeout() {
        return this.connectRequestTimeout;
    }

    public void setConnectRequestTimeout(Integer connectRequestTimeout) {
        this.connectRequestTimeout = connectRequestTimeout;
    }

    public Integer getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getConnectionSweeperInterval() {
        return this.connectionSweeperInterval;
    }

    public void setConnectionSweeperInterval(Integer connectionSweeperInterval) {
        this.connectionSweeperInterval = connectionSweeperInterval;
    }

    public boolean isExpectContinue() {
        return this.expectContinue;
    }

    public void setExpectContinue(boolean expectContinue) {
        this.expectContinue = expectContinue;
    }

    public boolean isCookieManagementDisabled() {
        return this.cookieManagementDisabled;
    }

    public void setCookieManagementDisabled(boolean cookieManagementDisabled) {
        this.cookieManagementDisabled = cookieManagementDisabled;
    }

    public String getConnectorClassName() {
        return this.connectorClassName;
    }

    public void setConnectorClassName(String connectorClassName) {
        this.connectorClassName = connectorClassName;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
