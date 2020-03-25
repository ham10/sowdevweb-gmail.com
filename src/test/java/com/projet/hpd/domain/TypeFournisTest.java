package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeFournisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeFournis.class);
        TypeFournis typeFournis1 = new TypeFournis();
        typeFournis1.setId(1L);
        TypeFournis typeFournis2 = new TypeFournis();
        typeFournis2.setId(typeFournis1.getId());
        assertThat(typeFournis1).isEqualTo(typeFournis2);
        typeFournis2.setId(2L);
        assertThat(typeFournis1).isNotEqualTo(typeFournis2);
        typeFournis1.setId(null);
        assertThat(typeFournis1).isNotEqualTo(typeFournis2);
    }
}
