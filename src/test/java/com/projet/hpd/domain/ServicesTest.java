package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Services.class);
        Services services1 = new Services();
        services1.setId(1L);
        Services services2 = new Services();
        services2.setId(services1.getId());
        assertThat(services1).isEqualTo(services2);
        services2.setId(2L);
        assertThat(services1).isNotEqualTo(services2);
        services1.setId(null);
        assertThat(services1).isNotEqualTo(services2);
    }
}
