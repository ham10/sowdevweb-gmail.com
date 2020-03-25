package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class MouvementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mouvement.class);
        Mouvement mouvement1 = new Mouvement();
        mouvement1.setId(1L);
        Mouvement mouvement2 = new Mouvement();
        mouvement2.setId(mouvement1.getId());
        assertThat(mouvement1).isEqualTo(mouvement2);
        mouvement2.setId(2L);
        assertThat(mouvement1).isNotEqualTo(mouvement2);
        mouvement1.setId(null);
        assertThat(mouvement1).isNotEqualTo(mouvement2);
    }
}
