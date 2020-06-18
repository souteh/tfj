package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Etapeparam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Etapeparam}.
 */
public interface EtapeparamService {

    /**
     * Save a etapeparam.
     *
     * @param etapeparam the entity to save.
     * @return the persisted entity.
     */
    Etapeparam save(Etapeparam etapeparam);

    /**
     * Get all the etapeparams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etapeparam> findAll(Pageable pageable);


    /**
     * Get the "id" etapeparam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etapeparam> findOne(Long id);

    /**
     * Delete the "id" etapeparam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
