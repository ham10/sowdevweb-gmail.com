package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TabAmortisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabAmortis.class);
        TabAmortis tabAmortis1 = new TabAmortis();
        tabAmortis1.setId(1L);
        TabAmortis tabAmortis2 = new TabAmortis();
        tabAmortis2.setId(tabAmortis1.getId());
        assertThat(tabAmortis1).isEqualTo(tabAmortis2);
        tabAmortis2.setId(2L);
        assertThat(tabAmortis1).isNotEqualTo(tabAmortis2);
        tabAmortis1.setId(null);
        assertThat(tabAmortis1).isNotEqualTo(tabAmortis2);
    }
}
