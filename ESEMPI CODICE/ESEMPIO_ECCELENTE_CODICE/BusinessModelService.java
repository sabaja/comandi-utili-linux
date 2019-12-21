package com.intesasanpaolo.bear.lmbe0.fidogaranzia.service;

import com.intesasanpaolo.bear.lmbe0.fidogaranzia.cache.BusinessModelCache;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.connector.jpa.BusinessModelRepository;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.exception.FidoGaranziaControllerException;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.service.util.CacheUtils;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.domain.CodStatoAvanzamento;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetail;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.support.dto.StatoAvanzamento;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.versioning.jpa.VersioningDelegate;
import com.intesasanpaolo.bear.lmbe0.model.BusinessModel;
import com.intesasanpaolo.bear.lmbe0.model.Fido;
import com.intesasanpaolo.bear.lmbe0.model.bin.BusinessModelBin;
import com.intesasanpaolo.bear.lmbe0.model.bin.GetBusinessModelOutputBin;
import com.intesasanpaolo.bear.lmbe0.model.bin.ImportoBin;
import com.intesasanpaolo.bear.lmbe0.model.bin.ImportoConControvaloreBin;
import com.intesasanpaolo.bear.lmbe0.model.domain.CodStato;
import com.intesasanpaolo.bear.lmbe0.model.domain.CodTipoOperazioni;
import com.intesasanpaolo.bear.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.intesasanpaolo.bear.lmbe0.fidogaranzia.connector.jpa.specification.SpecificationBuilder.buildBusinessModelSpecification;
import static com.intesasanpaolo.bear.lmbe0.model.domain.CodModalitaControlloSalvataggio.DRAFT;
import static java.math.BigInteger.ONE;

@Service
public class BusinessModelService extends BaseService {

    private static String FIDO_NOT_EXIST_EXCEPTION_MSG = "Errore! Il fido non è stato trovato.";
    private static String FIDO_NOT_EXIST_EXCEPTION_CODE = "FIDOE006";
    private static String MUST_BE_GREATER_THAN_ZERO_EXCEPTION_MSG = "Il valore dell'importo %s deve essere maggiore di 0";
    private static String MUST_BE_GREATER_THAN_ZERO_EXCEPTION_CODE = "FIGAE021";
    private static String BUSINESS_MODEL_ALREADY_EXIST_EXCEPTION_MSG = "Business Model è gia presente per questo fido";
    private static String BUSINESS_MODEL_ALREADY_EXIST_EXCEPTION_CODE = "FIGAE020";
    private static String BUSINESS_MODEL_NOT_EXIST_EXCEPTION_MSG = "Business Model non trovato";
    private static String BUSINESS_MODEL_NOT_EXIST_EXCEPTION_CODE = "FIGAE023";
    private static String PHASE_PRATICA_IS_NULL_EXCEPTION_MSG = "Errore! La fase pratica è null!";
    private static String PHASE_PRATICA_IS_NULL_EXCEPTION_CODE = "FIGAE022";
    private static String OPERATION_NOT_ALLOWED_MSG = "Errore, operazione non consentita";
    private static String OPERATION_NOT_ALLOWED_CODE = "FIGAE024";
    @Autowired
    BusinessModelCache businessModelCache;
    @Autowired
    private CacheUtils cacheUtils;
    @Autowired
    private BusinessModelRepository businessModelRepository;
    @Autowired
    private VersioningDelegate versioning;
    @Autowired
    private FidoService fidoService;
    private Predicate<BigDecimal> isBiggerThanZero = value -> value.compareTo(BigDecimal.ZERO) > 0;

