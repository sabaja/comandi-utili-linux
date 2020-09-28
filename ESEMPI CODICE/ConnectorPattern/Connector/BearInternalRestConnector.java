//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.bear;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.connector.rest.connector.BaseRestConnector;
import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.core.interceptors.RequestContextHolder;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService.RestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BearInternalRestConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseRestConnector<INPUT, OUTPUT, DTO, RESOURCE> {
    private String connectorName;
    private RestConfiguration myConfiguration;
    private String forcedConnectorName;
    @Autowired
    @Qualifier("RestConfiguration")
    ArchRestConfigurationService configuration;

    public BearInternalRestConnector(String connectorName) {
        this.connectorName = connectorName;
    }

    public BearInternalRestConnector(String connectorName, RestConfiguration configuration) {
        this.connectorName = connectorName;
        this.myConfiguration = configuration;
        this.setName(connectorName);
    }

    protected void init() {
        if (this.myConfiguration == null) {
            this.myConfiguration = this.configuration.retrieveRestConfiguration(this.connectorName);
        }

        if (this.myConfiguration == null) {
            throw new ExceptionInInitializerError();
        } else {
            this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for REST connector <{}>"), this.connectorName);
            this.configure(this.myConfiguration);
        }
    }

    protected void doPreExecute(RestConnectorRequest<DTO> request) {
        try {
            String authorizationHeader = RequestContextHolder.getAuthorizationContext().getAuthorizationHeader();
            if (authorizationHeader != null) {
                request.addHeader("Authorization", authorizationHeader);
            }
        } catch (Exception var3) {
            this.logger.error(LoggerUtils.formatArchRow("error retrieving authorization header"));
        }

        super.doPreExecute(request);
    }

    protected String getConnectorType() {
        return "connectors.bearInternalConfigurations";
    }

    public String getConnectorName() {
        return this.forcedConnectorName != null ? this.forcedConnectorName : super.getConnectorName();
    }

    public void forceConnectorName(String connectorName) {
        this.forcedConnectorName = connectorName;
    }
}
