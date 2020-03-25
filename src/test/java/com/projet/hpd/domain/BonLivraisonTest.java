package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class BonLivraisonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonLivraison.class);
        BonLivraison bonLivraison1 = new BonLivraison();
        bonLivraison1.setId(1L);
        BonLivraison bonLivraison2 = new BonLivraison();
        bonLivraison2.setId(bonLivraison1.getId());
        assertThat(bonLivraison1).isEqualTo(bonLivraison2);
        bonLivraison2.setId(2L);
        assertThat(bonLivraison1).isNotEqualTo(bonLivraison2);
        bonLivraison1.setId(null);
        assertThat(bonLivraison1).isNotEqualTo(bonLivraison2);
    }
}
