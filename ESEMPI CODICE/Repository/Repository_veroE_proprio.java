package com.intesasanpaolo.bear.lmbe0.pratica.connector.jpa;

import com.intesasanpaolo.bear.connector.jpa.JPAConnector;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.domain.CodTipoPratica;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.domain.FasePratica;
import com.intesasanpaolo.bear.lmbe0.pratica.model.ErroriTMPLEntity;
import com.intesasanpaolo.bear.lmbe0.pratica.model.bin.ControlliSalvataggioInputBin;
import com.intesasanpaolo.bear.lmbe0.pratica.model.bin.GetCompletezzaBinIn;
import com.intesasanpaolo.bear.lmbe0.pratica.model.domain.CodTipoErrore;
import com.intesasanpaolo.bear.lmbe0.pratica.model.domain.CodValidationStep;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import static com.intesasanpaolo.bear.lmbe0.pratica.connector.jpa.specification.ErroriTMPLSpecification.*;

@Repository
@Transactional(readOnly = true)
public interface ErroriTMPLRepository extends JPAConnector<ErroriTMPLEntity, BigInteger>, JpaSpecificationExecutor<ErroriTMPLEntity> {

    default List<ErroriTMPLEntity> retrieveListaErroriTMPL(ControlliSalvataggioInputBin inputBin, LocalDate dataInizio, LocalDate dataFine, CodValidationStep codStep) {
        CodTipoPratica codTipoPratica = inputBin.getCodTipoPratica() != null ? CodTipoPratica.fromValue(inputBin.getCodTipoPratica().toString()) : null;
        final Specification<ErroriTMPLEntity> specification = Specification
                .where(withCodAbiEqualsTo(inputBin.getCodAbi()))
                .and(withCodFasePraticaEqualsTo(inputBin.getCodFasePratica()))
                .and(withCodStepEqualsTo(codStep))
                .and(withCodTipoProcessoEqualsTo(codTipoPratica))//TODO codTipoProcesso is not codTipoPratica
                .and(withDataFine(dataFine))
                .and(withDataInizio(dataInizio));
        return findAll(specification);
    }

    default List<ErroriTMPLEntity> retrieveListaErroriTMPL(GetCompletezzaBinIn id, LocalDate dataInizio, LocalDate dataFine, String codStep) {

        final Specification<ErroriTMPLEntity> specification = Specification
                .where(withCodAbiEqualsTo(id.getCodAbi()))
                .and(withCodFasePraticaEqualsTo(FasePratica.fromValue(id.getCodFase()))
                        .and(withCodStepEqualsTo(CodValidationStep.valueOf(codStep)))
                        .and(withCodTipoProcessoEqualsTo(id.getCodTipoPratica())
                                .and(withDataFine(dataFine))
                                .and(withDataInizio(dataInizio))));
        return findAll(specification);
    }

    default List<ErroriTMPLEntity> retrieveListaErroriTMPL(String codFasePratica, String codTipoProcesso, LocalDate dataInizio, LocalDate dataFine, String codStep) {
        final Specification<ErroriTMPLEntity> specification = Specification
                .where(withCodFasePraticaEqualsTo(FasePratica.fromValue(codFasePratica))
                        .and(withCodStepEqualsTo(CodValidationStep.valueOf(codStep)))
                        .and(withCodTipoProcessoEqualsTo(CodTipoPratica.fromValue(codTipoProcesso)))
                        .and(withDataFine(dataFine))
                        .and(withDataInizio(dataInizio)));
        return findAll(specification);
    }

    default List<ErroriTMPLEntity> findAllBy(String codErrore, FasePratica codFasePratica, CodValidationStep codStep,
                                             String codAbi, CodTipoPratica codTipoProcesso, CodTipoErrore codTipoErrore) {
        final Specification<ErroriTMPLEntity> specification = Specification
                .where(withCodErroreEqualsTo(codErrore))
                .and(withCodFasePraticaEqualsTo(codFasePratica))
                .and(withCodStepEqualsTo(codStep))
                .and(withCodAbiEqualsTo(codAbi))
                .and(withCodTipoProcessoEqualsTo(codTipoProcesso))
                .and(withCodTipoErroreEqualsTo(codTipoErrore));

        return findAll(specification);
    }
}