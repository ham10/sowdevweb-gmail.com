package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeMagasinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMagasin.class);
        TypeMagasin typeMagasin1 = new TypeMagasin();
        typeMagasin1.setId(1L);
        TypeMagasin typeMagasin2 = new TypeMagasin();
        typeMagasin2.setId(typeMagasin1.getId());
        assertThat(typeMagasin1).isEqualTo(typeMagasin2);
        typeMagasin2.setId(2L);
        assertThat(typeMagasin1).isNotEqualTo(typeMagasin2);
        typeMagasin1.setId(null);
        assertThat(typeMagasin1).isNotEqualTo(typeMagasin2);
    }
}
