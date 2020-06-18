package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Tfj;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Tfj}.
 */
public interface TfjService {

    /**
     * Save a tfj.
     *
     * @param tfj the entity to save.
     * @return the persisted entity.
     */
    Tfj save(Tfj tfj);

    /**
     * Get all the tfjs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tfj> findAll(Pageable pageable);


    /**
     * Get the "id" tfj.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tfj> findOne(Long id);

    /**
     * Delete the "id" tfj.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
