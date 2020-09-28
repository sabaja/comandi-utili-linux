//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.layers.connector;

public interface AuditableConnectorConfiguration extends TraceableConnectorConfiguration {
    String AUDIT_ENABLED_KEY = "auditEnabled";
    String COD_SERVICE_KEY = "codService";
    String BFD_ENABLED_KEY = "bfdEnabled";
    String OPERATION_NAME_KEY = "operationName";

    boolean isAuditEnabled();

    void setAuditEnabled(boolean auditEnabled);

    String getCodService();

    void setCodService(String codService);

    boolean isBfdEnabled();

    void setBfdEnabled(boolean bfdEnabled);

    String getOperationName();

    void setOperationName(String operationName);
}
