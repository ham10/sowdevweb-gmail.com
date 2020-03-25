package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class HoraireConTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoraireCon.class);
        HoraireCon horaireCon1 = new HoraireCon();
        horaireCon1.setId(1L);
        HoraireCon horaireCon2 = new HoraireCon();
        horaireCon2.setId(horaireCon1.getId());
        assertThat(horaireCon1).isEqualTo(horaireCon2);
        horaireCon2.setId(2L);
        assertThat(horaireCon1).isNotEqualTo(horaireCon2);
        horaireCon1.setId(null);
        assertThat(horaireCon1).isNotEqualTo(horaireCon2);
    }
}
