package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class RayonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rayon.class);
        Rayon rayon1 = new Rayon();
        rayon1.setId(1L);
        Rayon rayon2 = new Rayon();
        rayon2.setId(rayon1.getId());
        assertThat(rayon1).isEqualTo(rayon2);
        rayon2.setId(2L);
        assertThat(rayon1).isNotEqualTo(rayon2);
        rayon1.setId(null);
        assertThat(rayon1).isNotEqualTo(rayon2);
    }
}
