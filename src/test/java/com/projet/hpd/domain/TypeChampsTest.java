package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeChampsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeChamps.class);
        TypeChamps typeChamps1 = new TypeChamps();
        typeChamps1.setId(1L);
        TypeChamps typeChamps2 = new TypeChamps();
        typeChamps2.setId(typeChamps1.getId());
        assertThat(typeChamps1).isEqualTo(typeChamps2);
        typeChamps2.setId(2L);
        assertThat(typeChamps1).isNotEqualTo(typeChamps2);
        typeChamps1.setId(null);
        assertThat(typeChamps1).isNotEqualTo(typeChamps2);
    }
}
