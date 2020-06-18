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

import com.sorec.concentrateur.tfj.domain.Actionparam;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.ActionparamRepository;
import com.sorec.concentrateur.tfj.service.dto.ActionparamCriteria;

/**
 * Service for executing complex queries for {@link Actionparam} entities in the database.
 * The main input is a {@link ActionparamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Actionparam} or a {@link Page} of {@link Actionparam} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActionparamQueryService extends QueryService<Actionparam> {

    private final Logger log = LoggerFactory.getLogger(ActionparamQueryService.class);

    private final ActionparamRepository actionparamRepository;

    public ActionparamQueryService(ActionparamRepository actionparamRepository) {
        this.actionparamRepository = actionparamRepository;
    }

    /**
     * Return a {@link List} of {@link Actionparam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Actionparam> findByCriteria(ActionparamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Actionparam> specification = createSpecification(criteria);
        return actionparamRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Actionparam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Actionparam> findByCriteria(ActionparamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Actionparam> specification = createSpecification(criteria);
        return actionparamRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActionparamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Actionparam> specification = createSpecification(criteria);
        return actionparamRepository.count(specification);
    }

    /**
     * Function to convert {@link ActionparamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Actionparam> createSpecification(ActionparamCriteria criteria) {
        Specification<Actionparam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Actionparam_.id));
            }
            if (criteria.getIdactionparam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdactionparam(), Actionparam_.idactionparam));
            }
            if (criteria.getLibelleactionparam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleactionparam(), Actionparam_.libelleactionparam));
            }
            if (criteria.getCodeactionparam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeactionparam(), Actionparam_.codeactionparam));
            }
            if (criteria.getIdetapeparamId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdetapeparamId(),
                    root -> root.join(Actionparam_.idetapeparam, JoinType.LEFT).get(Etapeparam_.id)));
            }
        }
        return specification;
    }
}
