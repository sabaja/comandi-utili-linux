//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.config.http;

public interface IHttpConnectionPoolConfigurationAware {
    HttpConnectionPoolConfiguration getConnectionPoolConfiguration();

    void setConnectionPoolConfiguration(HttpConnectionPoolConfiguration configuration);

    boolean hasConnectionPoolConfiguration();
}
