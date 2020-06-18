package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Messageerreur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Messageerreur}.
 */
public interface MessageerreurService {

    /**
     * Save a messageerreur.
     *
     * @param messageerreur the entity to save.
     * @return the persisted entity.
     */
    Messageerreur save(Messageerreur messageerreur);

    /**
     * Get all the messageerreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Messageerreur> findAll(Pageable pageable);


    /**
     * Get the "id" messageerreur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Messageerreur> findOne(Long id);

    /**
     * Delete the "id" messageerreur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
