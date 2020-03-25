package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeConstanteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeConstante.class);
        TypeConstante typeConstante1 = new TypeConstante();
        typeConstante1.setId(1L);
        TypeConstante typeConstante2 = new TypeConstante();
        typeConstante2.setId(typeConstante1.getId());
        assertThat(typeConstante1).isEqualTo(typeConstante2);
        typeConstante2.setId(2L);
        assertThat(typeConstante1).isNotEqualTo(typeConstante2);
        typeConstante1.setId(null);
        assertThat(typeConstante1).isNotEqualTo(typeConstante2);
    }
}
