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

import com.sorec.concentrateur.tfj.domain.Etape;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.EtapeRepository;
import com.sorec.concentrateur.tfj.service.dto.EtapeCriteria;

/**
 * Service for executing complex queries for {@link Etape} entities in the database.
 * The main input is a {@link EtapeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Etape} or a {@link Page} of {@link Etape} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EtapeQueryService extends QueryService<Etape> {

    private final Logger log = LoggerFactory.getLogger(EtapeQueryService.class);

    private final EtapeRepository etapeRepository;

    public EtapeQueryService(EtapeRepository etapeRepository) {
        this.etapeRepository = etapeRepository;
    }

    /**
     * Return a {@link List} of {@link Etape} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Etape> findByCriteria(EtapeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Etape> specification = createSpecification(criteria);
        return etapeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Etape} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Etape> findByCriteria(EtapeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Etape> specification = createSpecification(criteria);
        return etapeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EtapeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Etape> specification = createSpecification(criteria);
        return etapeRepository.count(specification);
    }

    /**
     * Function to convert {@link EtapeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Etape> createSpecification(EtapeCriteria criteria) {
        Specification<Etape> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Etape_.id));
            }
            if (criteria.getIdetape() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdetape(), Etape_.idetape));
            }
            if (criteria.getLibelleetape() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleetape(), Etape_.libelleetape));
            }
            if (criteria.getStatutetape() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatutetape(), Etape_.statutetape));
            }
            if (criteria.getHeureexecutionetape() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeureexecutionetape(), Etape_.heureexecutionetape));
            }
            if (criteria.getCodeetape() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeetape(), Etape_.codeetape));
            }
            if (criteria.getIdtfjId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdtfjId(),
                    root -> root.join(Etape_.idtfj, JoinType.LEFT).get(Tfj_.id)));
            }
        }
        return specification;
    }
}
