package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePlateauTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePlateau.class);
        TypePlateau typePlateau1 = new TypePlateau();
        typePlateau1.setId(1L);
        TypePlateau typePlateau2 = new TypePlateau();
        typePlateau2.setId(typePlateau1.getId());
        assertThat(typePlateau1).isEqualTo(typePlateau2);
        typePlateau2.setId(2L);
        assertThat(typePlateau1).isNotEqualTo(typePlateau2);
        typePlateau1.setId(null);
        assertThat(typePlateau1).isNotEqualTo(typePlateau2);
    }
}
