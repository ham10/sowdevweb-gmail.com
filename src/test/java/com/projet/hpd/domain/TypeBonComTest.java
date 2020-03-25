package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeBonComTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeBonCom.class);
        TypeBonCom typeBonCom1 = new TypeBonCom();
        typeBonCom1.setId(1L);
        TypeBonCom typeBonCom2 = new TypeBonCom();
        typeBonCom2.setId(typeBonCom1.getId());
        assertThat(typeBonCom1).isEqualTo(typeBonCom2);
        typeBonCom2.setId(2L);
        assertThat(typeBonCom1).isNotEqualTo(typeBonCom2);
        typeBonCom1.setId(null);
        assertThat(typeBonCom1).isNotEqualTo(typeBonCom2);
    }
}
