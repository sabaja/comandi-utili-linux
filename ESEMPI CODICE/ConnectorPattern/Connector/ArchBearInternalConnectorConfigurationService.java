//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.bear;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.config.http.HttpConnectionPoolConfiguration;
import com.intesasanpaolo.bear.core.properties.PropertiesManager;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService.RestConfiguration;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BearConnectorConfiguration")
public class ArchBearInternalConnectorConfigurationService {
    private static final Logger log = LoggerUtils.getLogger(ArchBearInternalConnectorConfigurationService.class);
    public static final String CONNECTOR_TYPE = "connectors.bearInternalConfigurations";
    @Autowired
    private PropertiesManager propertiesManager;

    public ArchBearInternalConnectorConfigurationService() {
    }

    public ArchBearInternalConnectorConfigurationService.BearInternalConfiguration retrieveRestConfiguration(String className) {
        ArchBearInternalConnectorConfigurationService.BearInternalConfiguration config = new ArchBearInternalConnectorConfigurationService.BearInternalConfiguration();
        if (this.propertiesManager.containsConnectorProperty("connectors.bearInternalConfigurations", className, "url")) {
            config.setUrl((String)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "url", String.class));
            config.setTimeout((Integer)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "timeout", Integer.TYPE));
            config.setMocked((Boolean)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "mocked", Boolean.class, false));
            config.setPath((String)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "path", String.class));
            config.setRandomMock((Boolean)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "randomMock", Boolean.class));
            config.setFiles(this.propertiesManager.getConnectorPropertyList("connectors.bearInternalConfigurations", className, "files", String.class));
            config.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "enabledTracing", Boolean.class));
            config.setAuditEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "auditEnabled", Boolean.class, false));
            config.setCodService((String)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "codService", String.class));
            config.setBfdEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "bfdEnabled", Boolean.class, false));
            config.setOperationName((String)this.propertiesManager.getConnectorProperty("connectors.bearInternalConfigurations", className, "operationName", String.class));
            config.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, "connectors.bearInternalConfigurations", className));
            return config;
        } else {
            log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
            return null;
        }
    }

    public static class BearInternalConfiguration extends RestConfiguration {
        public BearInternalConfiguration() {
        }
    }
}
