package com.sorec.concentrateur.tfj.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sorec.concentrateur.tfj.web.rest.TestUtil;

public class TfjTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tfj.class);
        Tfj tfj1 = new Tfj();
        tfj1.setId(1L);
        Tfj tfj2 = new Tfj();
        tfj2.setId(tfj1.getId());
        assertThat(tfj1).isEqualTo(tfj2);
        tfj2.setId(2L);
        assertThat(tfj1).isNotEqualTo(tfj2);
        tfj1.setId(null);
        assertThat(tfj1).isNotEqualTo(tfj2);
    }
}
