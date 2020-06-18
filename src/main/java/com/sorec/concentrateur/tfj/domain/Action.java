package com.sorec.concentrateur.tfj.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idaction", nullable = false)
    private Long idaction;

    @Size(max = 254)
    @Column(name = "libelleaction", length = 254)
    private String libelleaction;

    @Size(max = 254)
    @Column(name = "statutaction", length = 254)
    private String statutaction;

    @Size(max = 254)
    @Column(name = "codeaction", length = 254)
    private String codeaction;

    @ManyToOne
    @JsonIgnoreProperties(value = "actions", allowSetters = true)
    private Etape idetape;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdaction() {
        return idaction;
    }

    public Action idaction(Long idaction) {
        this.idaction = idaction;
        return this;
    }

    public void setIdaction(Long idaction) {
        this.idaction = idaction;
    }

    public String getLibelleaction() {
        return libelleaction;
    }

    public Action libelleaction(String libelleaction) {
        this.libelleaction = libelleaction;
        return this;
    }

    public void setLibelleaction(String libelleaction) {
        this.libelleaction = libelleaction;
    }

    public String getStatutaction() {
        return statutaction;
    }

    public Action statutaction(String statutaction) {
        this.statutaction = statutaction;
        return this;
    }

    public void setStatutaction(String statutaction) {
        this.statutaction = statutaction;
    }

    public String getCodeaction() {
        return codeaction;
    }

    public Action codeaction(String codeaction) {
        this.codeaction = codeaction;
        return this;
    }

    public void setCodeaction(String codeaction) {
        this.codeaction = codeaction;
    }

    public Etape getIdetape() {
        return idetape;
    }

    public Action idetape(Etape etape) {
        this.idetape = etape;
        return this;
    }

    public void setIdetape(Etape etape) {
        this.idetape = etape;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", idaction=" + getIdaction() +
            ", libelleaction='" + getLibelleaction() + "'" +
            ", statutaction='" + getStatutaction() + "'" +
            ", codeaction='" + getCodeaction() + "'" +
            "}";
    }
}
