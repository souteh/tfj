package com.sorec.concentrateur.tfj.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sorec.concentrateur.tfj.web.rest.TestUtil;

public class ActionparamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actionparam.class);
        Actionparam actionparam1 = new Actionparam();
        actionparam1.setId(1L);
        Actionparam actionparam2 = new Actionparam();
        actionparam2.setId(actionparam1.getId());
        assertThat(actionparam1).isEqualTo(actionparam2);
        actionparam2.setId(2L);
        assertThat(actionparam1).isNotEqualTo(actionparam2);
        actionparam1.setId(null);
        assertThat(actionparam1).isNotEqualTo(actionparam2);
    }
}
