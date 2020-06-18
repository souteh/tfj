package com.sorec.concentrateur.tfj.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sorec.concentrateur.tfj.domain.Messageerreur;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.MessageerreurRepository;
import com.sorec.concentrateur.tfj.service.dto.MessageerreurCriteria;

/**
 * Service for executing complex queries for {@link Messageerreur} entities in the database.
 * The main input is a {@link MessageerreurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Messageerreur} or a {@link Page} of {@link Messageerreur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageerreurQueryService extends QueryService<Messageerreur> {

    private final Logger log = LoggerFactory.getLogger(MessageerreurQueryService.class);

    private final MessageerreurRepository messageerreurRepository;

    public MessageerreurQueryService(MessageerreurRepository messageerreurRepository) {
        this.messageerreurRepository = messageerreurRepository;
    }

    /**
     * Return a {@link List} of {@link Messageerreur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Messageerreur> findByCriteria(MessageerreurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Messageerreur> specification = createSpecification(criteria);
        return messageerreurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Messageerreur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Messageerreur> findByCriteria(MessageerreurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Messageerreur> specification = createSpecification(criteria);
        return messageerreurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageerreurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Messageerreur> specification = createSpecification(criteria);
        return messageerreurRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageerreurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Messageerreur> createSpecification(MessageerreurCriteria criteria) {
        Specification<Messageerreur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Messageerreur_.id));
            }
            if (criteria.getIdmessage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdmessage(), Messageerreur_.idmessage));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Messageerreur_.description));
            }
            if (criteria.getIdactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdactionId(),
                    root -> root.join(Messageerreur_.idaction, JoinType.LEFT).get(Action_.id)));
            }
        }
        return specification;
    }
}
