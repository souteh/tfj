package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Action;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Action}.
 */
public interface ActionService {

    /**
     * Save a action.
     *
     * @param action the entity to save.
     * @return the persisted entity.
     */
    Action save(Action action);

    /**
     * Get all the actions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Action> findAll(Pageable pageable);


    /**
     * Get the "id" action.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Action> findOne(Long id);

    /**
     * Delete the "id" action.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
