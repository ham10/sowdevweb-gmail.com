package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ProdFournisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdFournis.class);
        ProdFournis prodFournis1 = new ProdFournis();
        prodFournis1.setId(1L);
        ProdFournis prodFournis2 = new ProdFournis();
        prodFournis2.setId(prodFournis1.getId());
        assertThat(prodFournis1).isEqualTo(prodFournis2);
        prodFournis2.setId(2L);
        assertThat(prodFournis1).isNotEqualTo(prodFournis2);
        prodFournis1.setId(null);
        assertThat(prodFournis1).isNotEqualTo(prodFournis2);
    }
}
