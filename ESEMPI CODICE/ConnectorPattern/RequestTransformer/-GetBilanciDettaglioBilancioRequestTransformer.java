package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.model.bin.GetBilanciInputBin;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BilanciGetDettaglioBilancioRequestTransformer implements
        IRestRequestTransformer<GetBilanciInputBin, Void> {
    private static final String ID_PRATICA = "idPratica";
    private static final String COD_VISTA = "codVista";
    private static final String NUM_PROGRESSIVO_BILANCIO = "numProgressivoBilancio";

    @Override
    public RestConnectorRequest<Void> transform(GetBilanciInputBin inputBin, Object... args) {
        final RestConnectorRequest<Void> request = new RestConnectorRequest<>();
        request.setMethod(HttpMethod.GET);
        request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        request.addParams(ID_PRATICA, String.valueOf(inputBin.getIdPratica()));
        request.addParams(NUM_PROGRESSIVO_BILANCIO, String.valueOf(inputBin.getNumProgressivoBilancio()));
        request.setQueryParams(createQueryParams(inputBin));
        return request;
    }

    private Map<String, String> createQueryParams(GetBilanciInputBin inputBin) {
        Map<String, String> queryParams = new HashMap<>();
        Optional.ofNullable(inputBin.getCodVista())
                .ifPresent(codVista -> queryParams.put(COD_VISTA, codVista.name()));

        return queryParams;
    }
}
