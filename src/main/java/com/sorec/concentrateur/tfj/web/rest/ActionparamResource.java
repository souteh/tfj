package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.domain.Actionparam;
import com.sorec.concentrateur.tfj.service.ActionparamService;
import com.sorec.concentrateur.tfj.web.rest.errors.BadRequestAlertException;
import com.sorec.concentrateur.tfj.service.dto.ActionparamCriteria;
import com.sorec.concentrateur.tfj.service.ActionparamQueryService;

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
 * REST controller for managing {@link com.sorec.concentrateur.tfj.domain.Actionparam}.
 */
@RestController
@RequestMapping("/api")
public class ActionparamResource {

    private final Logger log = LoggerFactory.getLogger(ActionparamResource.class);

    private static final String ENTITY_NAME = "tfjActionparam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionparamService actionparamService;

    private final ActionparamQueryService actionparamQueryService;

    public ActionparamResource(ActionparamService actionparamService, ActionparamQueryService actionparamQueryService) {
        this.actionparamService = actionparamService;
        this.actionparamQueryService = actionparamQueryService;
    }

    /**
     * {@code POST  /actionparams} : Create a new actionparam.
     *
     * @param actionparam the actionparam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionparam, or with status {@code 400 (Bad Request)} if the actionparam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actionparams")
    public ResponseEntity<Actionparam> createActionparam(@Valid @RequestBody Actionparam actionparam) throws URISyntaxException {
        log.debug("REST request to save Actionparam : {}", actionparam);
        if (actionparam.getId() != null) {
            throw new BadRequestAlertException("A new actionparam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actionparam result = actionparamService.save(actionparam);
        return ResponseEntity.created(new URI("/api/actionparams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actionparams} : Updates an existing actionparam.
     *
     * @param actionparam the actionparam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionparam,
     * or with status {@code 400 (Bad Request)} if the actionparam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionparam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actionparams")
    public ResponseEntity<Actionparam> updateActionparam(@Valid @RequestBody Actionparam actionparam) throws URISyntaxException {
        log.debug("REST request to update Actionparam : {}", actionparam);
        if (actionparam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Actionparam result = actionparamService.save(actionparam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionparam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /actionparams} : get all the actionparams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionparams in body.
     */
    @GetMapping("/actionparams")
    public ResponseEntity<List<Actionparam>> getAllActionparams(ActionparamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Actionparams by criteria: {}", criteria);
        Page<Actionparam> page = actionparamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /actionparams/count} : count all the actionparams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/actionparams/count")
    public ResponseEntity<Long> countActionparams(ActionparamCriteria criteria) {
        log.debug("REST request to count Actionparams by criteria: {}", criteria);
        return ResponseEntity.ok().body(actionparamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /actionparams/:id} : get the "id" actionparam.
     *
     * @param id the id of the actionparam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionparam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actionparams/{id}")
    public ResponseEntity<Actionparam> getActionparam(@PathVariable Long id) {
        log.debug("REST request to get Actionparam : {}", id);
        Optional<Actionparam> actionparam = actionparamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionparam);
    }

    /**
     * {@code DELETE  /actionparams/:id} : delete the "id" actionparam.
     *
     * @param id the id of the actionparam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actionparams/{id}")
    public ResponseEntity<Void> deleteActionparam(@PathVariable Long id) {
        log.debug("REST request to delete Actionparam : {}", id);
        actionparamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
