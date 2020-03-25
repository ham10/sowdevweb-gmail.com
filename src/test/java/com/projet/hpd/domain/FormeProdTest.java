package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class FormeProdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormeProd.class);
        FormeProd formeProd1 = new FormeProd();
        formeProd1.setId(1L);
        FormeProd formeProd2 = new FormeProd();
        formeProd2.setId(formeProd1.getId());
        assertThat(formeProd1).isEqualTo(formeProd2);
        formeProd2.setId(2L);
        assertThat(formeProd1).isNotEqualTo(formeProd2);
        formeProd1.setId(null);
        assertThat(formeProd1).isNotEqualTo(formeProd2);
    }
}
