package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class ClasseProdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseProd.class);
        ClasseProd classeProd1 = new ClasseProd();
        classeProd1.setId(1L);
        ClasseProd classeProd2 = new ClasseProd();
        classeProd2.setId(classeProd1.getId());
        assertThat(classeProd1).isEqualTo(classeProd2);
        classeProd2.setId(2L);
        assertThat(classeProd1).isNotEqualTo(classeProd2);
        classeProd1.setId(null);
        assertThat(classeProd1).isNotEqualTo(classeProd2);
    }
}
