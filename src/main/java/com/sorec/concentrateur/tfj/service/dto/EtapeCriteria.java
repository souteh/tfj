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
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Etape} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.EtapeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /etapes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EtapeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idetape;

    private StringFilter libelleetape;

    private StringFilter statutetape;

    private StringFilter heureexecutionetape;

    private StringFilter codeetape;

    private LongFilter idtfjId;

    public EtapeCriteria() {
    }

    public EtapeCriteria(EtapeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idetape = other.idetape == null ? null : other.idetape.copy();
        this.libelleetape = other.libelleetape == null ? null : other.libelleetape.copy();
        this.statutetape = other.statutetape == null ? null : other.statutetape.copy();
        this.heureexecutionetape = other.heureexecutionetape == null ? null : other.heureexecutionetape.copy();
        this.codeetape = other.codeetape == null ? null : other.codeetape.copy();
        this.idtfjId = other.idtfjId == null ? null : other.idtfjId.copy();
    }

    @Override
    public EtapeCriteria copy() {
        return new EtapeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdetape() {
        return idetape;
    }

    public void setIdetape(LongFilter idetape) {
        this.idetape = idetape;
    }

    public StringFilter getLibelleetape() {
        return libelleetape;
    }

    public void setLibelleetape(StringFilter libelleetape) {
        this.libelleetape = libelleetape;
    }

    public StringFilter getStatutetape() {
        return statutetape;
    }

    public void setStatutetape(StringFilter statutetape) {
        this.statutetape = statutetape;
    }

    public StringFilter getHeureexecutionetape() {
        return heureexecutionetape;
    }

    public void setHeureexecutionetape(StringFilter heureexecutionetape) {
        this.heureexecutionetape = heureexecutionetape;
    }

    public StringFilter getCodeetape() {
        return codeetape;
    }

    public void setCodeetape(StringFilter codeetape) {
        this.codeetape = codeetape;
    }

    public LongFilter getIdtfjId() {
        return idtfjId;
    }

    public void setIdtfjId(LongFilter idtfjId) {
        this.idtfjId = idtfjId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EtapeCriteria that = (EtapeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idetape, that.idetape) &&
            Objects.equals(libelleetape, that.libelleetape) &&
            Objects.equals(statutetape, that.statutetape) &&
            Objects.equals(heureexecutionetape, that.heureexecutionetape) &&
            Objects.equals(codeetape, that.codeetape) &&
            Objects.equals(idtfjId, that.idtfjId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idetape,
        libelleetape,
        statutetape,
        heureexecutionetape,
        codeetape,
        idtfjId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idetape != null ? "idetape=" + idetape + ", " : "") +
                (libelleetape != null ? "libelleetape=" + libelleetape + ", " : "") +
                (statutetape != null ? "statutetape=" + statutetape + ", " : "") +
                (heureexecutionetape != null ? "heureexecutionetape=" + heureexecutionetape + ", " : "") +
                (codeetape != null ? "codeetape=" + codeetape + ", " : "") +
                (idtfjId != null ? "idtfjId=" + idtfjId + ", " : "") +
            "}";
    }

}
