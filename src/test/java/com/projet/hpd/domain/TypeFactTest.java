package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeFactTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeFact.class);
        TypeFact typeFact1 = new TypeFact();
        typeFact1.setId(1L);
        TypeFact typeFact2 = new TypeFact();
        typeFact2.setId(typeFact1.getId());
        assertThat(typeFact1).isEqualTo(typeFact2);
        typeFact2.setId(2L);
        assertThat(typeFact1).isNotEqualTo(typeFact2);
        typeFact1.setId(null);
        assertThat(typeFact1).isNotEqualTo(typeFact2);
    }
}
