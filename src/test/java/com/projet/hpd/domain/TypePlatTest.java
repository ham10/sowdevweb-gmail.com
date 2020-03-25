package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePlatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePlat.class);
        TypePlat typePlat1 = new TypePlat();
        typePlat1.setId(1L);
        TypePlat typePlat2 = new TypePlat();
        typePlat2.setId(typePlat1.getId());
        assertThat(typePlat1).isEqualTo(typePlat2);
        typePlat2.setId(2L);
        assertThat(typePlat1).isNotEqualTo(typePlat2);
        typePlat1.setId(null);
        assertThat(typePlat1).isNotEqualTo(typePlat2);
    }
}
