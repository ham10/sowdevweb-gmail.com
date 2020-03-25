package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeSortieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeSortie.class);
        TypeSortie typeSortie1 = new TypeSortie();
        typeSortie1.setId(1L);
        TypeSortie typeSortie2 = new TypeSortie();
        typeSortie2.setId(typeSortie1.getId());
        assertThat(typeSortie1).isEqualTo(typeSortie2);
        typeSortie2.setId(2L);
        assertThat(typeSortie1).isNotEqualTo(typeSortie2);
        typeSortie1.setId(null);
        assertThat(typeSortie1).isNotEqualTo(typeSortie2);
    }
}
