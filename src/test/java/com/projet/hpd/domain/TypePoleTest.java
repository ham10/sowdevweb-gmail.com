package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePole.class);
        TypePole typePole1 = new TypePole();
        typePole1.setId(1L);
        TypePole typePole2 = new TypePole();
        typePole2.setId(typePole1.getId());
        assertThat(typePole1).isEqualTo(typePole2);
        typePole2.setId(2L);
        assertThat(typePole1).isNotEqualTo(typePole2);
        typePole1.setId(null);
        assertThat(typePole1).isNotEqualTo(typePole2);
    }
}
