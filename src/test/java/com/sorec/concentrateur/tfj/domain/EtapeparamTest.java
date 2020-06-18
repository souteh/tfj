package com.sorec.concentrateur.tfj.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sorec.concentrateur.tfj.web.rest.TestUtil;

public class EtapeparamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etapeparam.class);
        Etapeparam etapeparam1 = new Etapeparam();
        etapeparam1.setId(1L);
        Etapeparam etapeparam2 = new Etapeparam();
        etapeparam2.setId(etapeparam1.getId());
        assertThat(etapeparam1).isEqualTo(etapeparam2);
        etapeparam2.setId(2L);
        assertThat(etapeparam1).isNotEqualTo(etapeparam2);
        etapeparam1.setId(null);
        assertThat(etapeparam1).isNotEqualTo(etapeparam2);
    }
}
