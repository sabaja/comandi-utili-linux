package com.intesasanpaolo.bear.lmbe0.analisi.service.facade;


import com.intesasanpaolo.bear.lmbe0.analisi.bin.PutIndaginiInputBin;
import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.PutCRAndamentaleConnector;
import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers.PutCRAndamentaleRequestTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.connector.internal.transformers.PutCRAndamentaleResponseTransformer;
import com.intesasanpaolo.bear.lmbe0.analisi.exception.CommonException;
import com.intesasanpaolo.bear.lmbe0.api.indagini.resource.AvvioIndaginiResource;
import com.intesasanpaolo.bear.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PutCRAndamentaleServiceFacade extends BaseService {

    @Autowired
    private PutCRAndamentaleConnector connector;
    @Autowired
    private PutCRAndamentaleRequestTransformer requestTransformer;
    @Autowired
    private PutCRAndamentaleResponseTransformer responseTransformer;

    public AvvioIndaginiResource recalculateCRAndamentale(PutIndaginiInputBin inputBin) {
        logger.info("Calling PUT Indagine Puntuale with IdPratica = {}", inputBin.getIdPratica());
        try {
            return connector.call(inputBin, requestTransformer, responseTransformer);
        }catch (Exception e){
            logger.error("Error calling PUT Indagine Puntuale with request: {} ", inputBin);
            logger.error("{}", e.getMessage());
            throw CommonException.calcoloECAMNotFound();
        }
    }
}