package com.sorec.concentrateur.tfj.service;

import com.sorec.concentrateur.tfj.domain.Etape;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Etape}.
 */
public interface EtapeService {

    /**
     * Save a etape.
     *
     * @param etape the entity to save.
     * @return the persisted entity.
     */
    Etape save(Etape etape);

    /**
     * Get all the etapes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etape> findAll(Pageable pageable);


    /**
     * Get the "id" etape.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etape> findOne(Long id);

    /**
     * Delete the "id" etape.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
