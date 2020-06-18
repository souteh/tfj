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
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Etapeparam} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.EtapeparamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /etapeparams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EtapeparamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idetapeparam;

    private StringFilter libelleetapeparam;

    private StringFilter codeetapeparam;

    public EtapeparamCriteria() {
    }

    public EtapeparamCriteria(EtapeparamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idetapeparam = other.idetapeparam == null ? null : other.idetapeparam.copy();
        this.libelleetapeparam = other.libelleetapeparam == null ? null : other.libelleetapeparam.copy();
        this.codeetapeparam = other.codeetapeparam == null ? null : other.codeetapeparam.copy();
    }

    @Override
    public EtapeparamCriteria copy() {
        return new EtapeparamCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdetapeparam() {
        return idetapeparam;
    }

    public void setIdetapeparam(LongFilter idetapeparam) {
        this.idetapeparam = idetapeparam;
    }

    public StringFilter getLibelleetapeparam() {
        return libelleetapeparam;
    }

    public void setLibelleetapeparam(StringFilter libelleetapeparam) {
        this.libelleetapeparam = libelleetapeparam;
    }

    public StringFilter getCodeetapeparam() {
        return codeetapeparam;
    }

    public void setCodeetapeparam(StringFilter codeetapeparam) {
        this.codeetapeparam = codeetapeparam;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EtapeparamCriteria that = (EtapeparamCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idetapeparam, that.idetapeparam) &&
            Objects.equals(libelleetapeparam, that.libelleetapeparam) &&
            Objects.equals(codeetapeparam, that.codeetapeparam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idetapeparam,
        libelleetapeparam,
        codeetapeparam
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapeparamCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idetapeparam != null ? "idetapeparam=" + idetapeparam + ", " : "") +
                (libelleetapeparam != null ? "libelleetapeparam=" + libelleetapeparam + ", " : "") +
                (codeetapeparam != null ? "codeetapeparam=" + codeetapeparam + ", " : "") +
            "}";
    }

}
