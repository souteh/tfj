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

import com.sorec.concentrateur.tfj.domain.Tfj;
import com.sorec.concentrateur.tfj.domain.*; // for static metamodels
import com.sorec.concentrateur.tfj.repository.TfjRepository;
import com.sorec.concentrateur.tfj.service.dto.TfjCriteria;

/**
 * Service for executing complex queries for {@link Tfj} entities in the database.
 * The main input is a {@link TfjCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tfj} or a {@link Page} of {@link Tfj} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TfjQueryService extends QueryService<Tfj> {

    private final Logger log = LoggerFactory.getLogger(TfjQueryService.class);

    private final TfjRepository tfjRepository;

    public TfjQueryService(TfjRepository tfjRepository) {
        this.tfjRepository = tfjRepository;
    }

    /**
     * Return a {@link List} of {@link Tfj} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tfj> findByCriteria(TfjCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tfj> specification = createSpecification(criteria);
        return tfjRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tfj} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tfj> findByCriteria(TfjCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tfj> specification = createSpecification(criteria);
        return tfjRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TfjCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tfj> specification = createSpecification(criteria);
        return tfjRepository.count(specification);
    }

    /**
     * Function to convert {@link TfjCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tfj> createSpecification(TfjCriteria criteria) {
        Specification<Tfj> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tfj_.id));
            }
            if (criteria.getIdtfj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdtfj(), Tfj_.idtfj));
            }
            if (criteria.getDatetfj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatetfj(), Tfj_.datetfj));
            }
            if (criteria.getStatuttfj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatuttfj(), Tfj_.statuttfj));
            }
        }
        return specification;
    }
}
