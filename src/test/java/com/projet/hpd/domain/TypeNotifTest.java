package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypeNotifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeNotif.class);
        TypeNotif typeNotif1 = new TypeNotif();
        typeNotif1.setId(1L);
        TypeNotif typeNotif2 = new TypeNotif();
        typeNotif2.setId(typeNotif1.getId());
        assertThat(typeNotif1).isEqualTo(typeNotif2);
        typeNotif2.setId(2L);
        assertThat(typeNotif1).isNotEqualTo(typeNotif2);
        typeNotif1.setId(null);
        assertThat(typeNotif1).isNotEqualTo(typeNotif2);
    }
}
