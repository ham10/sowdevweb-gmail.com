package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ParamSysTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamSys.class);
        ParamSys paramSys1 = new ParamSys();
        paramSys1.setId(1L);
        ParamSys paramSys2 = new ParamSys();
        paramSys2.setId(paramSys1.getId());
        assertThat(paramSys1).isEqualTo(paramSys2);
        paramSys2.setId(2L);
        assertThat(paramSys1).isNotEqualTo(paramSys2);
        paramSys1.setId(null);
        assertThat(paramSys1).isNotEqualTo(paramSys2);
    }
}
