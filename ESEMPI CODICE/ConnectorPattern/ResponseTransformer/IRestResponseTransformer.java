//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.rest.transformer;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;

public interface IRestResponseTransformer<RESOURCE, OUTPUT> {
    OUTPUT transform(RestConnectorResponse<RESOURCE> restConnectorResponse);
}
