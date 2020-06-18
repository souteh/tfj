package com.sorec.concentrateur.tfj.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Messageerreur.
 */
@Entity
@Table(name = "messageerreur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Messageerreur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idmessage", nullable = false)
    private Long idmessage;

    @Size(max = 254)
    @Column(name = "description", length = 254)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "messageerreurs", allowSetters = true)
    private Action idaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdmessage() {
        return idmessage;
    }

    public Messageerreur idmessage(Long idmessage) {
        this.idmessage = idmessage;
        return this;
    }

    public void setIdmessage(Long idmessage) {
        this.idmessage = idmessage;
    }

    public String getDescription() {
        return description;
    }

    public Messageerreur description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getIdaction() {
        return idaction;
    }

    public Messageerreur idaction(Action action) {
        this.idaction = action;
        return this;
    }

    public void setIdaction(Action action) {
        this.idaction = action;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Messageerreur)) {
            return false;
        }
        return id != null && id.equals(((Messageerreur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Messageerreur{" +
            "id=" + getId() +
            ", idmessage=" + getIdmessage() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
