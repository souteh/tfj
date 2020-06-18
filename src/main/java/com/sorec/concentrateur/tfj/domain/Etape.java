package com.sorec.concentrateur.tfj.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Etape.
 */
@Entity
@Table(name = "etape")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etape implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idetape", nullable = false)
    private Long idetape;

    @Size(max = 254)
    @Column(name = "libelleetape", length = 254)
    private String libelleetape;

    @Size(max = 254)
    @Column(name = "statutetape", length = 254)
    private String statutetape;

    @Size(max = 254)
    @Column(name = "heureexecutionetape", length = 254)
    private String heureexecutionetape;

    @Size(max = 254)
    @Column(name = "codeetape", length = 254)
    private String codeetape;

    @ManyToOne
    @JsonIgnoreProperties(value = "etapes", allowSetters = true)
    private Tfj idtfj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdetape() {
        return idetape;
    }

    public Etape idetape(Long idetape) {
        this.idetape = idetape;
        return this;
    }

    public void setIdetape(Long idetape) {
        this.idetape = idetape;
    }

    public String getLibelleetape() {
        return libelleetape;
    }

    public Etape libelleetape(String libelleetape) {
        this.libelleetape = libelleetape;
        return this;
    }

    public void setLibelleetape(String libelleetape) {
        this.libelleetape = libelleetape;
    }

    public String getStatutetape() {
        return statutetape;
    }

    public Etape statutetape(String statutetape) {
        this.statutetape = statutetape;
        return this;
    }

    public void setStatutetape(String statutetape) {
        this.statutetape = statutetape;
    }

    public String getHeureexecutionetape() {
        return heureexecutionetape;
    }

    public Etape heureexecutionetape(String heureexecutionetape) {
        this.heureexecutionetape = heureexecutionetape;
        return this;
    }

    public void setHeureexecutionetape(String heureexecutionetape) {
        this.heureexecutionetape = heureexecutionetape;
    }

    public String getCodeetape() {
        return codeetape;
    }

    public Etape codeetape(String codeetape) {
        this.codeetape = codeetape;
        return this;
    }

    public void setCodeetape(String codeetape) {
        this.codeetape = codeetape;
    }

    public Tfj getIdtfj() {
        return idtfj;
    }

    public Etape idtfj(Tfj tfj) {
        this.idtfj = tfj;
        return this;
    }

    public void setIdtfj(Tfj tfj) {
        this.idtfj = tfj;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etape)) {
            return false;
        }
        return id != null && id.equals(((Etape) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etape{" +
            "id=" + getId() +
            ", idetape=" + getIdetape() +
            ", libelleetape='" + getLibelleetape() + "'" +
            ", statutetape='" + getStatutetape() + "'" +
            ", heureexecutionetape='" + getHeureexecutionetape() + "'" +
            ", codeetape='" + getCodeetape() + "'" +
            "}";
    }
}
