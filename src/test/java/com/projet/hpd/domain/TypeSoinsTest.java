package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeSoinsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeSoins.class);
        TypeSoins typeSoins1 = new TypeSoins();
        typeSoins1.setId(1L);
        TypeSoins typeSoins2 = new TypeSoins();
        typeSoins2.setId(typeSoins1.getId());
        assertThat(typeSoins1).isEqualTo(typeSoins2);
        typeSoins2.setId(2L);
        assertThat(typeSoins1).isNotEqualTo(typeSoins2);
        typeSoins1.setId(null);
        assertThat(typeSoins1).isNotEqualTo(typeSoins2);
    }
}
