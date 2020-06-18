package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.domain.Etape;
import com.sorec.concentrateur.tfj.service.EtapeService;
import com.sorec.concentrateur.tfj.web.rest.errors.BadRequestAlertException;
import com.sorec.concentrateur.tfj.service.dto.EtapeCriteria;
import com.sorec.concentrateur.tfj.service.EtapeQueryService;

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
 * REST controller for managing {@link com.sorec.concentrateur.tfj.domain.Etape}.
 */
@RestController
@RequestMapping("/api")
public class EtapeResource {

    private final Logger log = LoggerFactory.getLogger(EtapeResource.class);

    private static final String ENTITY_NAME = "tfjEtape";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapeService etapeService;

    private final EtapeQueryService etapeQueryService;

    public EtapeResource(EtapeService etapeService, EtapeQueryService etapeQueryService) {
        this.etapeService = etapeService;
        this.etapeQueryService = etapeQueryService;
    }

    /**
     * {@code POST  /etapes} : Create a new etape.
     *
     * @param etape the etape to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etape, or with status {@code 400 (Bad Request)} if the etape has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etapes")
    public ResponseEntity<Etape> createEtape(@Valid @RequestBody Etape etape) throws URISyntaxException {
        log.debug("REST request to save Etape : {}", etape);
        if (etape.getId() != null) {
            throw new BadRequestAlertException("A new etape cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etape result = etapeService.save(etape);
        return ResponseEntity.created(new URI("/api/etapes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etapes} : Updates an existing etape.
     *
     * @param etape the etape to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etape,
     * or with status {@code 400 (Bad Request)} if the etape is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etape couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etapes")
    public ResponseEntity<Etape> updateEtape(@Valid @RequestBody Etape etape) throws URISyntaxException {
        log.debug("REST request to update Etape : {}", etape);
        if (etape.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Etape result = etapeService.save(etape);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etape.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etapes} : get all the etapes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapes in body.
     */
    @GetMapping("/etapes")
    public ResponseEntity<List<Etape>> getAllEtapes(EtapeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Etapes by criteria: {}", criteria);
        Page<Etape> page = etapeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etapes/count} : count all the etapes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/etapes/count")
    public ResponseEntity<Long> countEtapes(EtapeCriteria criteria) {
        log.debug("REST request to count Etapes by criteria: {}", criteria);
        return ResponseEntity.ok().body(etapeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /etapes/:id} : get the "id" etape.
     *
     * @param id the id of the etape to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etape, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etapes/{id}")
    public ResponseEntity<Etape> getEtape(@PathVariable Long id) {
        log.debug("REST request to get Etape : {}", id);
        Optional<Etape> etape = etapeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etape);
    }

    /**
     * {@code DELETE  /etapes/:id} : delete the "id" etape.
     *
     * @param id the id of the etape to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etapes/{id}")
    public ResponseEntity<Void> deleteEtape(@PathVariable Long id) {
        log.debug("REST request to delete Etape : {}", id);
        etapeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
