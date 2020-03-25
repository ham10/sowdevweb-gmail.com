package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeCaisseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCaisse.class);
        TypeCaisse typeCaisse1 = new TypeCaisse();
        typeCaisse1.setId(1L);
        TypeCaisse typeCaisse2 = new TypeCaisse();
        typeCaisse2.setId(typeCaisse1.getId());
        assertThat(typeCaisse1).isEqualTo(typeCaisse2);
        typeCaisse2.setId(2L);
        assertThat(typeCaisse1).isNotEqualTo(typeCaisse2);
        typeCaisse1.setId(null);
        assertThat(typeCaisse1).isNotEqualTo(typeCaisse2);
    }
}
