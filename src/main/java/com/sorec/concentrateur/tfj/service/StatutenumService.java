package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Statutenum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Statutenum}.
 */
public interface StatutenumService {

    /**
     * Save a statutenum.
     *
     * @param statutenum the entity to save.
     * @return the persisted entity.
     */
    Statutenum save(Statutenum statutenum);

    /**
     * Get all the statutenums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Statutenum> findAll(Pageable pageable);


    /**
     * Get the "id" statutenum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Statutenum> findOne(Long id);

    /**
     * Delete the "id" statutenum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