    @Transactional(readOnly = true)
    public GetBusinessModelOutputBin getBusinessModelResource(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) {
        Fido fido = getFido(idPratica, numFido, numVersione);
        BusinessModel businessModel = getBusinessModel(idPratica, numFido, numVersione);
        return GetBusinessModelOutputBin.builder()
                .idPratica(idPratica)
                .idFido(numFido)
                .numProgressivoPool(businessModel.getNumProgressivoPool())
                .codStato(CodStato.findByValue(businessModel.getCodStato()))
                .flgHTC(parseBooleanFromInt(businessModel.getFlgHTC()))
                .flgHTCS(parseBooleanFromInt(businessModel.getFlgHTCS()))
                .flgOther(parseBooleanFromInt(businessModel.getFlgOther()))
                .impHTC(createImportoConControvaloreBin(fido, businessModel.getImpHTC()))
                .impHTCS(createImportoConControvaloreBin(fido, businessModel.getImpHTCS()))
                .impOther(createImportoConControvaloreBin(fido, businessModel.getImpOther()))
                .datCessioneHTC(businessModel.getDatCessioneHTC())
                .datCessioneHTCS(businessModel.getDatCessioneHTCS())
                .datCessioneOther(businessModel.getDatCessioneOther())
                .codRichiestaCessioneHTC(businessModel.getCodRichiestaCessioneHTC())
                .codRichiestaCessioneHTCS(businessModel.getCodRichiestaCessioneHTCS())
                .codRichiestaCessioneOther(businessModel.getCodRichiestaCessioneOTHER())
                .build();
    }

    @Transactional
    public BusinessModelBin createBusinessModel(BusinessModelBin bin, PraticaDetail praticaDetail) {
        if (!isFidoExist(bin.getIdPratica(), bin.getNumFido(), praticaDetail.getNumVersionCurrent())) {
            throw new FidoGaranziaControllerException(FIDO_NOT_EXIST_EXCEPTION_MSG, FIDO_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND);
        }
        if (isBusinessModelExist(bin.getIdPratica(), bin.getNumFido(), praticaDetail.getNumVersionCurrent())) {
            throw new FidoGaranziaControllerException(BUSINESS_MODEL_ALREADY_EXIST_EXCEPTION_MSG, BUSINESS_MODEL_ALREADY_EXIST_EXCEPTION_CODE, HttpStatus.BAD_REQUEST);
        }
        setImportosByCodStato(bin, praticaDetail);
        BusinessModel businessModel = buildAndSaveBusinessModel(bin, praticaDetail.getNumVersionCurrent());
        return BusinessModelBin.builder()
                .idPratica(bin.getIdPratica())
                .numFido(bin.getNumFido())
                .businessModel(businessModel)
                .build();
    }

    @Transactional
    public BusinessModelBin updateBusinessModel(BusinessModelBin bin, PraticaDetail praticaDetail) {

        BusinessModel sourceBusinessModel;

        if (!isFidoExist(bin.getIdPratica(), bin.getNumFido(), praticaDetail.getNumVersionCurrent())) {
            throw new FidoGaranziaControllerException(FIDO_NOT_EXIST_EXCEPTION_MSG, FIDO_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND);
        }
        if (!isBusinessModelExist(bin.getIdPratica(), bin.getNumFido(), praticaDetail.getNumVersionCurrent())) {
            throw new FidoGaranziaControllerException(BUSINESS_MODEL_NOT_EXIST_EXCEPTION_MSG, BUSINESS_MODEL_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND);
        }

        if ((bin.getCodTipoOperazioni() == CodTipoOperazioni.REVOCA) || (bin.getCodTipoOperazioni() == CodTipoOperazioni.NON_AUTORIZZA)) {
            throw new FidoGaranziaControllerException(OPERATION_NOT_ALLOWED_MSG, OPERATION_NOT_ALLOWED_CODE, HttpStatus.METHOD_NOT_ALLOWED);
        } else if (bin.getCodTipoOperazioni() == CodTipoOperazioni.ANNULLA_VARIAZIONE) {
            sourceBusinessModel = getBusinessModel(bin.getIdPratica(), bin.getNumFido(), praticaDetail.getNumVersionCurrent().subtract(ONE));
        } else {
            setImportosByCodStato(bin, praticaDetail);
            sourceBusinessModel = bin.getBusinessModel();
        }
        BusinessModel businessModel = buildAndUpdateBusinessModel(bin, praticaDetail.getNumVersionCurrent(), sourceBusinessModel);
        return BusinessModelBin.builder()
                .idPratica(bin.getIdPratica())
                .numFido(bin.getNumFido())
                .codTipoOperazioni(bin.getCodTipoOperazioni())
                .businessModel(businessModel)
                .build();
    }

