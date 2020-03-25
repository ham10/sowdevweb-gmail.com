package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CompteGeneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteGene.class);
        CompteGene compteGene1 = new CompteGene();
        compteGene1.setId(1L);
        CompteGene compteGene2 = new CompteGene();
        compteGene2.setId(compteGene1.getId());
        assertThat(compteGene1).isEqualTo(compteGene2);
        compteGene2.setId(2L);
        assertThat(compteGene1).isNotEqualTo(compteGene2);
        compteGene1.setId(null);
        assertThat(compteGene1).isNotEqualTo(compteGene2);
    }
}
