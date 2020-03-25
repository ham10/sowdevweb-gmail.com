package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class DosMedicalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosMedical.class);
        DosMedical dosMedical1 = new DosMedical();
        dosMedical1.setId(1L);
        DosMedical dosMedical2 = new DosMedical();
        dosMedical2.setId(dosMedical1.getId());
        assertThat(dosMedical1).isEqualTo(dosMedical2);
        dosMedical2.setId(2L);
        assertThat(dosMedical1).isNotEqualTo(dosMedical2);
        dosMedical1.setId(null);
        assertThat(dosMedical1).isNotEqualTo(dosMedical2);
    }
}
