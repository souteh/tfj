package com.sorec.concentrateur.tfj.service.impl;

import com.sorec.concentrateur.tfj.service.EtapeparamService;
import com.sorec.concentrateur.tfj.domain.Etapeparam;
import com.sorec.concentrateur.tfj.repository.EtapeparamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etapeparam}.
 */
@Service
@Transactional
public class EtapeparamServiceImpl implements EtapeparamService {

    private final Logger log = LoggerFactory.getLogger(EtapeparamServiceImpl.class);

    private final EtapeparamRepository etapeparamRepository;

    public EtapeparamServiceImpl(EtapeparamRepository etapeparamRepository) {
        this.etapeparamRepository = etapeparamRepository;
    }

    /**
     * Save a etapeparam.
     *
     * @param etapeparam the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Etapeparam save(Etapeparam etapeparam) {
        log.debug("Request to save Etapeparam : {}", etapeparam);
        return etapeparamRepository.save(etapeparam);
    }

    /**
     * Get all the etapeparams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Etapeparam> findAll(Pageable pageable) {
        log.debug("Request to get all Etapeparams");
        return etapeparamRepository.findAll(pageable);
    }


    /**
     * Get one etapeparam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Etapeparam> findOne(Long id) {
        log.debug("Request to get Etapeparam : {}", id);
        return etapeparamRepository.findById(id);
    }

    /**
     * Delete the etapeparam by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etapeparam : {}", id);
        etapeparamRepository.deleteById(id);
    }
}
