package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ModeRegleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeRegle.class);
        ModeRegle modeRegle1 = new ModeRegle();
        modeRegle1.setId(1L);
        ModeRegle modeRegle2 = new ModeRegle();
        modeRegle2.setId(modeRegle1.getId());
        assertThat(modeRegle1).isEqualTo(modeRegle2);
        modeRegle2.setId(2L);
        assertThat(modeRegle1).isNotEqualTo(modeRegle2);
        modeRegle1.setId(null);
        assertThat(modeRegle1).isNotEqualTo(modeRegle2);
    }
}
