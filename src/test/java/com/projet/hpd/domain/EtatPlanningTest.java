package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatPlanningTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatPlanning.class);
        EtatPlanning etatPlanning1 = new EtatPlanning();
        etatPlanning1.setId(1L);
        EtatPlanning etatPlanning2 = new EtatPlanning();
        etatPlanning2.setId(etatPlanning1.getId());
        assertThat(etatPlanning1).isEqualTo(etatPlanning2);
        etatPlanning2.setId(2L);
        assertThat(etatPlanning1).isNotEqualTo(etatPlanning2);
        etatPlanning1.setId(null);
        assertThat(etatPlanning1).isNotEqualTo(etatPlanning2);
    }
}
