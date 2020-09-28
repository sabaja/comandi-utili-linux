package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.exception.AnalisiException;
import com.intesasanpaolo.bear.lmbe0.api.indagini.resource.AvvioIndaginiResource;
import org.springframework.stereotype.Service;

@Service
public class PutCRAndamentaleResponseTransformer implements IRestResponseTransformer<AvvioIndaginiResource, AvvioIndaginiResource> {
    @Override
    public AvvioIndaginiResource transform(RestConnectorResponse<AvvioIndaginiResource> restConnectorResponse) {
        if (!restConnectorResponse.getResponse().getStatusCode().is2xxSuccessful())
            throw AnalisiException.putIndaginiFailed();

        return restConnectorResponse.getResponse().getBody();
    }
}
