package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePrChargeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePrCharge.class);
        TypePrCharge typePrCharge1 = new TypePrCharge();
        typePrCharge1.setId(1L);
        TypePrCharge typePrCharge2 = new TypePrCharge();
        typePrCharge2.setId(typePrCharge1.getId());
        assertThat(typePrCharge1).isEqualTo(typePrCharge2);
        typePrCharge2.setId(2L);
        assertThat(typePrCharge1).isNotEqualTo(typePrCharge2);
        typePrCharge1.setId(null);
        assertThat(typePrCharge1).isNotEqualTo(typePrCharge2);
    }
}
