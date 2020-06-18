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

import com.sorec.concentrateur.tfj.domain.Statutenum;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.StatutenumRepository;
import com.sorec.concentrateur.tfj.service.dto.StatutenumCriteria;

/**
 * Service for executing complex queries for {@link Statutenum} entities in the database.
 * The main input is a {@link StatutenumCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Statutenum} or a {@link Page} of {@link Statutenum} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatutenumQueryService extends QueryService<Statutenum> {

    private final Logger log = LoggerFactory.getLogger(StatutenumQueryService.class);

    private final StatutenumRepository statutenumRepository;

    public StatutenumQueryService(StatutenumRepository statutenumRepository) {
        this.statutenumRepository = statutenumRepository;
    }

    /**
     * Return a {@link List} of {@link Statutenum} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Statutenum> findByCriteria(StatutenumCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Statutenum> specification = createSpecification(criteria);
        return statutenumRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Statutenum} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Statutenum> findByCriteria(StatutenumCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Statutenum> specification = createSpecification(criteria);
        return statutenumRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatutenumCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Statutenum> specification = createSpecification(criteria);
        return statutenumRepository.count(specification);
    }

    /**
     * Function to convert {@link StatutenumCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Statutenum> createSpecification(StatutenumCriteria criteria) {
        Specification<Statutenum> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Statutenum_.id));
            }
            if (criteria.getIdstatut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdstatut(), Statutenum_.idstatut));
            }
            if (criteria.getLibellestatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibellestatut(), Statutenum_.libellestatut));
            }
            if (criteria.getCodestatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodestatut(), Statutenum_.codestatut));
            }
        }
        return specification;
    }
}
