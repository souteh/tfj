package com.sorec.concentrateur.tfj.service.impl;

import com.sorec.concentrateur.tfj.service.EtapeService;
import com.sorec.concentrateur.tfj.domain.Etape;
import com.sorec.concentrateur.tfj.repository.EtapeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etape}.
 */
@Service
@Transactional
public class EtapeServiceImpl implements EtapeService {

    private final Logger log = LoggerFactory.getLogger(EtapeServiceImpl.class);

    private final EtapeRepository etapeRepository;

    public EtapeServiceImpl(EtapeRepository etapeRepository) {
        this.etapeRepository = etapeRepository;
    }

    /**
     * Save a etape.
     *
     * @param etape the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Etape save(Etape etape) {
        log.debug("Request to save Etape : {}", etape);
        return etapeRepository.save(etape);
    }

    /**
     * Get all the etapes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Etape> findAll(Pageable pageable) {
        log.debug("Request to get all Etapes");
        return etapeRepository.findAll(pageable);
    }


    /**
     * Get one etape by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Etape> findOne(Long id) {
        log.debug("Request to get Etape : {}", id);
        return etapeRepository.findById(id);
    }

    /**
     * Delete the etape by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etape : {}", id);
        etapeRepository.deleteById(id);
    }
}
