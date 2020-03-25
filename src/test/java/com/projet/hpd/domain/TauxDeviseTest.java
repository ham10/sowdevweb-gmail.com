package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TauxDeviseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TauxDevise.class);
        TauxDevise tauxDevise1 = new TauxDevise();
        tauxDevise1.setId(1L);
        TauxDevise tauxDevise2 = new TauxDevise();
        tauxDevise2.setId(tauxDevise1.getId());
        assertThat(tauxDevise1).isEqualTo(tauxDevise2);
        tauxDevise2.setId(2L);
        assertThat(tauxDevise1).isNotEqualTo(tauxDevise2);
        tauxDevise1.setId(null);
        assertThat(tauxDevise1).isNotEqualTo(tauxDevise2);
    }
}
