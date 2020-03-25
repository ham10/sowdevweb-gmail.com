package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeMvtStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMvtStock.class);
        TypeMvtStock typeMvtStock1 = new TypeMvtStock();
        typeMvtStock1.setId(1L);
        TypeMvtStock typeMvtStock2 = new TypeMvtStock();
        typeMvtStock2.setId(typeMvtStock1.getId());
        assertThat(typeMvtStock1).isEqualTo(typeMvtStock2);
        typeMvtStock2.setId(2L);
        assertThat(typeMvtStock1).isNotEqualTo(typeMvtStock2);
        typeMvtStock1.setId(null);
        assertThat(typeMvtStock1).isNotEqualTo(typeMvtStock2);
    }
}
