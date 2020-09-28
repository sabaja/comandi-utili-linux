package com.intesasanpaolo.bear.lmbe0.analisi.service.facade;

import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.BilanciGetDettaglioBilancioConnector;
import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers.BilanciGetDettaglioBilancioRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers.BilanciGetDettaglioBilancioResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.model.bin.GetBilanciInputBin;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.api.DettaglioBilancioBaseApi;
import com.intesasanpaolo.bear.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BilanciGetDettaglioBilancioServiceFacade extends BaseService {
    private final BilanciGetDettaglioBilancioConnector connector;
    private final BilanciGetDettaglioBilancioRequestTransformer requestTransformer;
    private final BilanciGetDettaglioBilancioResponseTransformer responseTransformer;

    public DettaglioBilancioBaseApi retrieveDettaglioBilancio(GetBilanciInputBin inputBin) {
        return connector.call(inputBin, requestTransformer, responseTransformer);
    }

}
