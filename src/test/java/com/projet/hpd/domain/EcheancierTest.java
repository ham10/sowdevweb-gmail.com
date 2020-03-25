package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EcheancierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Echeancier.class);
        Echeancier echeancier1 = new Echeancier();
        echeancier1.setId(1L);
        Echeancier echeancier2 = new Echeancier();
        echeancier2.setId(echeancier1.getId());
        assertThat(echeancier1).isEqualTo(echeancier2);
        echeancier2.setId(2L);
        assertThat(echeancier1).isNotEqualTo(echeancier2);
        echeancier1.setId(null);
        assertThat(echeancier1).isNotEqualTo(echeancier2);
    }
}
