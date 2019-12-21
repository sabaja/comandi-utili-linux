package com.intesasanpaolo.bear.lmbe0.fidogaranzia.mapper;

import com.intesasanpaolo.bear.lmbe0.fidogaranzia.BaseIntegrationTest;
import com.intesasanpaolo.bear.lmbe0.fidogaranzia.dto.AllocazioneDTO;
import com.intesasanpaolo.bear.lmbe0.model.Allocazione;
import com.intesasanpaolo.bear.lmbe0.model.bin.AllocazioneBin;
import com.intesasanpaolo.bear.lmbe0.model.domain.Periodo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocazioneMapperTest extends BaseIntegrationTest {

    @Autowired
    private AllocazioneMapper mapper;

    @Test
    public void shouldMapToEntity() {
        //given
        AllocazioneBin bin = AllocazioneBin.builder()
                .idPratica(BigInteger.ONE)
                .numFido(BigInteger.ONE)
                .build();
        AllocazioneDTO dto = new AllocazioneDTO();
        dto.setCodFil("cFil1");
        dto.setImpFido(BigDecimal.TEN);
        dto.setDurata("100");
        dto.setCodPeriodicita(Periodo.GIORNI);

        //when
        Allocazione allocazione = mapper.mapToEntity(bin, dto);

        assertThat(allocazione.getIdPratica()).isEqualTo(bin.getIdPratica());
        assertThat(allocazione.getNumProgFido()).isEqualTo(bin.getNumFido());
        assertThat(allocazione.getCodFil()).isEqualTo(dto.getCodFil());
        assertThat(allocazione.getImpFido()).isEqualTo(dto.getImpFido());
        assertThat(allocazione.getDurata()).isEqualTo(dto.getDurata());
        assertThat(allocazione.getCodPeriodicita()).isEqualTo(dto.getCodPeriodicita());

    }
}
