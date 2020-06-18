package com.sorec.concentrateur.tfj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tfj.
 */
@Entity
@Table(name = "tfj")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tfj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idtfj", nullable = false)
    private Long idtfj;

    @Column(name = "datetfj")
    private Instant datetfj;

    @Size(max = 254)
    @Column(name = "statuttfj", length = 254)
    private String statuttfj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdtfj() {
        return idtfj;
    }

    public Tfj idtfj(Long idtfj) {
        this.idtfj = idtfj;
        return this;
    }

    public void setIdtfj(Long idtfj) {
        this.idtfj = idtfj;
    }

    public Instant getDatetfj() {
        return datetfj;
    }

    public Tfj datetfj(Instant datetfj) {
        this.datetfj = datetfj;
        return this;
    }

    public void setDatetfj(Instant datetfj) {
        this.datetfj = datetfj;
    }

    public String getStatuttfj() {
        return statuttfj;
    }

    public Tfj statuttfj(String statuttfj) {
        this.statuttfj = statuttfj;
        return this;
    }

    public void setStatuttfj(String statuttfj) {
        this.statuttfj = statuttfj;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tfj)) {
            return false;
        }
        return id != null && id.equals(((Tfj) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tfj{" +
            "id=" + getId() +
            ", idtfj=" + getIdtfj() +
            ", datetfj='" + getDatetfj() + "'" +
            ", statuttfj='" + getStatuttfj() + "'" +
            "}";
    }
}
