package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class NatureOpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureOp.class);
        NatureOp natureOp1 = new NatureOp();
        natureOp1.setId(1L);
        NatureOp natureOp2 = new NatureOp();
        natureOp2.setId(natureOp1.getId());
        assertThat(natureOp1).isEqualTo(natureOp2);
        natureOp2.setId(2L);
        assertThat(natureOp1).isNotEqualTo(natureOp2);
        natureOp1.setId(null);
        assertThat(natureOp1).isNotEqualTo(natureOp2);
    }
}
