package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeProdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeProd.class);
        TypeProd typeProd1 = new TypeProd();
        typeProd1.setId(1L);
        TypeProd typeProd2 = new TypeProd();
        typeProd2.setId(typeProd1.getId());
        assertThat(typeProd1).isEqualTo(typeProd2);
        typeProd2.setId(2L);
        assertThat(typeProd1).isNotEqualTo(typeProd2);
        typeProd1.setId(null);
        assertThat(typeProd1).isNotEqualTo(typeProd2);
    }
}
