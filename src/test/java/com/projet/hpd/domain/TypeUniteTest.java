package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeUniteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeUnite.class);
        TypeUnite typeUnite1 = new TypeUnite();
        typeUnite1.setId(1L);
        TypeUnite typeUnite2 = new TypeUnite();
        typeUnite2.setId(typeUnite1.getId());
        assertThat(typeUnite1).isEqualTo(typeUnite2);
        typeUnite2.setId(2L);
        assertThat(typeUnite1).isNotEqualTo(typeUnite2);
        typeUnite1.setId(null);
        assertThat(typeUnite1).isNotEqualTo(typeUnite2);
    }
}
