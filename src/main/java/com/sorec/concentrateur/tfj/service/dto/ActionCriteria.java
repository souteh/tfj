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
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Action} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.ActionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /actions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idaction;

    private StringFilter libelleaction;

    private StringFilter statutaction;

    private StringFilter codeaction;

    private LongFilter idetapeId;

    public ActionCriteria() {
    }

    public ActionCriteria(ActionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idaction = other.idaction == null ? null : other.idaction.copy();
        this.libelleaction = other.libelleaction == null ? null : other.libelleaction.copy();
        this.statutaction = other.statutaction == null ? null : other.statutaction.copy();
        this.codeaction = other.codeaction == null ? null : other.codeaction.copy();
        this.idetapeId = other.idetapeId == null ? null : other.idetapeId.copy();
    }

    @Override
    public ActionCriteria copy() {
        return new ActionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdaction() {
        return idaction;
    }

    public void setIdaction(LongFilter idaction) {
        this.idaction = idaction;
    }

    public StringFilter getLibelleaction() {
        return libelleaction;
    }

    public void setLibelleaction(StringFilter libelleaction) {
        this.libelleaction = libelleaction;
    }

    public StringFilter getStatutaction() {
        return statutaction;
    }

    public void setStatutaction(StringFilter statutaction) {
        this.statutaction = statutaction;
    }

    public StringFilter getCodeaction() {
        return codeaction;
    }

    public void setCodeaction(StringFilter codeaction) {
        this.codeaction = codeaction;
    }

    public LongFilter getIdetapeId() {
        return idetapeId;
    }

    public void setIdetapeId(LongFilter idetapeId) {
        this.idetapeId = idetapeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActionCriteria that = (ActionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idaction, that.idaction) &&
            Objects.equals(libelleaction, that.libelleaction) &&
            Objects.equals(statutaction, that.statutaction) &&
            Objects.equals(codeaction, that.codeaction) &&
            Objects.equals(idetapeId, that.idetapeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idaction,
        libelleaction,
        statutaction,
        codeaction,
        idetapeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idaction != null ? "idaction=" + idaction + ", " : "") +
                (libelleaction != null ? "libelleaction=" + libelleaction + ", " : "") +
                (statutaction != null ? "statutaction=" + statutaction + ", " : "") +
                (codeaction != null ? "codeaction=" + codeaction + ", " : "") +
                (idetapeId != null ? "idetapeId=" + idetapeId + ", " : "") +
            "}";
    }

}
