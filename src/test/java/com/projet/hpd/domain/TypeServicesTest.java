package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeServices.class);
        TypeServices typeServices1 = new TypeServices();
        typeServices1.setId(1L);
        TypeServices typeServices2 = new TypeServices();
        typeServices2.setId(typeServices1.getId());
        assertThat(typeServices1).isEqualTo(typeServices2);
        typeServices2.setId(2L);
        assertThat(typeServices1).isNotEqualTo(typeServices2);
        typeServices1.setId(null);
        assertThat(typeServices1).isNotEqualTo(typeServices2);
    }
}
