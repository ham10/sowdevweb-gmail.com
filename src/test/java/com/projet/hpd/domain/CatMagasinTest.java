package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CatMagasinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatMagasin.class);
        CatMagasin catMagasin1 = new CatMagasin();
        catMagasin1.setId(1L);
        CatMagasin catMagasin2 = new CatMagasin();
        catMagasin2.setId(catMagasin1.getId());
        assertThat(catMagasin1).isEqualTo(catMagasin2);
        catMagasin2.setId(2L);
        assertThat(catMagasin1).isNotEqualTo(catMagasin2);
        catMagasin1.setId(null);
        assertThat(catMagasin1).isNotEqualTo(catMagasin2);
    }
}
