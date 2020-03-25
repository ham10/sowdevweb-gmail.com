package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TarifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarif.class);
        Tarif tarif1 = new Tarif();
        tarif1.setId(1L);
        Tarif tarif2 = new Tarif();
        tarif2.setId(tarif1.getId());
        assertThat(tarif1).isEqualTo(tarif2);
        tarif2.setId(2L);
        assertThat(tarif1).isNotEqualTo(tarif2);
        tarif1.setId(null);
        assertThat(tarif1).isNotEqualTo(tarif2);
    }
}
