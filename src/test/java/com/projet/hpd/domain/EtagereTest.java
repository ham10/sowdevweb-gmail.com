package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class EtagereTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etagere.class);
        Etagere etagere1 = new Etagere();
        etagere1.setId(1L);
        Etagere etagere2 = new Etagere();
        etagere2.setId(etagere1.getId());
        assertThat(etagere1).isEqualTo(etagere2);
        etagere2.setId(2L);
        assertThat(etagere1).isNotEqualTo(etagere2);
        etagere1.setId(null);
        assertThat(etagere1).isNotEqualTo(etagere2);
    }
}
