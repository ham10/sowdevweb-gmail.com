package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class BonDeCommandeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonDeCommande.class);
        BonDeCommande bonDeCommande1 = new BonDeCommande();
        bonDeCommande1.setId(1L);
        BonDeCommande bonDeCommande2 = new BonDeCommande();
        bonDeCommande2.setId(bonDeCommande1.getId());
        assertThat(bonDeCommande1).isEqualTo(bonDeCommande2);
        bonDeCommande2.setId(2L);
        assertThat(bonDeCommande1).isNotEqualTo(bonDeCommande2);
        bonDeCommande1.setId(null);
        assertThat(bonDeCommande1).isNotEqualTo(bonDeCommande2);
    }
}
