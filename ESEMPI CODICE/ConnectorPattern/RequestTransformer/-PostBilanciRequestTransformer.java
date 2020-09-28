package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.exception.AnalisiException;
import com.intesasanpaolo.bear.lmbe0.api.indagini.resource.AvvioIndaginiResource;
import org.springframework.stereotype.Service;

@Service
public class package com.intesasanpaolo.bear.lmbe0.analisi.connector.internal;

import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.model.bin.PostBilanciInputBin;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.dto.PostBilancioDto;
import com.intesasanpaolo.bear.lmbe0.api.bilanci.enums.TipoBilancioGenericoEnum;
import com.intesasanpaolo.bear.lmbe0.api.soggetto.api.SoggettoPraticaApi;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PostBilanciRequestTransformer implements
        IRestRequestTransformer<PostBilanciInputBin, PostBilancioDto> {
    private static final String ID_PRATICA = "idPratica";
    private static final String COD_VISTA = "codVista";
    private static final String COD_AMBITO = "codAmbito";
    private static final String COD_AZIONE = "codAzione";

    @Override
    public RestConnectorRequest<PostBilancioDto> transform(PostBilanciInputBin inputBin, Object... args) {
        final RestConnectorRequest<PostBilancioDto> request = new RestConnectorRequest<>();
        request.setMethod(HttpMethod.POST);

        Optional.ofNullable(inputBin.getIdPratica())
                .ifPresent(idPratica -> request.addParams(ID_PRATICA, idPratica.toString()));

        request.setQueryParams(createQueryParams(inputBin));
        request.setRequest(createBilanciDto(inputBin));
        return request;
    }

    private Map<String, String> createQueryParams(PostBilanciInputBin inputBin) {
        Map<String, String> params = new HashMap<>();
        Optional.ofNullable(inputBin.getCodAmbito())
                .ifPresent(codAmbito -> params.put(COD_AMBITO, codAmbito.toString()));
        Optional.ofNullable(inputBin.getCodAzione())
                .ifPresent(codAzione -> params.put(COD_AZIONE, codAzione.toString()));
        Optional.ofNullable(inputBin.getCodVista())
                .ifPresent(codVista -> params.put(COD_VISTA, codVista.toString()));

        return params;
    }

    private PostBilancioDto createBilanciDto(PostBilanciInputBin inputBin) {
        PostBilancioDto bilancioDto = new PostBilancioDto();
        bilancioDto.setCodAbi(Optional.ofNullable(inputBin).map(PostBilanciInputBin::getSoggettoPeer).map(SoggettoPraticaApi::getCodAbi).orElse(null));
        bilancioDto.setCodNsg(Optional.ofNullable(inputBin).map(PostBilanciInputBin::getSoggettoPeer).map(SoggettoPraticaApi::getCodNsg).orElse(null));
        bilancioDto.setCodSnsg(Optional.ofNullable(inputBin).map(PostBilanciInputBin::getSoggettoPeer).map(SoggettoPraticaApi::getCodSnsg).orElse(null));
        bilancioDto.setDesDenominazione(Optional.ofNullable(inputBin).map(PostBilanciInputBin::getSoggettoPeer).map(SoggettoPraticaApi::getDesDenominazione).orElse(null));
        bilancioDto.setCodTipoBilancioGenerico(TipoBilancioGenericoEnum.INDIVIDUALE);
        bilancioDto.setDatiChiamata(Optional.ofNullable(inputBin.getDatiChiamataDto()).orElse(null));
        return bilancioDto;
    }
}
 implements IRestResponseTransformer<AvvioIndaginiResource, AvvioIndaginiResource> {
    @Override
    public AvvioIndaginiResource transform(RestConnectorResponse<AvvioIndaginiResource> restConnectorResponse) {
        if (!restConnectorResponse.getResponse().getStatusCode().is2xxSuccessful())
            throw AnalisiException.putIndaginiFailed();

        return restConnectorResponse.getResponse().getBody();
    }
}
