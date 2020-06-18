package com.sorec.concentrateur.tfj.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Actionparam.
 */
@Entity
@Table(name = "actionparam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Actionparam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idactionparam", nullable = false)
    private Long idactionparam;

    @Size(max = 254)
    @Column(name = "libelleactionparam", length = 254)
    private String libelleactionparam;

    @Size(max = 254)
    @Column(name = "codeactionparam", length = 254)
    private String codeactionparam;

    @ManyToOne
    @JsonIgnoreProperties(value = "actionparams", allowSetters = true)
    private Etapeparam idetapeparam;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdactionparam() {
        return idactionparam;
    }

    public Actionparam idactionparam(Long idactionparam) {
        this.idactionparam = idactionparam;
        return this;
    }

    public void setIdactionparam(Long idactionparam) {
        this.idactionparam = idactionparam;
    }

    public String getLibelleactionparam() {
        return libelleactionparam;
    }

    public Actionparam libelleactionparam(String libelleactionparam) {
        this.libelleactionparam = libelleactionparam;
        return this;
    }

    public void setLibelleactionparam(String libelleactionparam) {
        this.libelleactionparam = libelleactionparam;
    }

    public String getCodeactionparam() {
        return codeactionparam;
    }

    public Actionparam codeactionparam(String codeactionparam) {
        this.codeactionparam = codeactionparam;
        return this;
    }

    public void setCodeactionparam(String codeactionparam) {
        this.codeactionparam = codeactionparam;
    }

    public Etapeparam getIdetapeparam() {
        return idetapeparam;
    }

    public Actionparam idetapeparam(Etapeparam etapeparam) {
        this.idetapeparam = etapeparam;
        return this;
    }

    public void setIdetapeparam(Etapeparam etapeparam) {
        this.idetapeparam = etapeparam;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actionparam)) {
            return false;
        }
        return id != null && id.equals(((Actionparam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Actionparam{" +
            "id=" + getId() +
            ", idactionparam=" + getIdactionparam() +
            ", libelleactionparam='" + getLibelleactionparam() + "'" +
            ", codeactionparam='" + getCodeactionparam() + "'" +
            "}";
    }
}
