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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Tfj} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.TfjResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tfjs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TfjCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idtfj;

    private InstantFilter datetfj;

    private StringFilter statuttfj;

    public TfjCriteria() {
    }

    public TfjCriteria(TfjCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idtfj = other.idtfj == null ? null : other.idtfj.copy();
        this.datetfj = other.datetfj == null ? null : other.datetfj.copy();
        this.statuttfj = other.statuttfj == null ? null : other.statuttfj.copy();
    }

    @Override
    public TfjCriteria copy() {
        return new TfjCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdtfj() {
        return idtfj;
    }

    public void setIdtfj(LongFilter idtfj) {
        this.idtfj = idtfj;
    }

    public InstantFilter getDatetfj() {
        return datetfj;
    }

    public void setDatetfj(InstantFilter datetfj) {
        this.datetfj = datetfj;
    }

    public StringFilter getStatuttfj() {
        return statuttfj;
    }

    public void setStatuttfj(StringFilter statuttfj) {
        this.statuttfj = statuttfj;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TfjCriteria that = (TfjCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idtfj, that.idtfj) &&
            Objects.equals(datetfj, that.datetfj) &&
            Objects.equals(statuttfj, that.statuttfj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idtfj,
        datetfj,
        statuttfj
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TfjCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idtfj != null ? "idtfj=" + idtfj + ", " : "") +
                (datetfj != null ? "datetfj=" + datetfj + ", " : "") +
                (statuttfj != null ? "statuttfj=" + statuttfj + ", " : "") +
            "}";
    }

}
