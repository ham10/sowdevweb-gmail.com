package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class PoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pole.class);
        Pole pole1 = new Pole();
        pole1.setId(1L);
        Pole pole2 = new Pole();
        pole2.setId(pole1.getId());
        assertThat(pole1).isEqualTo(pole2);
        pole2.setId(2L);
        assertThat(pole1).isNotEqualTo(pole2);
        pole1.setId(null);
        assertThat(pole1).isNotEqualTo(pole2);
    }
}
