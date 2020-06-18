package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.domain.Messageerreur;
import com.sorec.concentrateur.tfj.service.MessageerreurService;
import com.sorec.concentrateur.tfj.web.rest.errors.BadRequestAlertException;
import com.sorec.concentrateur.tfj.service.dto.MessageerreurCriteria;
import com.sorec.concentrateur.tfj.service.MessageerreurQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sorec.concentrateur.tfj.domain.Messageerreur}.
 */
@RestController
@RequestMapping("/api")
public class MessageerreurResource {

    private final Logger log = LoggerFactory.getLogger(MessageerreurResource.class);

    private static final String ENTITY_NAME = "tfjMessageerreur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageerreurService messageerreurService;

    private final MessageerreurQueryService messageerreurQueryService;

    public MessageerreurResource(MessageerreurService messageerreurService, MessageerreurQueryService messageerreurQueryService) {
        this.messageerreurService = messageerreurService;
        this.messageerreurQueryService = messageerreurQueryService;
    }

    /**
     * {@code POST  /messageerreurs} : Create a new messageerreur.
     *
     * @param messageerreur the messageerreur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageerreur, or with status {@code 400 (Bad Request)} if the messageerreur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/messageerreurs")
    public ResponseEntity<Messageerreur> createMessageerreur(@Valid @RequestBody Messageerreur messageerreur) throws URISyntaxException {
        log.debug("REST request to save Messageerreur : {}", messageerreur);
        if (messageerreur.getId() != null) {
            throw new BadRequestAlertException("A new messageerreur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Messageerreur result = messageerreurService.save(messageerreur);
        return ResponseEntity.created(new URI("/api/messageerreurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /messageerreurs} : Updates an existing messageerreur.
     *
     * @param messageerreur the messageerreur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageerreur,
     * or with status {@code 400 (Bad Request)} if the messageerreur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageerreur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/messageerreurs")
    public ResponseEntity<Messageerreur> updateMessageerreur(@Valid @RequestBody Messageerreur messageerreur) throws URISyntaxException {
        log.debug("REST request to update Messageerreur : {}", messageerreur);
        if (messageerreur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Messageerreur result = messageerreurService.save(messageerreur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageerreur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /messageerreurs} : get all the messageerreurs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageerreurs in body.
     */
    @GetMapping("/messageerreurs")
    public ResponseEntity<List<Messageerreur>> getAllMessageerreurs(MessageerreurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Messageerreurs by criteria: {}", criteria);
        Page<Messageerreur> page = messageerreurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /messageerreurs/count} : count all the messageerreurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/messageerreurs/count")
    public ResponseEntity<Long> countMessageerreurs(MessageerreurCriteria criteria) {
        log.debug("REST request to count Messageerreurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageerreurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /messageerreurs/:id} : get the "id" messageerreur.
     *
     * @param id the id of the messageerreur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageerreur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/messageerreurs/{id}")
    public ResponseEntity<Messageerreur> getMessageerreur(@PathVariable Long id) {
        log.debug("REST request to get Messageerreur : {}", id);
        Optional<Messageerreur> messageerreur = messageerreurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageerreur);
    }

    /**
     * {@code DELETE  /messageerreurs/:id} : delete the "id" messageerreur.
     *
     * @param id the id of the messageerreur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/messageerreurs/{id}")
    public ResponseEntity<Void> deleteMessageerreur(@PathVariable Long id) {
        log.debug("REST request to delete Messageerreur : {}", id);
        messageerreurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
