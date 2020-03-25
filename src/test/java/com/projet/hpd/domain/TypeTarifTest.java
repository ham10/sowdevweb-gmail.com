package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeTarifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeTarif.class);
        TypeTarif typeTarif1 = new TypeTarif();
        typeTarif1.setId(1L);
        TypeTarif typeTarif2 = new TypeTarif();
        typeTarif2.setId(typeTarif1.getId());
        assertThat(typeTarif1).isEqualTo(typeTarif2);
        typeTarif2.setId(2L);
        assertThat(typeTarif1).isNotEqualTo(typeTarif2);
        typeTarif1.setId(null);
        assertThat(typeTarif1).isNotEqualTo(typeTarif2);
    }
}
