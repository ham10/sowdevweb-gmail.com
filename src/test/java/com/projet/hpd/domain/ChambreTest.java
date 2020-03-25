package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ChambreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chambre.class);
        Chambre chambre1 = new Chambre();
        chambre1.setId(1L);
        Chambre chambre2 = new Chambre();
        chambre2.setId(chambre1.getId());
        assertThat(chambre1).isEqualTo(chambre2);
        chambre2.setId(2L);
        assertThat(chambre1).isNotEqualTo(chambre2);
        chambre1.setId(null);
        assertThat(chambre1).isNotEqualTo(chambre2);
    }
}
