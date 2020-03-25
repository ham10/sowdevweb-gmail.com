package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtatOperationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatOperation.class);
        EtatOperation etatOperation1 = new EtatOperation();
        etatOperation1.setId(1L);
        EtatOperation etatOperation2 = new EtatOperation();
        etatOperation2.setId(etatOperation1.getId());
        assertThat(etatOperation1).isEqualTo(etatOperation2);
        etatOperation2.setId(2L);
        assertThat(etatOperation1).isNotEqualTo(etatOperation2);
        etatOperation1.setId(null);
        assertThat(etatOperation1).isNotEqualTo(etatOperation2);
    }
}
