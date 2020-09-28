package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.bin.PutIndaginiInputBin;
import com.intesasanpaolo.bear.lmbe0.api.indagini.dto.domain.CodIndagine;
import com.intesasanpaolo.bear.lmbe0.api.indagini.dto.domain.CodStepRichiesta;
import com.intesasanpaolo.bear.lmbe0.api.indagini.dto.domain.TipoAzione;
import com.intesasanpaolo.bear.lmbe0.api.indagini.dto.indagini.AvvioIndagineSingolaDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PutCRAndamentaleRequestTransformer implements IRestRequestTransformer<PutIndaginiInputBin, AvvioIndagineSingolaDto> {

    @Override
    public RestConnectorRequest<AvvioIndagineSingolaDto> transform(PutIndaginiInputBin inputBin, Object... args) {
        RestConnectorRequest<AvvioIndagineSingolaDto> request = new RestConnectorRequest<>();
        request.setMethod(HttpMethod.PUT);
        request.addParams("idPratica", inputBin.getIdPratica().toString());
        request.addParams("codNsgProgressivo", inputBin.getCodNsg());
        AvvioIndagineSingolaDto requestBody = createRequestDto(inputBin);
        request.setRequest(requestBody);

        return request;
    }

    private AvvioIndagineSingolaDto createRequestDto(PutIndaginiInputBin inputBin) {
        AvvioIndagineSingolaDto body = new AvvioIndagineSingolaDto();
        body.setTipoAzione(TipoAzione.CALCOLO);
        body.setCodStepRichiesta(CodStepRichiesta.ANALISI);
        body.setCodIndagine(Collections.singletonList(CodIndagine.CRAN));
        body.setCodVista(inputBin.getCodVista());
        body.setDatiChiamata(inputBin.getDatiChiamataDto());

        return body;
    }

}
