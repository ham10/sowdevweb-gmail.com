package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ResultatActeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatActe.class);
        ResultatActe resultatActe1 = new ResultatActe();
        resultatActe1.setId(1L);
        ResultatActe resultatActe2 = new ResultatActe();
        resultatActe2.setId(resultatActe1.getId());
        assertThat(resultatActe1).isEqualTo(resultatActe2);
        resultatActe2.setId(2L);
        assertThat(resultatActe1).isNotEqualTo(resultatActe2);
        resultatActe1.setId(null);
        assertThat(resultatActe1).isNotEqualTo(resultatActe2);
    }
}
