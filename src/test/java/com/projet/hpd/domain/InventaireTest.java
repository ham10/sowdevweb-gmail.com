package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class InventaireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventaire.class);
        Inventaire inventaire1 = new Inventaire();
        inventaire1.setId(1L);
        Inventaire inventaire2 = new Inventaire();
        inventaire2.setId(inventaire1.getId());
        assertThat(inventaire1).isEqualTo(inventaire2);
        inventaire2.setId(2L);
        assertThat(inventaire1).isNotEqualTo(inventaire2);
        inventaire1.setId(null);
        assertThat(inventaire1).isNotEqualTo(inventaire2);
    }
}
