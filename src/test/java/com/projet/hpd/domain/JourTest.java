package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class JourTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jour.class);
        Jour jour1 = new Jour();
        jour1.setId(1L);
        Jour jour2 = new Jour();
        jour2.setId(jour1.getId());
        assertThat(jour1).isEqualTo(jour2);
        jour2.setId(2L);
        assertThat(jour1).isNotEqualTo(jour2);
        jour1.setId(null);
        assertThat(jour1).isNotEqualTo(jour2);
    }
}
