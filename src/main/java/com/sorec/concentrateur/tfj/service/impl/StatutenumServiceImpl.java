package com.sorec.concentrateur.tfj.service.impl;

import com.sorec.concentrateur.tfj.service.StatutenumService;
import com.sorec.concentrateur.tfj.domain.Statutenum;
import com.sorec.concentrateur.tfj.repository.StatutenumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Statutenum}.
 */
@Service
@Transactional
public class StatutenumServiceImpl implements StatutenumService {

    private final Logger log = LoggerFactory.getLogger(StatutenumServiceImpl.class);

    private final StatutenumRepository statutenumRepository;

    public StatutenumServiceImpl(StatutenumRepository statutenumRepository) {
        this.statutenumRepository = statutenumRepository;
    }

    /**
     * Save a statutenum.
     *
     * @param statutenum the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Statutenum save(Statutenum statutenum) {
        log.debug("Request to save Statutenum : {}", statutenum);
        return statutenumRepository.save(statutenum);
    }

    /**
     * Get all the statutenums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Statutenum> findAll(Pageable pageable) {
        log.debug("Request to get all Statutenums");
        return statutenumRepository.findAll(pageable);
    }


    /**
     * Get one statutenum by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Statutenum> findOne(Long id) {
        log.debug("Request to get Statutenum : {}", id);
        return statutenumRepository.findById(id);
    }

    /**
     * Delete the statutenum by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Statutenum : {}", id);
        statutenumRepository.deleteById(id);
    }
}
