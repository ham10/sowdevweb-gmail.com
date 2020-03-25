package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class DetailPlanningTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailPlanning.class);
        DetailPlanning detailPlanning1 = new DetailPlanning();
        detailPlanning1.setId(1L);
        DetailPlanning detailPlanning2 = new DetailPlanning();
        detailPlanning2.setId(detailPlanning1.getId());
        assertThat(detailPlanning1).isEqualTo(detailPlanning2);
        detailPlanning2.setId(2L);
        assertThat(detailPlanning1).isNotEqualTo(detailPlanning2);
        detailPlanning1.setId(null);
        assertThat(detailPlanning1).isNotEqualTo(detailPlanning2);
    }
}
