package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EcritureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecriture.class);
        Ecriture ecriture1 = new Ecriture();
        ecriture1.setId(1L);
        Ecriture ecriture2 = new Ecriture();
        ecriture2.setId(ecriture1.getId());
        assertThat(ecriture1).isEqualTo(ecriture2);
        ecriture2.setId(2L);
        assertThat(ecriture1).isNotEqualTo(ecriture2);
        ecriture1.setId(null);
        assertThat(ecriture1).isNotEqualTo(ecriture2);
    }
}
