package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeCondTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCond.class);
        TypeCond typeCond1 = new TypeCond();
        typeCond1.setId(1L);
        TypeCond typeCond2 = new TypeCond();
        typeCond2.setId(typeCond1.getId());
        assertThat(typeCond1).isEqualTo(typeCond2);
        typeCond2.setId(2L);
        assertThat(typeCond1).isNotEqualTo(typeCond2);
        typeCond1.setId(null);
        assertThat(typeCond1).isNotEqualTo(typeCond2);
    }
}
