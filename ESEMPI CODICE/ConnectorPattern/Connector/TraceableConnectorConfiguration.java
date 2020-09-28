//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.layers.connector;

public interface TraceableConnectorConfiguration extends ConnectorConfiguration {
    String ENABLED_TRACING_KEY = "enabledTracing";

    Boolean isEnabledTracing();

    void setEnabledTracing(Boolean enabledTracing);
}
