package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class BoxeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boxe.class);
        Boxe boxe1 = new Boxe();
        boxe1.setId(1L);
        Boxe boxe2 = new Boxe();
        boxe2.setId(boxe1.getId());
        assertThat(boxe1).isEqualTo(boxe2);
        boxe2.setId(2L);
        assertThat(boxe1).isNotEqualTo(boxe2);
        boxe1.setId(null);
        assertThat(boxe1).isNotEqualTo(boxe2);
    }
}
