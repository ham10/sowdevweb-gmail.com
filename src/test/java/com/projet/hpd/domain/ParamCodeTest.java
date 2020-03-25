package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ParamCodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamCode.class);
        ParamCode paramCode1 = new ParamCode();
        paramCode1.setId(1L);
        ParamCode paramCode2 = new ParamCode();
        paramCode2.setId(paramCode1.getId());
        assertThat(paramCode1).isEqualTo(paramCode2);
        paramCode2.setId(2L);
        assertThat(paramCode1).isNotEqualTo(paramCode2);
        paramCode1.setId(null);
        assertThat(paramCode1).isNotEqualTo(paramCode2);
    }
}
