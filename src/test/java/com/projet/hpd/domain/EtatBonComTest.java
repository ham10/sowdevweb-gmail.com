package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatBonComTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatBonCom.class);
        EtatBonCom etatBonCom1 = new EtatBonCom();
        etatBonCom1.setId(1L);
        EtatBonCom etatBonCom2 = new EtatBonCom();
        etatBonCom2.setId(etatBonCom1.getId());
        assertThat(etatBonCom1).isEqualTo(etatBonCom2);
        etatBonCom2.setId(2L);
        assertThat(etatBonCom1).isNotEqualTo(etatBonCom2);
        etatBonCom1.setId(null);
        assertThat(etatBonCom1).isNotEqualTo(etatBonCom2);
    }
}
