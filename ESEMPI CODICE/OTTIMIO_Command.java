package com.intesasanpaolo.bear.lmbe0.fidogaranzia.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.service.PoolService;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetail;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetailHolder;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.marker.PraticaDetailNeeded;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.marker.PraticaVersionNeeded;
import com.intesasanpaolo.bear.lmbe0.model.PoolPartEntity;
import com.intesasanpaolo.bear.lmbe0.model.bin.PostPartecipantPoolBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PostPartecipantPoolCommand extends BaseCommand<PoolPartEntity> implements
        PraticaDetailHolder, PraticaDetailNeeded, PraticaVersionNeeded {

    private final PraticaDetail praticaDetail;

    @Autowired
    private PoolService service;

    private PostPartecipantPoolBin modelIn;

    public PostPartecipantPoolCommand(PostPartecipantPoolBin modelIn) {
        this.modelIn = modelIn;
        this.praticaDetail = new PraticaDetail(modelIn.getPoolPart().getIdPratica());
    }

    PostPartecipantPoolCommand(PraticaDetail praticaDetail, PoolService service, PostPartecipantPoolBin modelIn) {
        this.praticaDetail = praticaDetail;
        this.service = service;
        this.modelIn = modelIn;
    }

    @Override
    public PoolPartEntity doExecute() {
        return service.savePartecipantPool(modelIn, praticaDetail);
    }

    @Override
    protected boolean canExecute() {
        return praticaDetail.isCheckPassed();
    }

    @Override
    public PraticaDetail getPraticaDetail() {
        return praticaDetail;
    }
}
