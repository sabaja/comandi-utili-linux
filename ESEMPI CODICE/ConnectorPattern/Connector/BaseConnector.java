//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.layers.connector;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.layers.connector.request.BaseConnectorRequest;
import com.intesasanpaolo.bear.layers.connector.response.BaseConnectorResponse;
import org.slf4j.Logger;

public abstract class BaseConnector<INPUT extends BaseConnectorRequest, OUTPUT extends BaseConnectorResponse> {
    protected final Logger logger = LoggerUtils.getLogger(this.getClass());

    public BaseConnector() {
    }

    protected void doPreExecute(INPUT request) {
        this.logger.debug(LoggerUtils.formatArchRow("STARTING doPreExecute"));
    }

    protected OUTPUT doExecute(INPUT request) {
        return null;
    }

    protected OUTPUT doPostExecute(INPUT request, OUTPUT response) {
        return response;
    }

    protected OUTPUT execute(INPUT request) {
        this.doPreExecute(request);
        OUTPUT output = this.doExecute(request);
        return this.doPostExecute(request, output);
    }
}
