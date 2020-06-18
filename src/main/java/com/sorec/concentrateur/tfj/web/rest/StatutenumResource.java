package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.domain.Statutenum;
import com.sorec.concentrateur.tfj.service.StatutenumService;
import com.sorec.concentrateur.tfj.web.rest.errors.BadRequestAlertException;
import com.sorec.concentrateur.tfj.service.dto.StatutenumCriteria;
import com.sorec.concentrateur.tfj.service.StatutenumQueryService;

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
 * REST controller for managing {@link com.sorec.concentrateur.tfj.domain.Statutenum}.
 */
@RestController
@RequestMapping("/api")
public class StatutenumResource {

    private final Logger log = LoggerFactory.getLogger(StatutenumResource.class);

    private static final String ENTITY_NAME = "tfjStatutenum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatutenumService statutenumService;

    private final StatutenumQueryService statutenumQueryService;

    public StatutenumResource(StatutenumService statutenumService, StatutenumQueryService statutenumQueryService) {
        this.statutenumService = statutenumService;
        this.statutenumQueryService = statutenumQueryService;
    }

    /**
     * {@code POST  /statutenums} : Create a new statutenum.
     *
     * @param statutenum the statutenum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statutenum, or with status {@code 400 (Bad Request)} if the statutenum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statutenums")
    public ResponseEntity<Statutenum> createStatutenum(@Valid @RequestBody Statutenum statutenum) throws URISyntaxException {
        log.debug("REST request to save Statutenum : {}", statutenum);
        if (statutenum.getId() != null) {
            throw new BadRequestAlertException("A new statutenum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Statutenum result = statutenumService.save(statutenum);
        return ResponseEntity.created(new URI("/api/statutenums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statutenums} : Updates an existing statutenum.
     *
     * @param statutenum the statutenum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statutenum,
     * or with status {@code 400 (Bad Request)} if the statutenum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statutenum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statutenums")
    public ResponseEntity<Statutenum> updateStatutenum(@Valid @RequestBody Statutenum statutenum) throws URISyntaxException {
        log.debug("REST request to update Statutenum : {}", statutenum);
        if (statutenum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Statutenum result = statutenumService.save(statutenum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statutenum.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /statutenums} : get all the statutenums.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statutenums in body.
     */
    @GetMapping("/statutenums")
    public ResponseEntity<List<Statutenum>> getAllStatutenums(StatutenumCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Statutenums by criteria: {}", criteria);
        Page<Statutenum> page = statutenumQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statutenums/count} : count all the statutenums.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/statutenums/count")
    public ResponseEntity<Long> countStatutenums(StatutenumCriteria criteria) {
        log.debug("REST request to count Statutenums by criteria: {}", criteria);
        return ResponseEntity.ok().body(statutenumQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statutenums/:id} : get the "id" statutenum.
     *
     * @param id the id of the statutenum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statutenum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statutenums/{id}")
    public ResponseEntity<Statutenum> getStatutenum(@PathVariable Long id) {
        log.debug("REST request to get Statutenum : {}", id);
        Optional<Statutenum> statutenum = statutenumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statutenum);
    }

    /**
     * {@code DELETE  /statutenums/:id} : delete the "id" statutenum.
     *
     * @param id the id of the statutenum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statutenums/{id}")
    public ResponseEntity<Void> deleteStatutenum(@PathVariable Long id) {
        log.debug("REST request to delete Statutenum : {}", id);
        statutenumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
