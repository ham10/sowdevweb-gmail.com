package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class SchemaComptaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaCompta.class);
        SchemaCompta schemaCompta1 = new SchemaCompta();
        schemaCompta1.setId(1L);
        SchemaCompta schemaCompta2 = new SchemaCompta();
        schemaCompta2.setId(schemaCompta1.getId());
        assertThat(schemaCompta1).isEqualTo(schemaCompta2);
        schemaCompta2.setId(2L);
        assertThat(schemaCompta1).isNotEqualTo(schemaCompta2);
        schemaCompta1.setId(null);
        assertThat(schemaCompta1).isNotEqualTo(schemaCompta2);
    }
}