    @Transactional
    public void deleteBusinessModel(BusinessModelBin bin, BigInteger numVersionCurrent) {
        if (!isFidoExist(bin.getIdPratica(), bin.getNumFido(), numVersionCurrent)) {
            throw new FidoGaranziaControllerException(FIDO_NOT_EXIST_EXCEPTION_MSG, FIDO_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND);
        }
        if (!isBusinessModelExist(bin.getIdPratica(), bin.getNumFido(), numVersionCurrent)) {
            throw new FidoGaranziaControllerException(BUSINESS_MODEL_NOT_EXIST_EXCEPTION_MSG, BUSINESS_MODEL_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND);
        }
        buildAndDeleteBusinessModel(bin, numVersionCurrent);
    }

    private void setImportosByCodStato(BusinessModelBin bin, PraticaDetail praticaDetail) {
        BusinessModel businessModel = bin.getBusinessModel();
        boolean isCodStatoDelibera = getCodStatoByNumCurrentVersion(praticaDetail) == CodStatoAvanzamento.DELIBERA;
        if (isCodStatoDelibera) {
            businessModel.setImpHTC(getImporto(businessModel.getFlgHTC(), businessModel.getImpHTC(), "HTC"));
            businessModel.setImpHTCS(getImporto(businessModel.getFlgHTCS(), businessModel.getImpHTCS(), "HTCS"));
            businessModel.setImpOther(getImporto(businessModel.getFlgOther(), businessModel.getImpOther(), "OTHER"));
        } else {
            businessModel.setImpHTCS(getImporto(businessModel.getFlgHTCS(), businessModel.getImpHTCS(), "HTCS"));
            businessModel.setImpOther(getImporto(businessModel.getFlgOther(), businessModel.getImpOther(), "OTHER"));
        }
    }

    private BigDecimal getImporto(Integer impFlag, BigDecimal impValue, String impName) {
        return parseBooleanFromInt(impFlag) ?
                Optional.ofNullable(impValue)
                        .filter(isBiggerThanZero)
                        .orElseThrow(() -> new FidoGaranziaControllerException(String.format(MUST_BE_GREATER_THAN_ZERO_EXCEPTION_MSG, impName),
                                MUST_BE_GREATER_THAN_ZERO_EXCEPTION_CODE, HttpStatus.INTERNAL_SERVER_ERROR))
                :
                BigDecimal.ZERO;
    }

    private BusinessModel buildAndSaveBusinessModel(BusinessModelBin bin, BigInteger numVersionCurrent) {
        BusinessModel businessModel = bin.getBusinessModel();
        businessModel.setNumVersionReference(numVersionCurrent);
        String cacheKey = bin.getModalitaControlloSalvataggio() == DRAFT ? cacheUtils.assembleCacheKey(bin.getIdPratica(), bin.getNumFido(), bin.getIdSessione()) : StringUtils.EMPTY;
        return cacheUtils
                .cacheOrSaveEntity(cacheKey, bin, businessModel, businessModelRepository, businessModelCache);
    }

