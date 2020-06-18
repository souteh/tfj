package com.sorec.concentrateur.tfj.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sorec.concentrateur.tfj.web.rest.TestUtil;

public class MessageerreurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Messageerreur.class);
        Messageerreur messageerreur1 = new Messageerreur();
        messageerreur1.setId(1L);
        Messageerreur messageerreur2 = new Messageerreur();
        messageerreur2.setId(messageerreur1.getId());
        assertThat(messageerreur1).isEqualTo(messageerreur2);
        messageerreur2.setId(2L);
        assertThat(messageerreur1).isNotEqualTo(messageerreur2);
        messageerreur1.setId(null);
        assertThat(messageerreur1).isNotEqualTo(messageerreur2);
    }
}
