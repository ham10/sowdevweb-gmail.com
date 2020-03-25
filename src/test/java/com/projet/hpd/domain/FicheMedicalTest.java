package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class FicheMedicalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FicheMedical.class);
        FicheMedical ficheMedical1 = new FicheMedical();
        ficheMedical1.setId(1L);
        FicheMedical ficheMedical2 = new FicheMedical();
        ficheMedical2.setId(ficheMedical1.getId());
        assertThat(ficheMedical1).isEqualTo(ficheMedical2);
        ficheMedical2.setId(2L);
        assertThat(ficheMedical1).isNotEqualTo(ficheMedical2);
        ficheMedical1.setId(null);
        assertThat(ficheMedical1).isNotEqualTo(ficheMedical2);
    }
}
