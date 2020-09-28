//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.rest.transformer;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;

public interface IRestRequestTransformer<INPUT, DTO> {
    RestConnectorRequest<DTO> transform(INPUT om, Object... args);
}
