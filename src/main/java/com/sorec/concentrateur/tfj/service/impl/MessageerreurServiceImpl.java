package com.sorec.concentrateur.tfj.service.impl;

import com.sorec.concentrateur.tfj.service.MessageerreurService;
import com.sorec.concentrateur.tfj.domain.Messageerreur;
import com.sorec.concentrateur.tfj.repository.MessageerreurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Messageerreur}.
 */
@Service
@Transactional
public class MessageerreurServiceImpl implements MessageerreurService {

    private final Logger log = LoggerFactory.getLogger(MessageerreurServiceImpl.class);

    private final MessageerreurRepository messageerreurRepository;

    public MessageerreurServiceImpl(MessageerreurRepository messageerreurRepository) {
        this.messageerreurRepository = messageerreurRepository;
    }

    /**
     * Save a messageerreur.
     *
     * @param messageerreur the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Messageerreur save(Messageerreur messageerreur) {
        log.debug("Request to save Messageerreur : {}", messageerreur);
        return messageerreurRepository.save(messageerreur);
    }

    /**
     * Get all the messageerreurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Messageerreur> findAll(Pageable pageable) {
        log.debug("Request to get all Messageerreurs");
        return messageerreurRepository.findAll(pageable);
    }


    /**
     * Get one messageerreur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Messageerreur> findOne(Long id) {
        log.debug("Request to get Messageerreur : {}", id);
        return messageerreurRepository.findById(id);
    }

    /**
     * Delete the messageerreur by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Messageerreur : {}", id);
        messageerreurRepository.deleteById(id);
    }
}
