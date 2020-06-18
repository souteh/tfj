package com.sorec.concentrateur.tfj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Etapeparam.
 */
@Entity
@Table(name = "etapeparam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etapeparam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idetapeparam", nullable = false)
    private Long idetapeparam;

    @Size(max = 254)
    @Column(name = "libelleetapeparam", length = 254)
    private String libelleetapeparam;

    @Size(max = 254)
    @Column(name = "codeetapeparam", length = 254)
    private String codeetapeparam;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdetapeparam() {
        return idetapeparam;
    }

    public Etapeparam idetapeparam(Long idetapeparam) {
        this.idetapeparam = idetapeparam;
        return this;
    }

    public void setIdetapeparam(Long idetapeparam) {
        this.idetapeparam = idetapeparam;
    }

    public String getLibelleetapeparam() {
        return libelleetapeparam;
    }

    public Etapeparam libelleetapeparam(String libelleetapeparam) {
        this.libelleetapeparam = libelleetapeparam;
        return this;
    }

    public void setLibelleetapeparam(String libelleetapeparam) {
        this.libelleetapeparam = libelleetapeparam;
    }

    public String getCodeetapeparam() {
        return codeetapeparam;
    }

    public Etapeparam codeetapeparam(String codeetapeparam) {
        this.codeetapeparam = codeetapeparam;
        return this;
    }

    public void setCodeetapeparam(String codeetapeparam) {
        this.codeetapeparam = codeetapeparam;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etapeparam)) {
            return false;
        }
        return id != null && id.equals(((Etapeparam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etapeparam{" +
            "id=" + getId() +
            ", idetapeparam=" + getIdetapeparam() +
            ", libelleetapeparam='" + getLibelleetapeparam() + "'" +
            ", codeetapeparam='" + getCodeetapeparam() + "'" +
            "}";
    }
}
