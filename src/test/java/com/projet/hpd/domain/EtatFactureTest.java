package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatFactureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatFacture.class);
        EtatFacture etatFacture1 = new EtatFacture();
        etatFacture1.setId(1L);
        EtatFacture etatFacture2 = new EtatFacture();
        etatFacture2.setId(etatFacture1.getId());
        assertThat(etatFacture1).isEqualTo(etatFacture2);
        etatFacture2.setId(2L);
        assertThat(etatFacture1).isNotEqualTo(etatFacture2);
        etatFacture1.setId(null);
        assertThat(etatFacture1).isNotEqualTo(etatFacture2);
    }
}
