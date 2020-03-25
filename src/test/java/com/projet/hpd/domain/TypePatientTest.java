package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePatientTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePatient.class);
        TypePatient typePatient1 = new TypePatient();
        typePatient1.setId(1L);
        TypePatient typePatient2 = new TypePatient();
        typePatient2.setId(typePatient1.getId());
        assertThat(typePatient1).isEqualTo(typePatient2);
        typePatient2.setId(2L);
        assertThat(typePatient1).isNotEqualTo(typePatient2);
        typePatient1.setId(null);
        assertThat(typePatient1).isNotEqualTo(typePatient2);
    }
}
