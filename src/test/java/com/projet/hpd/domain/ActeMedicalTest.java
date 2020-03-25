package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ActeMedicalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeMedical.class);
        ActeMedical acteMedical1 = new ActeMedical();
        acteMedical1.setId(1L);
        ActeMedical acteMedical2 = new ActeMedical();
        acteMedical2.setId(acteMedical1.getId());
        assertThat(acteMedical1).isEqualTo(acteMedical2);
        acteMedical2.setId(2L);
        assertThat(acteMedical1).isNotEqualTo(acteMedical2);
        acteMedical1.setId(null);
        assertThat(acteMedical1).isNotEqualTo(acteMedical2);
    }
}
