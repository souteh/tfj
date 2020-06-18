package com.sorec.concentrateur.tfj.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Statutenum} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.StatutenumResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /statutenums?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatutenumCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idstatut;

    private StringFilter libellestatut;

    private StringFilter codestatut;

    public StatutenumCriteria() {
    }

    public StatutenumCriteria(StatutenumCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idstatut = other.idstatut == null ? null : other.idstatut.copy();
        this.libellestatut = other.libellestatut == null ? null : other.libellestatut.copy();
        this.codestatut = other.codestatut == null ? null : other.codestatut.copy();
    }

    @Override
    public StatutenumCriteria copy() {
        return new StatutenumCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdstatut() {
        return idstatut;
    }

    public void setIdstatut(LongFilter idstatut) {
        this.idstatut = idstatut;
    }

    public StringFilter getLibellestatut() {
        return libellestatut;
    }

    public void setLibellestatut(StringFilter libellestatut) {
        this.libellestatut = libellestatut;
    }

    public StringFilter getCodestatut() {
        return codestatut;
    }

    public void setCodestatut(StringFilter codestatut) {
        this.codestatut = codestatut;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StatutenumCriteria that = (StatutenumCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idstatut, that.idstatut) &&
            Objects.equals(libellestatut, that.libellestatut) &&
            Objects.equals(codestatut, that.codestatut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idstatut,
        libellestatut,
        codestatut
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatutenumCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idstatut != null ? "idstatut=" + idstatut + ", " : "") +
                (libellestatut != null ? "libellestatut=" + libellestatut + ", " : "") +
                (codestatut != null ? "codestatut=" + codestatut + ", " : "") +
            "}";
    }

}
