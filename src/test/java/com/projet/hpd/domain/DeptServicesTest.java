package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class DeptServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeptServices.class);
        DeptServices deptServices1 = new DeptServices();
        deptServices1.setId(1L);
        DeptServices deptServices2 = new DeptServices();
        deptServices2.setId(deptServices1.getId());
        assertThat(deptServices1).isEqualTo(deptServices2);
        deptServices2.setId(2L);
        assertThat(deptServices1).isNotEqualTo(deptServices2);
        deptServices1.setId(null);
        assertThat(deptServices1).isNotEqualTo(deptServices2);
    }
}
