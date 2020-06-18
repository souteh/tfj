package com.sorec.concentrateur.tfj.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sorec.concentrateur.tfj.web.rest.TestUtil;

public class StatutenumTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statutenum.class);
        Statutenum statutenum1 = new Statutenum();
        statutenum1.setId(1L);
        Statutenum statutenum2 = new Statutenum();
        statutenum2.setId(statutenum1.getId());
        assertThat(statutenum1).isEqualTo(statutenum2);
        statutenum2.setId(2L);
        assertThat(statutenum1).isNotEqualTo(statutenum2);
        statutenum1.setId(null);
        assertThat(statutenum1).isNotEqualTo(statutenum2);
    }
}
