package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CodeBudgetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeBudget.class);
        CodeBudget codeBudget1 = new CodeBudget();
        codeBudget1.setId(1L);
        CodeBudget codeBudget2 = new CodeBudget();
        codeBudget2.setId(codeBudget1.getId());
        assertThat(codeBudget1).isEqualTo(codeBudget2);
        codeBudget2.setId(2L);
        assertThat(codeBudget1).isNotEqualTo(codeBudget2);
        codeBudget1.setId(null);
        assertThat(codeBudget1).isNotEqualTo(codeBudget2);
    }
}
