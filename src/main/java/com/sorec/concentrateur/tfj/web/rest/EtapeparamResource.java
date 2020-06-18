package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.domain.Etapeparam;
import com.sorec.concentrateur.tfj.service.EtapeparamService;
import com.sorec.concentrateur.tfj.web.rest.errors.BadRequestAlertException;
import com.sorec.concentrateur.tfj.service.dto.EtapeparamCriteria;
import com.sorec.concentrateur.tfj.service.EtapeparamQueryService;

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
 * REST controller for managing {@link com.sorec.concentrateur.tfj.domain.Etapeparam}.
 */
@RestController
@RequestMapping("/api")
public class EtapeparamResource {

    private final Logger log = LoggerFactory.getLogger(EtapeparamResource.class);

    private static final String ENTITY_NAME = "tfjEtapeparam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapeparamService etapeparamService;

    private final EtapeparamQueryService etapeparamQueryService;

    public EtapeparamResource(EtapeparamService etapeparamService, EtapeparamQueryService etapeparamQueryService) {
        this.etapeparamService = etapeparamService;
        this.etapeparamQueryService = etapeparamQueryService;
    }

    /**
     * {@code POST  /etapeparams} : Create a new etapeparam.
     *
     * @param etapeparam the etapeparam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapeparam, or with status {@code 400 (Bad Request)} if the etapeparam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etapeparams")
    public ResponseEntity<Etapeparam> createEtapeparam(@Valid @RequestBody Etapeparam etapeparam) throws URISyntaxException {
        log.debug("REST request to save Etapeparam : {}", etapeparam);
        if (etapeparam.getId() != null) {
            throw new BadRequestAlertException("A new etapeparam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etapeparam result = etapeparamService.save(etapeparam);
        return ResponseEntity.created(new URI("/api/etapeparams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etapeparams} : Updates an existing etapeparam.
     *
     * @param etapeparam the etapeparam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeparam,
     * or with status {@code 400 (Bad Request)} if the etapeparam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapeparam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etapeparams")
    public ResponseEntity<Etapeparam> updateEtapeparam(@Valid @RequestBody Etapeparam etapeparam) throws URISyntaxException {
        log.debug("REST request to update Etapeparam : {}", etapeparam);
        if (etapeparam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Etapeparam result = etapeparamService.save(etapeparam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeparam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etapeparams} : get all the etapeparams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapeparams in body.
     */
    @GetMapping("/etapeparams")
    public ResponseEntity<List<Etapeparam>> getAllEtapeparams(EtapeparamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Etapeparams by criteria: {}", criteria);
        Page<Etapeparam> page = etapeparamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etapeparams/count} : count all the etapeparams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/etapeparams/count")
    public ResponseEntity<Long> countEtapeparams(EtapeparamCriteria criteria) {
        log.debug("REST request to count Etapeparams by criteria: {}", criteria);
        return ResponseEntity.ok().body(etapeparamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /etapeparams/:id} : get the "id" etapeparam.
     *
     * @param id the id of the etapeparam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapeparam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etapeparams/{id}")
    public ResponseEntity<Etapeparam> getEtapeparam(@PathVariable Long id) {
        log.debug("REST request to get Etapeparam : {}", id);
        Optional<Etapeparam> etapeparam = etapeparamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapeparam);
    }

    /**
     * {@code DELETE  /etapeparams/:id} : delete the "id" etapeparam.
     *
     * @param id the id of the etapeparam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etapeparams/{id}")
    public ResponseEntity<Void> deleteEtapeparam(@PathVariable Long id) {
        log.debug("REST request to delete Etapeparam : {}", id);
        etapeparamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
