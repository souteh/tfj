package com.sorec.concentrateur.tfj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Statutenum.
 */
@Entity
@Table(name = "statutenum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Statutenum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idstatut")
    private Long idstatut;

    @Size(max = 254)
    @Column(name = "libellestatut", length = 254)
    private String libellestatut;

    @Size(max = 254)
    @Column(name = "codestatut", length = 254)
    private String codestatut;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdstatut() {
        return idstatut;
    }

    public Statutenum idstatut(Long idstatut) {
        this.idstatut = idstatut;
        return this;
    }

    public void setIdstatut(Long idstatut) {
        this.idstatut = idstatut;
    }

    public String getLibellestatut() {
        return libellestatut;
    }

    public Statutenum libellestatut(String libellestatut) {
        this.libellestatut = libellestatut;
        return this;
    }

    public void setLibellestatut(String libellestatut) {
        this.libellestatut = libellestatut;
    }

    public String getCodestatut() {
        return codestatut;
    }

    public Statutenum codestatut(String codestatut) {
        this.codestatut = codestatut;
        return this;
    }

    public void setCodestatut(String codestatut) {
        this.codestatut = codestatut;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statutenum)) {
            return false;
        }
        return id != null && id.equals(((Statutenum) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statutenum{" +
            "id=" + getId() +
            ", idstatut=" + getIdstatut() +
            ", libellestatut='" + getLibellestatut() + "'" +
            ", codestatut='" + getCodestatut() + "'" +
            "}";
    }
}
