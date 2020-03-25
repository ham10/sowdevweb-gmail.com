package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class FonctionnaliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fonctionnalite.class);
        Fonctionnalite fonctionnalite1 = new Fonctionnalite();
        fonctionnalite1.setId(1L);
        Fonctionnalite fonctionnalite2 = new Fonctionnalite();
        fonctionnalite2.setId(fonctionnalite1.getId());
        assertThat(fonctionnalite1).isEqualTo(fonctionnalite2);
        fonctionnalite2.setId(2L);
        assertThat(fonctionnalite1).isNotEqualTo(fonctionnalite2);
        fonctionnalite1.setId(null);
        assertThat(fonctionnalite1).isNotEqualTo(fonctionnalite2);
    }
}
