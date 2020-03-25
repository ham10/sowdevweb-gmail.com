package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class MachAutoriseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachAutorise.class);
        MachAutorise machAutorise1 = new MachAutorise();
        machAutorise1.setId(1L);
        MachAutorise machAutorise2 = new MachAutorise();
        machAutorise2.setId(machAutorise1.getId());
        assertThat(machAutorise1).isEqualTo(machAutorise2);
        machAutorise2.setId(2L);
        assertThat(machAutorise1).isNotEqualTo(machAutorise2);
        machAutorise1.setId(null);
        assertThat(machAutorise1).isNotEqualTo(machAutorise2);
    }
}
