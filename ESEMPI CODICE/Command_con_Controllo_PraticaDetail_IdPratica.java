package com.intesasanpaolo.bear.lmbe0.motori.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetail;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetailHolder;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.marker.PraticaVersionNeeded;
import com.intesasanpaolo.bear.lmbe0.motori.model.bin.DataRevisioneBin;
import com.intesasanpaolo.bear.lmbe0.motori.service.DataRevisioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PutDataRevisioneCommand extends BaseCommand<Void> implements
        PraticaDetailHolder, PraticaVersionNeeded {

    @Autowired
    private DataRevisioneService service;
    private DataRevisioneBin modelIn;
    private PraticaDetail praticaDetail;

    public PutDataRevisioneCommand(DataRevisioneBin modelIn) {
        this.modelIn = modelIn;
        praticaDetail = new PraticaDetail(modelIn.getIdPratica());
    }

    @Override
    protected Void doExecute() {
        DataRevisioneBin dataRevisioneBinWithNumVersion = modelIn.toBuilder()
                .numVersion(praticaDetail.getNumVersionCurrent()).build();

        service.saveRevisionDate(dataRevisioneBinWithNumVersion);

        return null;
    }

    @Override
    public PraticaDetail getPraticaDetail() {
        return this.praticaDetail;
    }

    @Override
    protected boolean canExecute() {
        return praticaDetail.isCheckPassed();
    }
}
