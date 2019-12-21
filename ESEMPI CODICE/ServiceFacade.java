package com.intesasanpaolo.bear.lmbe0.orchestrazione.service.facade;

import com.intesasanpaolo.bear.lmbe0.orchestrazione.connector.rest.AggiornaDatiPraticaManualeConnector;
import com.intesasanpaolo.bear.lmbe0.orchestrazione.connector.rest.transformers.AggiornaDatiPraticaManualeRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.orchestrazione.connector.rest.transformers.AggiornaDatiPraticaManualeResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.orchestrazione.dto.CreazionePraticaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class PutPraticheServiceFacade {
    private final AggiornaDatiPraticaManualeConnector connector;
    private final AggiornaDatiPraticaManualeRequestTransformer requestTransformer;
    private final AggiornaDatiPraticaManualeResponseTransformer responseTransformer;

    public void callPutPraitcheOnPraticaMS(CreazionePraticaRequestDto modelIn, BigInteger idPratica) {
        connector.call(modelIn, requestTransformer, responseTransformer, idPratica);
    }
}
