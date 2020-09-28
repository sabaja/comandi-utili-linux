//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.bear;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.connector.bear.ArchBearInternalConnectorConfigurationService.BearInternalConfiguration;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService.RestConfiguration;
import com.intesasanpaolo.bear.util.ReflectionUtils;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public abstract class BearInternalConnector<INPUT, OUTPUT, DTO, RESOURCE> {
    private static final String BEAN_SUFFIX = "_INTERNAL";
    protected final Logger logger = LoggerUtils.getLogger(this.getClass());
    @Autowired
    @Qualifier("BearConnectorConfiguration")
    ArchBearInternalConnectorConfigurationService configuration;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    private BearInternalRestConnector<INPUT, OUTPUT, DTO, RESOURCE> internalRestConnector;

    public BearInternalConnector() {
    }

    @PostConstruct
    private void init() {
        String connectorName = this.getClass().getSimpleName();
        BearInternalConfiguration myConfiguration = this.configuration.retrieveRestConfiguration(this.getClass().getSimpleName());
        if (myConfiguration == null) {
            throw new ExceptionInInitializerError();
        } else {
            this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for INTERNAL REST connector <{}>"), connectorName);
            RestConfiguration restConfig = new RestConfiguration(myConfiguration);

            try {
                this.internalRestConnector = new BearInternalRestConnector(connectorName + "_INTERNAL", restConfig);
                this.internalRestConnector.forceConnectorName(this.getClass().getSimpleName());
                this.beanFactory.autowireBean(this.internalRestConnector);
                this.internalRestConnector = (BearInternalRestConnector)this.beanFactory.initializeBean(this.internalRestConnector, connectorName + "_INTERNAL");
                this.internalRestConnector.setClazz(ReflectionUtils.getGenericTypeClass(this.getClass(), 3));
            } catch (BeansException var5) {
                this.internalRestConnector = null;
                throw new ExceptionInInitializerError(var5);
            }
        }
    }

    public OUTPUT call(INPUT input, IRestRequestTransformer<INPUT, DTO> requestTransformer, IRestResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
        if (this.internalRestConnector == null) {
            throw new UnsupportedOperationException("Internal Rest Connector not defined");
        } else {
            return this.internalRestConnector.call(input, requestTransformer, responseTransformer, args);
        }
    }
}
