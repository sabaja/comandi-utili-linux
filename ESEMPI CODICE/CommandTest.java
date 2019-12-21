package com.intesasanpaolo.bear.lmbe0.fidogaranzia.command;

import com.intesasanpaolo.bear.lmbe0.fidogaranzia.dto.CreatePartecipanteDto;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.service.PoolService;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.dto.DatiChiamataDto;
import com.intesasanpaolo.bear.lmbe0.lib.core.standard.retriever.PraticaDetail;
import com.intesasanpaolo.bear.lmbe0.model.PoolPartEntity;
import com.intesasanpaolo.bear.lmbe0.model.bin.PostPartecipantPoolBin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PraticaAffidamentoManualeRichiestaChiusuraPraticaCommandTest {

    @InjectMocks
    private PraticaAffidamentoManualeRichiestaChiusuraPraticaCommand command;

    @Mock
    private PoolService poolService;

    @Mock
    private PraticaDetail praticaDetail;

    @Mock
    private PostPartecipantPoolBin postPartecipantPoolBin;

    private BigInteger idPratica = BigInteger.valueOf(12345);
    private BigInteger numPool = BigInteger.valueOf(1234);

    @Test
    public void doExecuteTest() throws Exception {
        when(praticaDetail.isCheckPassed()).thenReturn(true);
        when(poolService.savePartecipantPool(any(PostPartecipantPoolBin.class), any(PraticaDetail.class))).thenReturn(mockedResponse());
        PoolPartEntity output = command.execute();

        assertThat(output)
                .isNotNull()
                .isEqualTo(mockedResponse());
    }

    private PoolPartEntity mockedResponse() {
        PoolPartEntity partecipant = new PoolPartEntity();
        partecipant.setIdPratica(idPratica);
        partecipant.setNumProgPool(numPool);
        partecipant.setCodNsgPart("codNsg");
        partecipant.setDesDenominazione("desDenom");
        partecipant.setFlgCapofila(false);
        partecipant.setPerImpTot(BigDecimal.ONE);

        return partecipant;
    }


}