package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.api.SintesiBilancioBaseApi;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.resource.BilanciResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostBilanciResponseTransformer implements IRestResponseTransformer<BilanciResource, List<SintesiBilancioBaseApi>> {
    @Override
    public List<SintesiBilancioBaseApi> transform(RestConnectorResponse<BilanciResource> restConnectorResponse) {
        return Optional.ofNullable(restConnectorResponse.getResponse())
                .map(HttpEntity::getBody)
                .map(BilanciResource::getListaSintesiBilancio)
                .orElse(Collections.emptyList());
    }
}
