package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ChapComptaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChapCompta.class);
        ChapCompta chapCompta1 = new ChapCompta();
        chapCompta1.setId(1L);
        ChapCompta chapCompta2 = new ChapCompta();
        chapCompta2.setId(chapCompta1.getId());
        assertThat(chapCompta1).isEqualTo(chapCompta2);
        chapCompta2.setId(2L);
        assertThat(chapCompta1).isNotEqualTo(chapCompta2);
        chapCompta1.setId(null);
        assertThat(chapCompta1).isNotEqualTo(chapCompta2);
    }
}
