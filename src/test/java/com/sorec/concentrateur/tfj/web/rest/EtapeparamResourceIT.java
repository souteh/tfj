package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Etapeparam;
import com.sorec.concentrateur.tfj.repository.EtapeparamRepository;
import com.sorec.concentrateur.tfj.service.EtapeparamService;
import com.sorec.concentrateur.tfj.service.dto.EtapeparamCriteria;
import com.sorec.concentrateur.tfj.service.EtapeparamQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EtapeparamResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtapeparamResourceIT {

    private static final Long DEFAULT_IDETAPEPARAM = 1L;
    private static final Long UPDATED_IDETAPEPARAM = 2L;
    private static final Long SMALLER_IDETAPEPARAM = 1L - 1L;

    private static final String DEFAULT_LIBELLEETAPEPARAM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLEETAPEPARAM = "BBBBBBBBBB";

    private static final String DEFAULT_CODEETAPEPARAM = "AAAAAAAAAA";
    private static final String UPDATED_CODEETAPEPARAM = "BBBBBBBBBB";

    @Autowired
    private EtapeparamRepository etapeparamRepository;

    @Autowired
    private EtapeparamService etapeparamService;

    @Autowired
    private EtapeparamQueryService etapeparamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapeparamMockMvc;

    private Etapeparam etapeparam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etapeparam createEntity(EntityManager em) {
        Etapeparam etapeparam = new Etapeparam()
            .idetapeparam(DEFAULT_IDETAPEPARAM)
            .libelleetapeparam(DEFAULT_LIBELLEETAPEPARAM)
            .codeetapeparam(DEFAULT_CODEETAPEPARAM);
        return etapeparam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etapeparam createUpdatedEntity(EntityManager em) {
        Etapeparam etapeparam = new Etapeparam()
            .idetapeparam(UPDATED_IDETAPEPARAM)
            .libelleetapeparam(UPDATED_LIBELLEETAPEPARAM)
            .codeetapeparam(UPDATED_CODEETAPEPARAM);
        return etapeparam;
    }

    @BeforeEach
    public void initTest() {
        etapeparam = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapeparam() throws Exception {
        int databaseSizeBeforeCreate = etapeparamRepository.findAll().size();
        // Create the Etapeparam
        restEtapeparamMockMvc.perform(post("/api/etapeparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etapeparam)))
            .andExpect(status().isCreated());

        // Validate the Etapeparam in the database
        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeCreate + 1);
        Etapeparam testEtapeparam = etapeparamList.get(etapeparamList.size() - 1);
        assertThat(testEtapeparam.getIdetapeparam()).isEqualTo(DEFAULT_IDETAPEPARAM);
        assertThat(testEtapeparam.getLibelleetapeparam()).isEqualTo(DEFAULT_LIBELLEETAPEPARAM);
        assertThat(testEtapeparam.getCodeetapeparam()).isEqualTo(DEFAULT_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void createEtapeparamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapeparamRepository.findAll().size();

        // Create the Etapeparam with an existing ID
        etapeparam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapeparamMockMvc.perform(post("/api/etapeparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etapeparam)))
            .andExpect(status().isBadRequest());

        // Validate the Etapeparam in the database
        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdetapeparamIsRequired() throws Exception {
        int databaseSizeBeforeTest = etapeparamRepository.findAll().size();
        // set the field null
        etapeparam.setIdetapeparam(null);

        // Create the Etapeparam, which fails.


        restEtapeparamMockMvc.perform(post("/api/etapeparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etapeparam)))
            .andExpect(status().isBadRequest());

        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtapeparams() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList
        restEtapeparamMockMvc.perform(get("/api/etapeparams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapeparam.getId().intValue())))
            .andExpect(jsonPath("$.[*].idetapeparam").value(hasItem(DEFAULT_IDETAPEPARAM.intValue())))
            .andExpect(jsonPath("$.[*].libelleetapeparam").value(hasItem(DEFAULT_LIBELLEETAPEPARAM)))
            .andExpect(jsonPath("$.[*].codeetapeparam").value(hasItem(DEFAULT_CODEETAPEPARAM)));
    }
    
    @Test
    @Transactional
    public void getEtapeparam() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get the etapeparam
        restEtapeparamMockMvc.perform(get("/api/etapeparams/{id}", etapeparam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etapeparam.getId().intValue()))
            .andExpect(jsonPath("$.idetapeparam").value(DEFAULT_IDETAPEPARAM.intValue()))
            .andExpect(jsonPath("$.libelleetapeparam").value(DEFAULT_LIBELLEETAPEPARAM))
            .andExpect(jsonPath("$.codeetapeparam").value(DEFAULT_CODEETAPEPARAM));
    }


    @Test
    @Transactional
    public void getEtapeparamsByIdFiltering() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        Long id = etapeparam.getId();

        defaultEtapeparamShouldBeFound("id.equals=" + id);
        defaultEtapeparamShouldNotBeFound("id.notEquals=" + id);

        defaultEtapeparamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEtapeparamShouldNotBeFound("id.greaterThan=" + id);

        defaultEtapeparamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEtapeparamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam equals to DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.equals=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam equals to UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.equals=" + UPDATED_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam not equals to DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.notEquals=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam not equals to UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.notEquals=" + UPDATED_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsInShouldWork() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam in DEFAULT_IDETAPEPARAM or UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.in=" + DEFAULT_IDETAPEPARAM + "," + UPDATED_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam equals to UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.in=" + UPDATED_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam is not null
        defaultEtapeparamShouldBeFound("idetapeparam.specified=true");

        // Get all the etapeparamList where idetapeparam is null
        defaultEtapeparamShouldNotBeFound("idetapeparam.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam is greater than or equal to DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.greaterThanOrEqual=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam is greater than or equal to UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.greaterThanOrEqual=" + UPDATED_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam is less than or equal to DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.lessThanOrEqual=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam is less than or equal to SMALLER_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.lessThanOrEqual=" + SMALLER_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsLessThanSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam is less than DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.lessThan=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam is less than UPDATED_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.lessThan=" + UPDATED_IDETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByIdetapeparamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where idetapeparam is greater than DEFAULT_IDETAPEPARAM
        defaultEtapeparamShouldNotBeFound("idetapeparam.greaterThan=" + DEFAULT_IDETAPEPARAM);

        // Get all the etapeparamList where idetapeparam is greater than SMALLER_IDETAPEPARAM
        defaultEtapeparamShouldBeFound("idetapeparam.greaterThan=" + SMALLER_IDETAPEPARAM);
    }


    @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam equals to DEFAULT_LIBELLEETAPEPARAM
        defaultEtapeparamShouldBeFound("libelleetapeparam.equals=" + DEFAULT_LIBELLEETAPEPARAM);

        // Get all the etapeparamList where libelleetapeparam equals to UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.equals=" + UPDATED_LIBELLEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam not equals to DEFAULT_LIBELLEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.notEquals=" + DEFAULT_LIBELLEETAPEPARAM);

        // Get all the etapeparamList where libelleetapeparam not equals to UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldBeFound("libelleetapeparam.notEquals=" + UPDATED_LIBELLEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamIsInShouldWork() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam in DEFAULT_LIBELLEETAPEPARAM or UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldBeFound("libelleetapeparam.in=" + DEFAULT_LIBELLEETAPEPARAM + "," + UPDATED_LIBELLEETAPEPARAM);

        // Get all the etapeparamList where libelleetapeparam equals to UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.in=" + UPDATED_LIBELLEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam is not null
        defaultEtapeparamShouldBeFound("libelleetapeparam.specified=true");

        // Get all the etapeparamList where libelleetapeparam is null
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamContainsSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam contains DEFAULT_LIBELLEETAPEPARAM
        defaultEtapeparamShouldBeFound("libelleetapeparam.contains=" + DEFAULT_LIBELLEETAPEPARAM);

        // Get all the etapeparamList where libelleetapeparam contains UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.contains=" + UPDATED_LIBELLEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByLibelleetapeparamNotContainsSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where libelleetapeparam does not contain DEFAULT_LIBELLEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("libelleetapeparam.doesNotContain=" + DEFAULT_LIBELLEETAPEPARAM);

        // Get all the etapeparamList where libelleetapeparam does not contain UPDATED_LIBELLEETAPEPARAM
        defaultEtapeparamShouldBeFound("libelleetapeparam.doesNotContain=" + UPDATED_LIBELLEETAPEPARAM);
    }


    @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam equals to DEFAULT_CODEETAPEPARAM
        defaultEtapeparamShouldBeFound("codeetapeparam.equals=" + DEFAULT_CODEETAPEPARAM);

        // Get all the etapeparamList where codeetapeparam equals to UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("codeetapeparam.equals=" + UPDATED_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam not equals to DEFAULT_CODEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("codeetapeparam.notEquals=" + DEFAULT_CODEETAPEPARAM);

        // Get all the etapeparamList where codeetapeparam not equals to UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldBeFound("codeetapeparam.notEquals=" + UPDATED_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamIsInShouldWork() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam in DEFAULT_CODEETAPEPARAM or UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldBeFound("codeetapeparam.in=" + DEFAULT_CODEETAPEPARAM + "," + UPDATED_CODEETAPEPARAM);

        // Get all the etapeparamList where codeetapeparam equals to UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("codeetapeparam.in=" + UPDATED_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam is not null
        defaultEtapeparamShouldBeFound("codeetapeparam.specified=true");

        // Get all the etapeparamList where codeetapeparam is null
        defaultEtapeparamShouldNotBeFound("codeetapeparam.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamContainsSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam contains DEFAULT_CODEETAPEPARAM
        defaultEtapeparamShouldBeFound("codeetapeparam.contains=" + DEFAULT_CODEETAPEPARAM);

        // Get all the etapeparamList where codeetapeparam contains UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("codeetapeparam.contains=" + UPDATED_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void getAllEtapeparamsByCodeetapeparamNotContainsSomething() throws Exception {
        // Initialize the database
        etapeparamRepository.saveAndFlush(etapeparam);

        // Get all the etapeparamList where codeetapeparam does not contain DEFAULT_CODEETAPEPARAM
        defaultEtapeparamShouldNotBeFound("codeetapeparam.doesNotContain=" + DEFAULT_CODEETAPEPARAM);

        // Get all the etapeparamList where codeetapeparam does not contain UPDATED_CODEETAPEPARAM
        defaultEtapeparamShouldBeFound("codeetapeparam.doesNotContain=" + UPDATED_CODEETAPEPARAM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEtapeparamShouldBeFound(String filter) throws Exception {
        restEtapeparamMockMvc.perform(get("/api/etapeparams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapeparam.getId().intValue())))
            .andExpect(jsonPath("$.[*].idetapeparam").value(hasItem(DEFAULT_IDETAPEPARAM.intValue())))
            .andExpect(jsonPath("$.[*].libelleetapeparam").value(hasItem(DEFAULT_LIBELLEETAPEPARAM)))
            .andExpect(jsonPath("$.[*].codeetapeparam").value(hasItem(DEFAULT_CODEETAPEPARAM)));

        // Check, that the count call also returns 1
        restEtapeparamMockMvc.perform(get("/api/etapeparams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEtapeparamShouldNotBeFound(String filter) throws Exception {
        restEtapeparamMockMvc.perform(get("/api/etapeparams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEtapeparamMockMvc.perform(get("/api/etapeparams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEtapeparam() throws Exception {
        // Get the etapeparam
        restEtapeparamMockMvc.perform(get("/api/etapeparams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapeparam() throws Exception {
        // Initialize the database
        etapeparamService.save(etapeparam);

        int databaseSizeBeforeUpdate = etapeparamRepository.findAll().size();

        // Update the etapeparam
        Etapeparam updatedEtapeparam = etapeparamRepository.findById(etapeparam.getId()).get();
        // Disconnect from session so that the updates on updatedEtapeparam are not directly saved in db
        em.detach(updatedEtapeparam);
        updatedEtapeparam
            .idetapeparam(UPDATED_IDETAPEPARAM)
            .libelleetapeparam(UPDATED_LIBELLEETAPEPARAM)
            .codeetapeparam(UPDATED_CODEETAPEPARAM);

        restEtapeparamMockMvc.perform(put("/api/etapeparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtapeparam)))
            .andExpect(status().isOk());

        // Validate the Etapeparam in the database
        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeUpdate);
        Etapeparam testEtapeparam = etapeparamList.get(etapeparamList.size() - 1);
        assertThat(testEtapeparam.getIdetapeparam()).isEqualTo(UPDATED_IDETAPEPARAM);
        assertThat(testEtapeparam.getLibelleetapeparam()).isEqualTo(UPDATED_LIBELLEETAPEPARAM);
        assertThat(testEtapeparam.getCodeetapeparam()).isEqualTo(UPDATED_CODEETAPEPARAM);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapeparam() throws Exception {
        int databaseSizeBeforeUpdate = etapeparamRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeparamMockMvc.perform(put("/api/etapeparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etapeparam)))
            .andExpect(status().isBadRequest());

        // Validate the Etapeparam in the database
        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtapeparam() throws Exception {
        // Initialize the database
        etapeparamService.save(etapeparam);

        int databaseSizeBeforeDelete = etapeparamRepository.findAll().size();

        // Delete the etapeparam
        restEtapeparamMockMvc.perform(delete("/api/etapeparams/{id}", etapeparam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etapeparam> etapeparamList = etapeparamRepository.findAll();
        assertThat(etapeparamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
