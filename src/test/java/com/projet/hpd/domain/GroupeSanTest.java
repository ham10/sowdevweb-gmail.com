package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class GroupeSanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeSan.class);
        GroupeSan groupeSan1 = new GroupeSan();
        groupeSan1.setId(1L);
        GroupeSan groupeSan2 = new GroupeSan();
        groupeSan2.setId(groupeSan1.getId());
        assertThat(groupeSan1).isEqualTo(groupeSan2);
        groupeSan2.setId(2L);
        assertThat(groupeSan1).isNotEqualTo(groupeSan2);
        groupeSan1.setId(null);
        assertThat(groupeSan1).isNotEqualTo(groupeSan2);
    }
}
