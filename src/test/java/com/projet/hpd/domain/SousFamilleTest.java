package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class SousFamilleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousFamille.class);
        SousFamille sousFamille1 = new SousFamille();
        sousFamille1.setId(1L);
        SousFamille sousFamille2 = new SousFamille();
        sousFamille2.setId(sousFamille1.getId());
        assertThat(sousFamille1).isEqualTo(sousFamille2);
        sousFamille2.setId(2L);
        assertThat(sousFamille1).isNotEqualTo(sousFamille2);
        sousFamille1.setId(null);
        assertThat(sousFamille1).isNotEqualTo(sousFamille2);
    }
}