    private BusinessModel buildAndUpdateBusinessModel(BusinessModelBin bin, BigInteger numVersionCurrent, BusinessModel sourceBusinessModel) {
        BusinessModel destinationBusinessModel = getBusinessModel(bin.getIdPratica(), bin.getNumFido(), numVersionCurrent);
        sourceBusinessModel.setNumVersionReference(numVersionCurrent);
        sourceBusinessModel.setId(destinationBusinessModel.getId());
        if (destinationBusinessModel.getCodStato().equals(CodStato.IN_ESSERE.getValue())) {
            sourceBusinessModel.setCodStato(CodStato.MODIFICATO.getValue());
        }
        String cacheKey = bin.getModalitaControlloSalvataggio() == DRAFT ? cacheUtils.assembleCacheKey(bin.getIdPratica(), bin.getNumFido(), bin.getIdSessione()) : StringUtils.EMPTY;
        return cacheUtils.cacheOrSaveEntity(cacheKey, bin, sourceBusinessModel, businessModelRepository, businessModelCache);
    }

    private void buildAndDeleteBusinessModel(BusinessModelBin bin, BigInteger numVersionCurrent) {
        BusinessModel businessModel = getBusinessModel(bin.getIdPratica(), bin.getNumFido(), numVersionCurrent);
        businessModel.setNumVersionReference(numVersionCurrent);
        businessModelRepository.delete(businessModel);
    }

    private ImportoConControvaloreBin createImportoConControvaloreBin(Fido fido, BigDecimal amount) {
        ImportoBin importo = new ImportoBin(fido.getCodDivisa(), amount);
        ImportoBin controvalore = new ImportoBin();
        controvalore.setCodDivisa("EUR");
        if (amount != null && fido.getNumCambio() != null) {
            controvalore.setValore(fido.getNumCambio().multiply(amount));
        }
        return new ImportoConControvaloreBin(importo, controvalore);
    }

    private CodStatoAvanzamento getCodStatoByNumCurrentVersion(PraticaDetail praticaDetail) {
        BigInteger numVersioneCurrent = praticaDetail.getNumVersionCurrent();

        return praticaDetail.getStatiAvanzamento().stream()
                .filter(e -> e.getNumVersione().compareTo(numVersioneCurrent) == 0)
                .findFirst()
                .map(StatoAvanzamento::getCodStatoAvanzamento)
                .orElseThrow(() -> new FidoGaranziaControllerException(PHASE_PRATICA_IS_NULL_EXCEPTION_MSG, PHASE_PRATICA_IS_NULL_EXCEPTION_CODE, HttpStatus.NOT_FOUND));
    }

    private Fido getFido(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) throws FidoGaranziaControllerException {
        return fidoService.findFido(idPratica, numFido, numVersione)
                .stream().filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new FidoGaranziaControllerException(FIDO_NOT_EXIST_EXCEPTION_MSG, FIDO_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND));
    }

    private Boolean isFidoExist(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) {
        return fidoService.findFido(idPratica, numFido, numVersione)
                .stream()
                .anyMatch(Objects::nonNull);
    }

    private BusinessModel getBusinessModel(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) {
        return getBusinessModels(idPratica, numFido, numVersione)
                .stream().filter(Objects::nonNull).findFirst()
                .orElseThrow(() -> new FidoGaranziaControllerException(BUSINESS_MODEL_NOT_EXIST_EXCEPTION_MSG, BUSINESS_MODEL_NOT_EXIST_EXCEPTION_CODE, HttpStatus.NOT_FOUND));
    }

    private Boolean isBusinessModelExist(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) {
        return getBusinessModels(idPratica, numFido, numVersione)
                .stream().anyMatch(Objects::nonNull);
    }

    private List<BusinessModel> getBusinessModels(BigInteger idPratica, BigInteger numFido, BigInteger numVersione) {
        return (numVersione == null) ?
                versioning.findCurrentAll(businessModelRepository, buildBusinessModelSpecification(idPratica, numFido)) :
                versioning.findHistoricalAll(businessModelRepository, numVersione, buildBusinessModelSpecification(idPratica, numFido));
    }

    private Boolean parseBooleanFromInt(Integer value) {
        return value != null && value == 1;
    }
}
