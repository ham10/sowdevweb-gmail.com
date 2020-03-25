package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePlanningTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePlanning.class);
        TypePlanning typePlanning1 = new TypePlanning();
        typePlanning1.setId(1L);
        TypePlanning typePlanning2 = new TypePlanning();
        typePlanning2.setId(typePlanning1.getId());
        assertThat(typePlanning1).isEqualTo(typePlanning2);
        typePlanning2.setId(2L);
        assertThat(typePlanning1).isNotEqualTo(typePlanning2);
        typePlanning1.setId(null);
        assertThat(typePlanning1).isNotEqualTo(typePlanning2);
    }
}
