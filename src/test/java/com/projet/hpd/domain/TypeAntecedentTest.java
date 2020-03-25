package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeAntecedentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAntecedent.class);
        TypeAntecedent typeAntecedent1 = new TypeAntecedent();
        typeAntecedent1.setId(1L);
        TypeAntecedent typeAntecedent2 = new TypeAntecedent();
        typeAntecedent2.setId(typeAntecedent1.getId());
        assertThat(typeAntecedent1).isEqualTo(typeAntecedent2);
        typeAntecedent2.setId(2L);
        assertThat(typeAntecedent1).isNotEqualTo(typeAntecedent2);
        typeAntecedent1.setId(null);
        assertThat(typeAntecedent1).isNotEqualTo(typeAntecedent2);
    }
}
