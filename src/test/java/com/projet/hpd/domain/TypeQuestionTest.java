package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeQuestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeQuestion.class);
        TypeQuestion typeQuestion1 = new TypeQuestion();
        typeQuestion1.setId(1L);
        TypeQuestion typeQuestion2 = new TypeQuestion();
        typeQuestion2.setId(typeQuestion1.getId());
        assertThat(typeQuestion1).isEqualTo(typeQuestion2);
        typeQuestion2.setId(2L);
        assertThat(typeQuestion1).isNotEqualTo(typeQuestion2);
        typeQuestion1.setId(null);
        assertThat(typeQuestion1).isNotEqualTo(typeQuestion2);
    }
}
