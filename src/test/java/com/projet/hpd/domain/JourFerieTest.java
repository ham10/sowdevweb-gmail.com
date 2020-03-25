package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class JourFerieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourFerie.class);
        JourFerie jourFerie1 = new JourFerie();
        jourFerie1.setId(1L);
        JourFerie jourFerie2 = new JourFerie();
        jourFerie2.setId(jourFerie1.getId());
        assertThat(jourFerie1).isEqualTo(jourFerie2);
        jourFerie2.setId(2L);
        assertThat(jourFerie1).isNotEqualTo(jourFerie2);
        jourFerie1.setId(null);
        assertThat(jourFerie1).isNotEqualTo(jourFerie2);
    }
}
