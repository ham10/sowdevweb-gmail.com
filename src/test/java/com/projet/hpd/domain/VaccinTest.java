package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class VaccinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vaccin.class);
        Vaccin vaccin1 = new Vaccin();
        vaccin1.setId(1L);
        Vaccin vaccin2 = new Vaccin();
        vaccin2.setId(vaccin1.getId());
        assertThat(vaccin1).isEqualTo(vaccin2);
        vaccin2.setId(2L);
        assertThat(vaccin1).isNotEqualTo(vaccin2);
        vaccin1.setId(null);
        assertThat(vaccin1).isNotEqualTo(vaccin2);
    }
}
