package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatCaisseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatCaisse.class);
        EtatCaisse etatCaisse1 = new EtatCaisse();
        etatCaisse1.setId(1L);
        EtatCaisse etatCaisse2 = new EtatCaisse();
        etatCaisse2.setId(etatCaisse1.getId());
        assertThat(etatCaisse1).isEqualTo(etatCaisse2);
        etatCaisse2.setId(2L);
        assertThat(etatCaisse1).isNotEqualTo(etatCaisse2);
        etatCaisse1.setId(null);
        assertThat(etatCaisse1).isNotEqualTo(etatCaisse2);
    }
}
