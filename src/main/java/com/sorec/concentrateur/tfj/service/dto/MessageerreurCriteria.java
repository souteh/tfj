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
 * Criteria class for the {@link com.sorec.concentrateur.tfj.domain.Messageerreur} entity. This class is used
 * in {@link com.sorec.concentrateur.tfj.web.rest.MessageerreurResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /messageerreurs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageerreurCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter idmessage;

    private StringFilter description;

    private LongFilter idactionId;

    public MessageerreurCriteria() {
    }

    public MessageerreurCriteria(MessageerreurCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idmessage = other.idmessage == null ? null : other.idmessage.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.idactionId = other.idactionId == null ? null : other.idactionId.copy();
    }

    @Override
    public MessageerreurCriteria copy() {
        return new MessageerreurCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(LongFilter idmessage) {
        this.idmessage = idmessage;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getIdactionId() {
        return idactionId;
    }

    public void setIdactionId(LongFilter idactionId) {
        this.idactionId = idactionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageerreurCriteria that = (MessageerreurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idmessage, that.idmessage) &&
            Objects.equals(description, that.description) &&
            Objects.equals(idactionId, that.idactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idmessage,
        description,
        idactionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageerreurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idmessage != null ? "idmessage=" + idmessage + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (idactionId != null ? "idactionId=" + idactionId + ", " : "") +
            "}";
    }

}
