//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.rest.configuration;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.config.http.HttpConnectionPoolConfiguration;
import com.intesasanpaolo.bear.config.http.IHttpConnectionPoolConfigurationAware;
import com.intesasanpaolo.bear.connector.bear.ArchBearInternalConnectorConfigurationService.BearInternalConfiguration;
import com.intesasanpaolo.bear.connector.bearcore.ArchBearCoreConnectorConfigurationService.BearCoreConfiguration;
import com.intesasanpaolo.bear.core.properties.PropertiesManager;
import com.intesasanpaolo.bear.layers.connector.AuditableConnectorConfiguration;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RestConfiguration")
public class ArchRestConfigurationService {
    public static final String CONNECTOR_TYPE = "connectors.restConfigurations";
    public static final String DATAPOWER_CONNECTOR_TYPE = "connectors.restDataPowerConfigurations";
    private static final Logger log = LoggerUtils.getLogger(ArchRestConfigurationService.class);
    @Autowired
    private PropertiesManager propertiesManager;

    public ArchRestConfigurationService() {
    }

    public ArchRestConfigurationService.RestConfiguration retrieveRestConfiguration(String className) {
        ArchRestConfigurationService.RestConfiguration dataPowerRestConfig;
        if (this.propertiesManager.containsConnectorProperty("connectors.restConfigurations", className, "url")) {
            dataPowerRestConfig = new ArchRestConfigurationService.RestConfiguration();
            dataPowerRestConfig.setUrl((String)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "url", String.class));
            dataPowerRestConfig.setTimeout((Integer)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "timeout", Integer.TYPE));
            dataPowerRestConfig.setMocked((Boolean)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "mocked", Boolean.class, false));
            dataPowerRestConfig.setPath((String)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "path", String.class));
            dataPowerRestConfig.setRandomMock((Boolean)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "randomMock", Boolean.class));
            dataPowerRestConfig.setFiles(this.propertiesManager.getConnectorPropertyList("connectors.restConfigurations", className, "files", String.class));
            dataPowerRestConfig.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "enabledTracing", Boolean.class));
            dataPowerRestConfig.setAuditEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "auditEnabled", Boolean.class, false));
            dataPowerRestConfig.setCodService((String)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "codService", String.class));
            dataPowerRestConfig.setBfdEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "bfdEnabled", Boolean.class, false));
            dataPowerRestConfig.setOperationName((String)this.propertiesManager.getConnectorProperty("connectors.restConfigurations", className, "operationName", String.class));
            dataPowerRestConfig.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, "connectors.restConfigurations", className));
            return dataPowerRestConfig;
        } else if (this.propertiesManager.containsConnectorProperty("connectors.restDataPowerConfigurations", className, "url")) {
            dataPowerRestConfig = new ArchRestConfigurationService.RestConfiguration();
            dataPowerRestConfig.setUrl((String)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "url", String.class));
            dataPowerRestConfig.setTimeout((Integer)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "timeout", Integer.TYPE));
            dataPowerRestConfig.setMocked((Boolean)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "mocked", Boolean.class, false));
            dataPowerRestConfig.setPath((String)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "path", String.class));
            dataPowerRestConfig.setRandomMock((Boolean)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "randomMock", Boolean.class));
            dataPowerRestConfig.setFiles(this.propertiesManager.getConnectorPropertyList("connectors.restDataPowerConfigurations", className, "files", String.class));
            dataPowerRestConfig.setEnabledTracing((Boolean)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "enabledTracing", Boolean.class));
            dataPowerRestConfig.setAuditEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "auditEnabled", Boolean.class, false));
            dataPowerRestConfig.setCodService((String)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "codService", String.class));
            dataPowerRestConfig.setBfdEnabled((Boolean)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "bfdEnabled", Boolean.class, false));
            dataPowerRestConfig.setOperationName((String)this.propertiesManager.getConnectorProperty("connectors.restDataPowerConfigurations", className, "operationName", String.class));
            dataPowerRestConfig.setConnectionPoolConfiguration(HttpConnectionPoolConfiguration.loadFromProperties(this.propertiesManager, "connectors.restDataPowerConfigurations", className));
            return dataPowerRestConfig;
        } else {
            log.warn(LoggerUtils.formatArchRow("Configuration not present for connector {}"), className);
            return null;
        }
    }

    public static class RestConfiguration implements IHttpConnectionPoolConfigurationAware, AuditableConnectorConfiguration {
        private String url;
        private int timeout = 0;
        private Boolean mocked = false;
        private boolean randomMock = true;
        private String path;
        private List<String> files;
        private HttpConnectionPoolConfiguration connectionPool = null;
        private Boolean enabledTracing;
        private boolean auditEnabled;
        private String codService;
        private boolean bfdEnabled;
        private String operationName;

        public RestConfiguration() {
        }

        public RestConfiguration(BearInternalConfiguration configuration) {
            this.url = configuration.getUrl();
            this.mocked = configuration.isMocked();
            this.randomMock = configuration.isRandomMock();
            this.path = configuration.getPath();
            this.files = configuration.getFiles();
            this.timeout = configuration.getTimeout();
            this.connectionPool = configuration.getConnectionPoolConfiguration();
            this.enabledTracing = configuration.isEnabledTracing();
            this.auditEnabled = configuration.isAuditEnabled();
            this.codService = configuration.getCodService();
            this.bfdEnabled = configuration.isBfdEnabled();
            this.operationName = configuration.getOperationName();
        }

        public RestConfiguration(BearCoreConfiguration configuration) {
            this.url = configuration.getUrl();
            this.mocked = configuration.isMocked();
            this.randomMock = configuration.isRandomMock();
            this.path = configuration.getPath();
            this.files = configuration.getFiles();
            this.timeout = configuration.getTimeout();
            this.connectionPool = configuration.getConnectionPoolConfiguration();
            this.enabledTracing = configuration.isEnabledTracing();
            this.auditEnabled = configuration.isAuditEnabled();
            this.codService = configuration.getCodService();
            this.bfdEnabled = configuration.isBfdEnabled();
            this.operationName = configuration.getOperationName();
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Boolean isMocked() {
            return this.mocked;
        }

        public void setMocked(Boolean mocked) {
            this.mocked = mocked != null ? mocked : false;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<String> getFiles() {
            return this.files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout != null ? timeout : 0;
        }

        public boolean isRandomMock() {
            return this.randomMock;
        }

        public void setRandomMock(Boolean randomMock) {
            this.randomMock = randomMock != null ? randomMock : true;
        }

        public HttpConnectionPoolConfiguration getConnectionPoolConfiguration() {
            return this.connectionPool;
        }

        public void setConnectionPoolConfiguration(HttpConnectionPoolConfiguration connectionPool) {
            this.connectionPool = connectionPool;
        }

        public boolean hasConnectionPoolConfiguration() {
            return this.connectionPool != null;
        }

        public Boolean isEnabledTracing() {
            return this.enabledTracing;
        }

        public void setEnabledTracing(Boolean enabledTracing) {
            this.enabledTracing = enabledTracing;
        }

        public boolean isAuditEnabled() {
            return this.auditEnabled;
        }

        public void setAuditEnabled(boolean auditEnabled) {
            this.auditEnabled = auditEnabled;
        }

        public String getCodService() {
            return this.codService;
        }

        public void setCodService(String codService) {
            this.codService = codService;
        }

        public boolean isBfdEnabled() {
            return this.bfdEnabled;
        }

        public void setBfdEnabled(boolean bfdEnabled) {
            this.bfdEnabled = bfdEnabled;
        }

        public String getOperationName() {
            return this.operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }
    }
}
