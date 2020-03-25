package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatImmoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatImmo.class);
        EtatImmo etatImmo1 = new EtatImmo();
        etatImmo1.setId(1L);
        EtatImmo etatImmo2 = new EtatImmo();
        etatImmo2.setId(etatImmo1.getId());
        assertThat(etatImmo1).isEqualTo(etatImmo2);
        etatImmo2.setId(2L);
        assertThat(etatImmo1).isNotEqualTo(etatImmo2);
        etatImmo1.setId(null);
        assertThat(etatImmo1).isNotEqualTo(etatImmo2);
    }
}
