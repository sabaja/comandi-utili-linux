package com.intesasanpaolo.bear.lmbe0.pratica.service;


import com.intesasanpaolo.bear.lmbe0.pratica.connector.jpa.ErroriTMPLRepository;
import com.intesasanpaolo.bear.lmbe0.pratica.connector.jpa.specification.ErroriTMPLSpecification;
import com.intesasanpaolo.bear.lmbe0.pratica.model.ErroriTMPLEntity;
import com.intesasanpaolo.bear.lmbe0.pratica.model.bin.ControlliSalvataggioInputBin;
import com.intesasanpaolo.bear.lmbe0.pratica.model.bin.GetCompletezzaBinIn;
import com.intesasanpaolo.bear.lmbe0.pratica.model.bin.GetErroriTemplateBinIn;
import com.intesasanpaolo.bear.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class GetErroriTMPLService extends BaseService {
    private ErroriTMPLRepository erroriTMPLRepository;

    public List<ErroriTMPLEntity> retrieveListaErroriTMPL(ControlliSalvataggioInputBin modelIn) {
        return erroriTMPLRepository.findAll(creaSpeficationErroriTMPLentity(modelIn));
    }

    public List<ErroriTMPLEntity> retrieveListaErroriTMPL(GetCompletezzaBinIn modelIn) {
        return  erroriTMPLRepository.retrieveListaErroriTMPL(modelIn,LocalDate.now(),LocalDate.now(),modelIn.getCodStep());
    }

    public List<ErroriTMPLEntity> retrieveListaErroriTMPL(GetErroriTemplateBinIn modelIn) {
        return erroriTMPLRepository.retrieveListaErroriTMPL(modelIn.getFasePratica().getValue(), modelIn.getCodTipoPratica().getValue(), LocalDate.now(), LocalDate.now(), modelIn.getCodStep().getValue());
    }

    private Specification<ErroriTMPLEntity> creaSpeficationErroriTMPLentity(ControlliSalvataggioInputBin modelIn) {
        return Specification
                .where(ErroriTMPLSpecification.withCodAbiEqualsTo(modelIn.getCodAbi())
                        .and(ErroriTMPLSpecification.withCodFasePraticaEqualsTo(modelIn.getCodFasePratica()))
                        .and(ErroriTMPLSpecification.withCodStepEqualsTo(modelIn.getCodStep()))
                        .and(ErroriTMPLSpecification.withCodTipoProcessoEqualsTo(modelIn.getCodTipoPratica()))
                        .and(ErroriTMPLSpecification.withDataFine(LocalDate.now()))
                        .and(ErroriTMPLSpecification.withDataInizio(LocalDate.now())));

    }
}

