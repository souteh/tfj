package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Tfj;
import com.sorec.concentrateur.tfj.repository.TfjRepository;
import com.sorec.concentrateur.tfj.service.TfjService;
import com.sorec.concentrateur.tfj.service.dto.TfjCriteria;
import com.sorec.concentrateur.tfj.service.TfjQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TfjResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TfjResourceIT {

    private static final Long DEFAULT_IDTFJ = 1L;
    private static final Long UPDATED_IDTFJ = 2L;
    private static final Long SMALLER_IDTFJ = 1L - 1L;

    private static final Instant DEFAULT_DATETFJ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATETFJ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUTTFJ = "AAAAAAAAAA";
    private static final String UPDATED_STATUTTFJ = "BBBBBBBBBB";

    @Autowired
    private TfjRepository tfjRepository;

    @Autowired
    private TfjService tfjService;

    @Autowired
    private TfjQueryService tfjQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTfjMockMvc;

    private Tfj tfj;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tfj createEntity(EntityManager em) {
        Tfj tfj = new Tfj()
            .idtfj(DEFAULT_IDTFJ)
            .datetfj(DEFAULT_DATETFJ)
            .statuttfj(DEFAULT_STATUTTFJ);
        return tfj;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tfj createUpdatedEntity(EntityManager em) {
        Tfj tfj = new Tfj()
            .idtfj(UPDATED_IDTFJ)
            .datetfj(UPDATED_DATETFJ)
            .statuttfj(UPDATED_STATUTTFJ);
        return tfj;
    }

    @BeforeEach
    public void initTest() {
        tfj = createEntity(em);
    }

    @Test
    @Transactional
    public void createTfj() throws Exception {
        int databaseSizeBeforeCreate = tfjRepository.findAll().size();
        // Create the Tfj
        restTfjMockMvc.perform(post("/api/tfjs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tfj)))
            .andExpect(status().isCreated());

        // Validate the Tfj in the database
        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeCreate + 1);
        Tfj testTfj = tfjList.get(tfjList.size() - 1);
        assertThat(testTfj.getIdtfj()).isEqualTo(DEFAULT_IDTFJ);
        assertThat(testTfj.getDatetfj()).isEqualTo(DEFAULT_DATETFJ);
        assertThat(testTfj.getStatuttfj()).isEqualTo(DEFAULT_STATUTTFJ);
    }

    @Test
    @Transactional
    public void createTfjWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tfjRepository.findAll().size();

        // Create the Tfj with an existing ID
        tfj.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTfjMockMvc.perform(post("/api/tfjs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tfj)))
            .andExpect(status().isBadRequest());

        // Validate the Tfj in the database
        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdtfjIsRequired() throws Exception {
        int databaseSizeBeforeTest = tfjRepository.findAll().size();
        // set the field null
        tfj.setIdtfj(null);

        // Create the Tfj, which fails.


        restTfjMockMvc.perform(post("/api/tfjs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tfj)))
            .andExpect(status().isBadRequest());

        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTfjs() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList
        restTfjMockMvc.perform(get("/api/tfjs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tfj.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtfj").value(hasItem(DEFAULT_IDTFJ.intValue())))
            .andExpect(jsonPath("$.[*].datetfj").value(hasItem(DEFAULT_DATETFJ.toString())))
            .andExpect(jsonPath("$.[*].statuttfj").value(hasItem(DEFAULT_STATUTTFJ)));
    }
    
    @Test
    @Transactional
    public void getTfj() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get the tfj
        restTfjMockMvc.perform(get("/api/tfjs/{id}", tfj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tfj.getId().intValue()))
            .andExpect(jsonPath("$.idtfj").value(DEFAULT_IDTFJ.intValue()))
            .andExpect(jsonPath("$.datetfj").value(DEFAULT_DATETFJ.toString()))
            .andExpect(jsonPath("$.statuttfj").value(DEFAULT_STATUTTFJ));
    }


    @Test
    @Transactional
    public void getTfjsByIdFiltering() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        Long id = tfj.getId();

        defaultTfjShouldBeFound("id.equals=" + id);
        defaultTfjShouldNotBeFound("id.notEquals=" + id);

        defaultTfjShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTfjShouldNotBeFound("id.greaterThan=" + id);

        defaultTfjShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTfjShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj equals to DEFAULT_IDTFJ
        defaultTfjShouldBeFound("idtfj.equals=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj equals to UPDATED_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.equals=" + UPDATED_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj not equals to DEFAULT_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.notEquals=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj not equals to UPDATED_IDTFJ
        defaultTfjShouldBeFound("idtfj.notEquals=" + UPDATED_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsInShouldWork() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj in DEFAULT_IDTFJ or UPDATED_IDTFJ
        defaultTfjShouldBeFound("idtfj.in=" + DEFAULT_IDTFJ + "," + UPDATED_IDTFJ);

        // Get all the tfjList where idtfj equals to UPDATED_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.in=" + UPDATED_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsNullOrNotNull() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj is not null
        defaultTfjShouldBeFound("idtfj.specified=true");

        // Get all the tfjList where idtfj is null
        defaultTfjShouldNotBeFound("idtfj.specified=false");
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj is greater than or equal to DEFAULT_IDTFJ
        defaultTfjShouldBeFound("idtfj.greaterThanOrEqual=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj is greater than or equal to UPDATED_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.greaterThanOrEqual=" + UPDATED_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj is less than or equal to DEFAULT_IDTFJ
        defaultTfjShouldBeFound("idtfj.lessThanOrEqual=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj is less than or equal to SMALLER_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.lessThanOrEqual=" + SMALLER_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsLessThanSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj is less than DEFAULT_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.lessThan=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj is less than UPDATED_IDTFJ
        defaultTfjShouldBeFound("idtfj.lessThan=" + UPDATED_IDTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByIdtfjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where idtfj is greater than DEFAULT_IDTFJ
        defaultTfjShouldNotBeFound("idtfj.greaterThan=" + DEFAULT_IDTFJ);

        // Get all the tfjList where idtfj is greater than SMALLER_IDTFJ
        defaultTfjShouldBeFound("idtfj.greaterThan=" + SMALLER_IDTFJ);
    }


    @Test
    @Transactional
    public void getAllTfjsByDatetfjIsEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where datetfj equals to DEFAULT_DATETFJ
        defaultTfjShouldBeFound("datetfj.equals=" + DEFAULT_DATETFJ);

        // Get all the tfjList where datetfj equals to UPDATED_DATETFJ
        defaultTfjShouldNotBeFound("datetfj.equals=" + UPDATED_DATETFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByDatetfjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where datetfj not equals to DEFAULT_DATETFJ
        defaultTfjShouldNotBeFound("datetfj.notEquals=" + DEFAULT_DATETFJ);

        // Get all the tfjList where datetfj not equals to UPDATED_DATETFJ
        defaultTfjShouldBeFound("datetfj.notEquals=" + UPDATED_DATETFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByDatetfjIsInShouldWork() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where datetfj in DEFAULT_DATETFJ or UPDATED_DATETFJ
        defaultTfjShouldBeFound("datetfj.in=" + DEFAULT_DATETFJ + "," + UPDATED_DATETFJ);

        // Get all the tfjList where datetfj equals to UPDATED_DATETFJ
        defaultTfjShouldNotBeFound("datetfj.in=" + UPDATED_DATETFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByDatetfjIsNullOrNotNull() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where datetfj is not null
        defaultTfjShouldBeFound("datetfj.specified=true");

        // Get all the tfjList where datetfj is null
        defaultTfjShouldNotBeFound("datetfj.specified=false");
    }

    @Test
    @Transactional
    public void getAllTfjsByStatuttfjIsEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj equals to DEFAULT_STATUTTFJ
        defaultTfjShouldBeFound("statuttfj.equals=" + DEFAULT_STATUTTFJ);

        // Get all the tfjList where statuttfj equals to UPDATED_STATUTTFJ
        defaultTfjShouldNotBeFound("statuttfj.equals=" + UPDATED_STATUTTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByStatuttfjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj not equals to DEFAULT_STATUTTFJ
        defaultTfjShouldNotBeFound("statuttfj.notEquals=" + DEFAULT_STATUTTFJ);

        // Get all the tfjList where statuttfj not equals to UPDATED_STATUTTFJ
        defaultTfjShouldBeFound("statuttfj.notEquals=" + UPDATED_STATUTTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByStatuttfjIsInShouldWork() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj in DEFAULT_STATUTTFJ or UPDATED_STATUTTFJ
        defaultTfjShouldBeFound("statuttfj.in=" + DEFAULT_STATUTTFJ + "," + UPDATED_STATUTTFJ);

        // Get all the tfjList where statuttfj equals to UPDATED_STATUTTFJ
        defaultTfjShouldNotBeFound("statuttfj.in=" + UPDATED_STATUTTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByStatuttfjIsNullOrNotNull() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj is not null
        defaultTfjShouldBeFound("statuttfj.specified=true");

        // Get all the tfjList where statuttfj is null
        defaultTfjShouldNotBeFound("statuttfj.specified=false");
    }
                @Test
    @Transactional
    public void getAllTfjsByStatuttfjContainsSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj contains DEFAULT_STATUTTFJ
        defaultTfjShouldBeFound("statuttfj.contains=" + DEFAULT_STATUTTFJ);

        // Get all the tfjList where statuttfj contains UPDATED_STATUTTFJ
        defaultTfjShouldNotBeFound("statuttfj.contains=" + UPDATED_STATUTTFJ);
    }

    @Test
    @Transactional
    public void getAllTfjsByStatuttfjNotContainsSomething() throws Exception {
        // Initialize the database
        tfjRepository.saveAndFlush(tfj);

        // Get all the tfjList where statuttfj does not contain DEFAULT_STATUTTFJ
        defaultTfjShouldNotBeFound("statuttfj.doesNotContain=" + DEFAULT_STATUTTFJ);

        // Get all the tfjList where statuttfj does not contain UPDATED_STATUTTFJ
        defaultTfjShouldBeFound("statuttfj.doesNotContain=" + UPDATED_STATUTTFJ);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTfjShouldBeFound(String filter) throws Exception {
        restTfjMockMvc.perform(get("/api/tfjs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tfj.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtfj").value(hasItem(DEFAULT_IDTFJ.intValue())))
            .andExpect(jsonPath("$.[*].datetfj").value(hasItem(DEFAULT_DATETFJ.toString())))
            .andExpect(jsonPath("$.[*].statuttfj").value(hasItem(DEFAULT_STATUTTFJ)));

        // Check, that the count call also returns 1
        restTfjMockMvc.perform(get("/api/tfjs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTfjShouldNotBeFound(String filter) throws Exception {
        restTfjMockMvc.perform(get("/api/tfjs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTfjMockMvc.perform(get("/api/tfjs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTfj() throws Exception {
        // Get the tfj
        restTfjMockMvc.perform(get("/api/tfjs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTfj() throws Exception {
        // Initialize the database
        tfjService.save(tfj);

        int databaseSizeBeforeUpdate = tfjRepository.findAll().size();

        // Update the tfj
        Tfj updatedTfj = tfjRepository.findById(tfj.getId()).get();
        // Disconnect from session so that the updates on updatedTfj are not directly saved in db
        em.detach(updatedTfj);
        updatedTfj
            .idtfj(UPDATED_IDTFJ)
            .datetfj(UPDATED_DATETFJ)
            .statuttfj(UPDATED_STATUTTFJ);

        restTfjMockMvc.perform(put("/api/tfjs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTfj)))
            .andExpect(status().isOk());

        // Validate the Tfj in the database
        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeUpdate);
        Tfj testTfj = tfjList.get(tfjList.size() - 1);
        assertThat(testTfj.getIdtfj()).isEqualTo(UPDATED_IDTFJ);
        assertThat(testTfj.getDatetfj()).isEqualTo(UPDATED_DATETFJ);
        assertThat(testTfj.getStatuttfj()).isEqualTo(UPDATED_STATUTTFJ);
    }

    @Test
    @Transactional
    public void updateNonExistingTfj() throws Exception {
        int databaseSizeBeforeUpdate = tfjRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTfjMockMvc.perform(put("/api/tfjs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tfj)))
            .andExpect(status().isBadRequest());

        // Validate the Tfj in the database
        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTfj() throws Exception {
        // Initialize the database
        tfjService.save(tfj);

        int databaseSizeBeforeDelete = tfjRepository.findAll().size();

        // Delete the tfj
        restTfjMockMvc.perform(delete("/api/tfjs/{id}", tfj.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tfj> tfjList = tfjRepository.findAll();
        assertThat(tfjList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
