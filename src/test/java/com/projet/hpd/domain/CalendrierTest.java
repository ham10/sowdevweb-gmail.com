package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CalendrierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendrier.class);
        Calendrier calendrier1 = new Calendrier();
        calendrier1.setId(1L);
        Calendrier calendrier2 = new Calendrier();
        calendrier2.setId(calendrier1.getId());
        assertThat(calendrier1).isEqualTo(calendrier2);
        calendrier2.setId(2L);
        assertThat(calendrier1).isNotEqualTo(calendrier2);
        calendrier1.setId(null);
        assertThat(calendrier1).isNotEqualTo(calendrier2);
    }
}
