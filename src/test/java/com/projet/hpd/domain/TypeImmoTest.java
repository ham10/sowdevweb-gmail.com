package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeImmoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeImmo.class);
        TypeImmo typeImmo1 = new TypeImmo();
        typeImmo1.setId(1L);
        TypeImmo typeImmo2 = new TypeImmo();
        typeImmo2.setId(typeImmo1.getId());
        assertThat(typeImmo1).isEqualTo(typeImmo2);
        typeImmo2.setId(2L);
        assertThat(typeImmo1).isNotEqualTo(typeImmo2);
        typeImmo1.setId(null);
        assertThat(typeImmo1).isNotEqualTo(typeImmo2);
    }
}
