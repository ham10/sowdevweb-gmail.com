package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeReport.class);
        TypeReport typeReport1 = new TypeReport();
        typeReport1.setId(1L);
        TypeReport typeReport2 = new TypeReport();
        typeReport2.setId(typeReport1.getId());
        assertThat(typeReport1).isEqualTo(typeReport2);
        typeReport2.setId(2L);
        assertThat(typeReport1).isNotEqualTo(typeReport2);
        typeReport1.setId(null);
        assertThat(typeReport1).isNotEqualTo(typeReport2);
    }
}
