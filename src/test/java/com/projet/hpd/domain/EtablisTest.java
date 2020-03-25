package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtablisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etablis.class);
        Etablis etablis1 = new Etablis();
        etablis1.setId(1L);
        Etablis etablis2 = new Etablis();
        etablis2.setId(etablis1.getId());
        assertThat(etablis1).isEqualTo(etablis2);
        etablis2.setId(2L);
        assertThat(etablis1).isNotEqualTo(etablis2);
        etablis1.setId(null);
        assertThat(etablis1).isNotEqualTo(etablis2);
    }
}
