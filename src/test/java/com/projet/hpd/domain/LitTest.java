package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class LitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lit.class);
        Lit lit1 = new Lit();
        lit1.setId(1L);
        Lit lit2 = new Lit();
        lit2.setId(lit1.getId());
        assertThat(lit1).isEqualTo(lit2);
        lit2.setId(2L);
        assertThat(lit1).isNotEqualTo(lit2);
        lit1.setId(null);
        assertThat(lit1).isNotEqualTo(lit2);
    }
}
