package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class RDVTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RDV.class);
        RDV rDV1 = new RDV();
        rDV1.setId(1L);
        RDV rDV2 = new RDV();
        rDV2.setId(rDV1.getId());
        assertThat(rDV1).isEqualTo(rDV2);
        rDV2.setId(2L);
        assertThat(rDV1).isNotEqualTo(rDV2);
        rDV1.setId(null);
        assertThat(rDV1).isNotEqualTo(rDV2);
    }
}
