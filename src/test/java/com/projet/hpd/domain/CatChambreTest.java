package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CatChambreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatChambre.class);
        CatChambre catChambre1 = new CatChambre();
        catChambre1.setId(1L);
        CatChambre catChambre2 = new CatChambre();
        catChambre2.setId(catChambre1.getId());
        assertThat(catChambre1).isEqualTo(catChambre2);
        catChambre2.setId(2L);
        assertThat(catChambre1).isNotEqualTo(catChambre2);
        catChambre1.setId(null);
        assertThat(catChambre1).isNotEqualTo(catChambre2);
    }
}
