package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CiviliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Civilite.class);
        Civilite civilite1 = new Civilite();
        civilite1.setId(1L);
        Civilite civilite2 = new Civilite();
        civilite2.setId(civilite1.getId());
        assertThat(civilite1).isEqualTo(civilite2);
        civilite2.setId(2L);
        assertThat(civilite1).isNotEqualTo(civilite2);
        civilite1.setId(null);
        assertThat(civilite1).isNotEqualTo(civilite2);
    }
}
