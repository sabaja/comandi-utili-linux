package com.intesasanpaolo.bear.lmbe0.pratica.connector.jpa.specification;

import com.intesasanpaolo.bear.lmbe0.lib.core.standard.domain.CodTipoPratica;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.domain.FasePratica;
import com.intesasanpaolo.bear.lmbe0.pratica.model.ErroriTMPLEntity;
import com.intesasanpaolo.bear.lmbe0.pratica.model.domain.CodTipoErrore;
import com.intesasanpaolo.bear.lmbe0.pratica.model.domain.CodValidationStep;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@UtilityClass
public class ErroriTMPLSpecification {
    private static final String COD_ABI = "codAbi";
    private static final String COD_TIPO_PROCESSO = "codTipoProcesso";
    private static final String COD_FASE_PRATICA = "codFasePratica";
    private static final String COD_STEP = "codStep";
    private static final String DAT_INIZIO = "datInizio";
    private static final String DAT_FINE = "datFine";

    public static Specification<ErroriTMPLEntity> withCodAbiEqualsTo(final String codAbi) {
        return isNull(codAbi)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codAbi"), codAbi);
    }

    public static Specification<ErroriTMPLEntity> withCodFasePraticaEqualsTo(final FasePratica codFasePratica) {
        return isNull(codFasePratica)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codFasePratica"), codFasePratica);
    }

    public static Specification<ErroriTMPLEntity> withCodStepEqualsTo(final CodValidationStep codStep) {
        return isNull(codStep)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codStep"), codStep);
    }

    public static Specification<ErroriTMPLEntity> withDataInizio(final LocalDate dataInizio) {
        return isNull(dataInizio)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("datInizio"), dataInizio);
    }

    public static Specification<ErroriTMPLEntity> withDataFine(final LocalDate dataFine) {
        return isNull(dataFine)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("datFine"), dataFine);
    }

    public static Specification<ErroriTMPLEntity> withCodErroreEqualsTo(final String codErrore) {
        return isNull(codErrore)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codErrore"), codErrore);
    }

    public static Specification<ErroriTMPLEntity> withCodTipoProcessoEqualsTo(final CodTipoPratica codTipoProcesso) {
        return isNull(codTipoProcesso)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codTipoProcesso"), codTipoProcesso);
    }

    public static Specification<ErroriTMPLEntity> withCodTipoErroreEqualsTo(final CodTipoErrore codTipoErrore) {
        return isNull(codTipoErrore)
                ? null
                : (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("codTipoErrore"), codTipoErrore);
    }

    public static Specification<ErroriTMPLEntity> withALotOfFields(FasePratica codFasePratica, CodTipoPratica codTipoPratica, String codAbi, CodValidationStep codStep, LocalDate datInizio, LocalDate datFine) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(COD_ABI), codAbi),
                criteriaBuilder.equal(root.get(COD_TIPO_PROCESSO), codTipoPratica),
                criteriaBuilder.equal(root.get(COD_FASE_PRATICA), codFasePratica),
                criteriaBuilder.equal(root.get(COD_STEP), codStep),
                criteriaBuilder.lessThanOrEqualTo(root.get(DAT_INIZIO), datInizio),
                criteriaBuilder.greaterThanOrEqualTo(root.get(DAT_FINE), datFine));
    }
}