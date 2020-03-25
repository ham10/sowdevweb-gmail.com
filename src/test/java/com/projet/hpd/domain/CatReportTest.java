package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class CatReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatReport.class);
        CatReport catReport1 = new CatReport();
        catReport1.setId(1L);
        CatReport catReport2 = new CatReport();
        catReport2.setId(catReport1.getId());
        assertThat(catReport1).isEqualTo(catReport2);
        catReport2.setId(2L);
        assertThat(catReport1).isNotEqualTo(catReport2);
        catReport1.setId(null);
        assertThat(catReport1).isNotEqualTo(catReport2);
    }
}
