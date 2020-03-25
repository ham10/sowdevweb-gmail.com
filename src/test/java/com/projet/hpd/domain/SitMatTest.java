package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class SitMatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SitMat.class);
        SitMat sitMat1 = new SitMat();
        sitMat1.setId(1L);
        SitMat sitMat2 = new SitMat();
        sitMat2.setId(sitMat1.getId());
        assertThat(sitMat1).isEqualTo(sitMat2);
        sitMat2.setId(2L);
        assertThat(sitMat1).isNotEqualTo(sitMat2);
        sitMat1.setId(null);
        assertThat(sitMat1).isNotEqualTo(sitMat2);
    }
}
