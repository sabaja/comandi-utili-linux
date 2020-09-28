package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.api.DettaglioBilancioBaseApi;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.resource.DettaglioBilancioResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BilanciGetDettaglioBilancioResponseTransformer implements IRestResponseTransformer<DettaglioBilancioResource, DettaglioBilancioBaseApi> {
    @Override
    public DettaglioBilancioBaseApi transform(RestConnectorResponse<DettaglioBilancioResource> restConnectorResponse) {
        return Optional.ofNullable(restConnectorResponse.getResponse())
                .map(HttpEntity::getBody)
                .map(DettaglioBilancioResource::getDettaglioBilancio)
                .orElse(new DettaglioBilancioBaseApi());
    }
}
