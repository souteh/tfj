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
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Actionparam} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.ActionparamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /actionparams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActionparamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idactionparam;

    private StringFilter libelleactionparam;

    private StringFilter codeactionparam;

    private LongFilter idetapeparamId;

    public ActionparamCriteria() {
    }

    public ActionparamCriteria(ActionparamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idactionparam = other.idactionparam == null ? null : other.idactionparam.copy();
        this.libelleactionparam = other.libelleactionparam == null ? null : other.libelleactionparam.copy();
        this.codeactionparam = other.codeactionparam == null ? null : other.codeactionparam.copy();
        this.idetapeparamId = other.idetapeparamId == null ? null : other.idetapeparamId.copy();
    }

    @Override
    public ActionparamCriteria copy() {
        return new ActionparamCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdactionparam() {
        return idactionparam;
    }

    public void setIdactionparam(LongFilter idactionparam) {
        this.idactionparam = idactionparam;
    }

    public StringFilter getLibelleactionparam() {
        return libelleactionparam;
    }

    public void setLibelleactionparam(StringFilter libelleactionparam) {
        this.libelleactionparam = libelleactionparam;
    }

    public StringFilter getCodeactionparam() {
        return codeactionparam;
    }

    public void setCodeactionparam(StringFilter codeactionparam) {
        this.codeactionparam = codeactionparam;
    }

    public LongFilter getIdetapeparamId() {
        return idetapeparamId;
    }

    public void setIdetapeparamId(LongFilter idetapeparamId) {
        this.idetapeparamId = idetapeparamId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActionparamCriteria that = (ActionparamCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idactionparam, that.idactionparam) &&
            Objects.equals(libelleactionparam, that.libelleactionparam) &&
            Objects.equals(codeactionparam, that.codeactionparam) &&
            Objects.equals(idetapeparamId, that.idetapeparamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idactionparam,
        libelleactionparam,
        codeactionparam,
        idetapeparamId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionparamCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idactionparam != null ? "idactionparam=" + idactionparam + ", " : "") +
                (libelleactionparam != null ? "libelleactionparam=" + libelleactionparam + ", " : "") +
                (codeactionparam != null ? "codeactionparam=" + codeactionparam + ", " : "") +
                (idetapeparamId != null ? "idetapeparamId=" + idetapeparamId + ", " : "") +
            "}";
    }

}
