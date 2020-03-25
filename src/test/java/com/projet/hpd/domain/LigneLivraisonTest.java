package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class LigneLivraisonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneLivraison.class);
        LigneLivraison ligneLivraison1 = new LigneLivraison();
        ligneLivraison1.setId(1L);
        LigneLivraison ligneLivraison2 = new LigneLivraison();
        ligneLivraison2.setId(ligneLivraison1.getId());
        assertThat(ligneLivraison1).isEqualTo(ligneLivraison2);
        ligneLivraison2.setId(2L);
        assertThat(ligneLivraison1).isNotEqualTo(ligneLivraison2);
        ligneLivraison1.setId(null);
        assertThat(ligneLivraison1).isNotEqualTo(ligneLivraison2);
    }
}
