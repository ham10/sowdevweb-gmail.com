package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ParamDiversTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamDivers.class);
        ParamDivers paramDivers1 = new ParamDivers();
        paramDivers1.setId(1L);
        ParamDivers paramDivers2 = new ParamDivers();
        paramDivers2.setId(paramDivers1.getId());
        assertThat(paramDivers1).isEqualTo(paramDivers2);
        paramDivers2.setId(2L);
        assertThat(paramDivers1).isNotEqualTo(paramDivers2);
        paramDivers1.setId(null);
        assertThat(paramDivers1).isNotEqualTo(paramDivers2);
    }
}
