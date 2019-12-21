    private List<TPadRevisionePraticaEntity> retrievePraticaRevisioneList(BigInteger idPratica, BigInteger numVersion) {
        Specification<TPadRevisionePraticaEntity> specification =
                PraticaRevisioneSpecification.withIdPraticaEqualsTo(idPratica);
        return numVersion == null
                ? versioningDelegate.findCurrentAll(revisionePraticaRepository, specification)
                : versioningDelegate.findHistoricalAll(revisionePraticaRepository, numVersion, specification);
    }
	
	
	package com.intesasanpaolo.bear.lmbe0.motori.connector.jpa.specification;

import com.intesasanpaolo.bear.lmbe0.motori.constants.MotoriConstants;
import com.intesasanpaolo.bear.lmbe0.motori.model.TPadRevisionePraticaEntity;
import java.math.BigInteger;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class PraticaRevisioneSpecification {

    public static Specification<TPadRevisionePraticaEntity> withIdPraticaEqualsTo(final BigInteger idPratica) {
        return (idPratica != null) ? (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(MotoriConstants.ID_PRATICA), idPratica) : null;
    }
}
_