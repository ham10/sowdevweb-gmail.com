package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ImmoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Immo.class);
        Immo immo1 = new Immo();
        immo1.setId(1L);
        Immo immo2 = new Immo();
        immo2.setId(immo1.getId());
        assertThat(immo1).isEqualTo(immo2);
        immo2.setId(2L);
        assertThat(immo1).isNotEqualTo(immo2);
        immo1.setId(null);
        assertThat(immo1).isNotEqualTo(immo2);
    }
}
