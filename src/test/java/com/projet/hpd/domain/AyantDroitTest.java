package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class AyantDroitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AyantDroit.class);
        AyantDroit ayantDroit1 = new AyantDroit();
        ayantDroit1.setId(1L);
        AyantDroit ayantDroit2 = new AyantDroit();
        ayantDroit2.setId(ayantDroit1.getId());
        assertThat(ayantDroit1).isEqualTo(ayantDroit2);
        ayantDroit2.setId(2L);
        assertThat(ayantDroit1).isNotEqualTo(ayantDroit2);
        ayantDroit1.setId(null);
        assertThat(ayantDroit1).isNotEqualTo(ayantDroit2);
    }
}
