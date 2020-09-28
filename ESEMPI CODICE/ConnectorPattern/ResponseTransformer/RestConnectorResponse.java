//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.rest.model;

import com.intesasanpaolo.bear.layers.connector.response.BaseConnectorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class RestConnectorResponse<T> extends BaseConnectorResponse {
    private ResponseEntity<T> response;
    private HttpHeaders httpHeaders;

    public RestConnectorResponse() {
    }

    public ResponseEntity<T> getResponse() {
        return this.response;
    }

    public void setResponse(ResponseEntity<T> response) {
        this.response = response;
    }

    public HttpHeaders getHttpHeaders() {
        return this.httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public RestConnectorResponse<T> getResult() {
        return this;
    }
}
