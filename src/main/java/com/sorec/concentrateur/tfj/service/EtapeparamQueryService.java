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

import com.sorec.concentrateur.tfj.domain.Etapeparam;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.EtapeparamRepository;
import com.sorec.concentrateur.tfj.service.dto.EtapeparamCriteria;

/**
 * Service for executing complex queries for {@link Etapeparam} entities in the database.
 * The main input is a {@link EtapeparamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Etapeparam} or a {@link Page} of {@link Etapeparam} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EtapeparamQueryService extends QueryService<Etapeparam> {

    private final Logger log = LoggerFactory.getLogger(EtapeparamQueryService.class);

    private final EtapeparamRepository etapeparamRepository;

    public EtapeparamQueryService(EtapeparamRepository etapeparamRepository) {
        this.etapeparamRepository = etapeparamRepository;
    }

    /**
     * Return a {@link List} of {@link Etapeparam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Etapeparam> findByCriteria(EtapeparamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Etapeparam> specification = createSpecification(criteria);
        return etapeparamRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Etapeparam} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Etapeparam> findByCriteria(EtapeparamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Etapeparam> specification = createSpecification(criteria);
        return etapeparamRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EtapeparamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Etapeparam> specification = createSpecification(criteria);
        return etapeparamRepository.count(specification);
    }

    /**
     * Function to convert {@link EtapeparamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Etapeparam> createSpecification(EtapeparamCriteria criteria) {
        Specification<Etapeparam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Etapeparam_.id));
            }
            if (criteria.getIdetapeparam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdetapeparam(), Etapeparam_.idetapeparam));
            }
            if (criteria.getLibelleetapeparam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleetapeparam(), Etapeparam_.libelleetapeparam));
            }
            if (criteria.getCodeetapeparam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeetapeparam(), Etapeparam_.codeetapeparam));
            }
        }
        return specification;
    }
}
