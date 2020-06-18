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

import com.sorec.concentrateur.tfj.domain.Action;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.ActionRepository;
import com.sorec.concentrateur.tfj.service.dto.ActionCriteria;

/**
 * Service for executing complex queries for {@link Action} entities in the database.
 * The main input is a {@link ActionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Action} or a {@link Page} of {@link Action} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActionQueryService extends QueryService<Action> {

    private final Logger log = LoggerFactory.getLogger(ActionQueryService.class);

    private final ActionRepository actionRepository;

    public ActionQueryService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    /**
     * Return a {@link List} of {@link Action} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Action> findByCriteria(ActionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Action> specification = createSpecification(criteria);
        return actionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Action} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Action> findByCriteria(ActionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Action> specification = createSpecification(criteria);
        return actionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Action> specification = createSpecification(criteria);
        return actionRepository.count(specification);
    }

    /**
     * Function to convert {@link ActionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Action> createSpecification(ActionCriteria criteria) {
        Specification<Action> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Action_.id));
            }
            if (criteria.getIdaction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdaction(), Action_.idaction));
            }
            if (criteria.getLibelleaction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleaction(), Action_.libelleaction));
            }
            if (criteria.getStatutaction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatutaction(), Action_.statutaction));
            }
            if (criteria.getCodeaction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeaction(), Action_.codeaction));
            }
            if (criteria.getIdetapeId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdetapeId(),
                    root -> root.join(Action_.idetape, JoinType.LEFT).get(Etape_.id)));
            }
        }
        return specification;
    }
}
