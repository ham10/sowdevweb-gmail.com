package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeFactureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeFacture.class);
        TypeFacture typeFacture1 = new TypeFacture();
        typeFacture1.setId(1L);
        TypeFacture typeFacture2 = new TypeFacture();
        typeFacture2.setId(typeFacture1.getId());
        assertThat(typeFacture1).isEqualTo(typeFacture2);
        typeFacture2.setId(2L);
        assertThat(typeFacture1).isNotEqualTo(typeFacture2);
        typeFacture1.setId(null);
        assertThat(typeFacture1).isNotEqualTo(typeFacture2);
    }
}
