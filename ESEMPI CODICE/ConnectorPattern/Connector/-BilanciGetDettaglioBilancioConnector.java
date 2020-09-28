package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal;

import com.intesasanpaolo.bear.connector.bear.BearInternalConnector;
import com.intesasanpaolo.bear.lmbe0.analisi.model.bin.GetBilanciInputBin;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.api.DettaglioBilancioBaseApi;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.resource.DettaglioBilancioResource;
import org.springframework.stereotype.Service;

@Service
public class BilanciGetDettaglioBilancioConnector extends BearInternalConnector<
        GetBilanciInputBin, DettaglioBilancioBaseApi,
        Void, DettaglioBilancioResource> {
}
