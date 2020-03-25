package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatRdvTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatRdv.class);
        EtatRdv etatRdv1 = new EtatRdv();
        etatRdv1.setId(1L);
        EtatRdv etatRdv2 = new EtatRdv();
        etatRdv2.setId(etatRdv1.getId());
        assertThat(etatRdv1).isEqualTo(etatRdv2);
        etatRdv2.setId(2L);
        assertThat(etatRdv1).isNotEqualTo(etatRdv2);
        etatRdv1.setId(null);
        assertThat(etatRdv1).isNotEqualTo(etatRdv2);
    }
}
