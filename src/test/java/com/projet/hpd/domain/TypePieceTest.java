package com.projet.hpd.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.projet.hpd.web.rest.TestUtil;

public class TypePieceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePiece.class);
        TypePiece typePiece1 = new TypePiece();
        typePiece1.setId(1L);
        TypePiece typePiece2 = new TypePiece();
        typePiece2.setId(typePiece1.getId());
        assertThat(typePiece1).isEqualTo(typePiece2);
        typePiece2.setId(2L);
        assertThat(typePiece1).isNotEqualTo(typePiece2);
        typePiece1.setId(null);
        assertThat(typePiece1).isNotEqualTo(typePiece2);
    }
}
