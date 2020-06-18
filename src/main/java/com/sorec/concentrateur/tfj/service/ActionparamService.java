package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Actionparam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Actionparam}.
 */
public interface ActionparamService {

    /**
     * Save a actionparam.
     *
     * @param actionparam the entity to save.
     * @return the persisted entity.
     */
    Actionparam save(Actionparam actionparam);

    /**
     * Get all the actionparams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Actionparam> findAll(Pageable pageable);


    /**
     * Get the "id" actionparam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Actionparam> findOne(Long id);

    /**
     * Delete the "id" actionparam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
