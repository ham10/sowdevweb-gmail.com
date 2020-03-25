package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeLitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeLit.class);
        TypeLit typeLit1 = new TypeLit();
        typeLit1.setId(1L);
        TypeLit typeLit2 = new TypeLit();
        typeLit2.setId(typeLit1.getId());
        assertThat(typeLit1).isEqualTo(typeLit2);
        typeLit2.setId(2L);
        assertThat(typeLit1).isNotEqualTo(typeLit2);
        typeLit1.setId(null);
        assertThat(typeLit1).isNotEqualTo(typeLit2);
    }
}
