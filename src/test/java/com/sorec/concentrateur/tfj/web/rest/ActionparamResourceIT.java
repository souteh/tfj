package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Actionparam;
import com.sorec.concentrateur.tfj.domain.Etapeparam;
import com.sorec.concentrateur.tfj.repository.ActionparamRepository;
import com.sorec.concentrateur.tfj.service.ActionparamService;
import com.sorec.concentrateur.tfj.service.dto.ActionparamCriteria;
import com.sorec.concentrateur.tfj.service.ActionparamQueryService;

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
 * Integration tests for the {@link ActionparamResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActionparamResourceIT {

    private static final Long DEFAULT_IDACTIONPARAM = 1L;
    private static final Long UPDATED_IDACTIONPARAM = 2L;
    private static final Long SMALLER_IDACTIONPARAM = 1L - 1L;

    private static final String DEFAULT_LIBELLEACTIONPARAM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLEACTIONPARAM = "BBBBBBBBBB";

    private static final String DEFAULT_CODEACTIONPARAM = "AAAAAAAAAA";
    private static final String UPDATED_CODEACTIONPARAM = "BBBBBBBBBB";

    @Autowired
    private ActionparamRepository actionparamRepository;

    @Autowired
    private ActionparamService actionparamService;

    @Autowired
    private ActionparamQueryService actionparamQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionparamMockMvc;

    private Actionparam actionparam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actionparam createEntity(EntityManager em) {
        Actionparam actionparam = new Actionparam()
            .idactionparam(DEFAULT_IDACTIONPARAM)
            .libelleactionparam(DEFAULT_LIBELLEACTIONPARAM)
            .codeactionparam(DEFAULT_CODEACTIONPARAM);
        return actionparam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actionparam createUpdatedEntity(EntityManager em) {
        Actionparam actionparam = new Actionparam()
            .idactionparam(UPDATED_IDACTIONPARAM)
            .libelleactionparam(UPDATED_LIBELLEACTIONPARAM)
            .codeactionparam(UPDATED_CODEACTIONPARAM);
        return actionparam;
    }

    @BeforeEach
    public void initTest() {
        actionparam = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionparam() throws Exception {
        int databaseSizeBeforeCreate = actionparamRepository.findAll().size();
        // Create the Actionparam
        restActionparamMockMvc.perform(post("/api/actionparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionparam)))
            .andExpect(status().isCreated());

        // Validate the Actionparam in the database
        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeCreate + 1);
        Actionparam testActionparam = actionparamList.get(actionparamList.size() - 1);
        assertThat(testActionparam.getIdactionparam()).isEqualTo(DEFAULT_IDACTIONPARAM);
        assertThat(testActionparam.getLibelleactionparam()).isEqualTo(DEFAULT_LIBELLEACTIONPARAM);
        assertThat(testActionparam.getCodeactionparam()).isEqualTo(DEFAULT_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void createActionparamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionparamRepository.findAll().size();

        // Create the Actionparam with an existing ID
        actionparam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionparamMockMvc.perform(post("/api/actionparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionparam)))
            .andExpect(status().isBadRequest());

        // Validate the Actionparam in the database
        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdactionparamIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionparamRepository.findAll().size();
        // set the field null
        actionparam.setIdactionparam(null);

        // Create the Actionparam, which fails.


        restActionparamMockMvc.perform(post("/api/actionparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionparam)))
            .andExpect(status().isBadRequest());

        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionparams() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList
        restActionparamMockMvc.perform(get("/api/actionparams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionparam.getId().intValue())))
            .andExpect(jsonPath("$.[*].idactionparam").value(hasItem(DEFAULT_IDACTIONPARAM.intValue())))
            .andExpect(jsonPath("$.[*].libelleactionparam").value(hasItem(DEFAULT_LIBELLEACTIONPARAM)))
            .andExpect(jsonPath("$.[*].codeactionparam").value(hasItem(DEFAULT_CODEACTIONPARAM)));
    }
    
    @Test
    @Transactional
    public void getActionparam() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get the actionparam
        restActionparamMockMvc.perform(get("/api/actionparams/{id}", actionparam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionparam.getId().intValue()))
            .andExpect(jsonPath("$.idactionparam").value(DEFAULT_IDACTIONPARAM.intValue()))
            .andExpect(jsonPath("$.libelleactionparam").value(DEFAULT_LIBELLEACTIONPARAM))
            .andExpect(jsonPath("$.codeactionparam").value(DEFAULT_CODEACTIONPARAM));
    }


    @Test
    @Transactional
    public void getActionparamsByIdFiltering() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        Long id = actionparam.getId();

        defaultActionparamShouldBeFound("id.equals=" + id);
        defaultActionparamShouldNotBeFound("id.notEquals=" + id);

        defaultActionparamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActionparamShouldNotBeFound("id.greaterThan=" + id);

        defaultActionparamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActionparamShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam equals to DEFAULT_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.equals=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam equals to UPDATED_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.equals=" + UPDATED_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam not equals to DEFAULT_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.notEquals=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam not equals to UPDATED_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.notEquals=" + UPDATED_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsInShouldWork() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam in DEFAULT_IDACTIONPARAM or UPDATED_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.in=" + DEFAULT_IDACTIONPARAM + "," + UPDATED_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam equals to UPDATED_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.in=" + UPDATED_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam is not null
        defaultActionparamShouldBeFound("idactionparam.specified=true");

        // Get all the actionparamList where idactionparam is null
        defaultActionparamShouldNotBeFound("idactionparam.specified=false");
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam is greater than or equal to DEFAULT_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.greaterThanOrEqual=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam is greater than or equal to UPDATED_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.greaterThanOrEqual=" + UPDATED_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam is less than or equal to DEFAULT_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.lessThanOrEqual=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam is less than or equal to SMALLER_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.lessThanOrEqual=" + SMALLER_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsLessThanSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam is less than DEFAULT_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.lessThan=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam is less than UPDATED_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.lessThan=" + UPDATED_IDACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByIdactionparamIsGreaterThanSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where idactionparam is greater than DEFAULT_IDACTIONPARAM
        defaultActionparamShouldNotBeFound("idactionparam.greaterThan=" + DEFAULT_IDACTIONPARAM);

        // Get all the actionparamList where idactionparam is greater than SMALLER_IDACTIONPARAM
        defaultActionparamShouldBeFound("idactionparam.greaterThan=" + SMALLER_IDACTIONPARAM);
    }


    @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamIsEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam equals to DEFAULT_LIBELLEACTIONPARAM
        defaultActionparamShouldBeFound("libelleactionparam.equals=" + DEFAULT_LIBELLEACTIONPARAM);

        // Get all the actionparamList where libelleactionparam equals to UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldNotBeFound("libelleactionparam.equals=" + UPDATED_LIBELLEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam not equals to DEFAULT_LIBELLEACTIONPARAM
        defaultActionparamShouldNotBeFound("libelleactionparam.notEquals=" + DEFAULT_LIBELLEACTIONPARAM);

        // Get all the actionparamList where libelleactionparam not equals to UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldBeFound("libelleactionparam.notEquals=" + UPDATED_LIBELLEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamIsInShouldWork() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam in DEFAULT_LIBELLEACTIONPARAM or UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldBeFound("libelleactionparam.in=" + DEFAULT_LIBELLEACTIONPARAM + "," + UPDATED_LIBELLEACTIONPARAM);

        // Get all the actionparamList where libelleactionparam equals to UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldNotBeFound("libelleactionparam.in=" + UPDATED_LIBELLEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam is not null
        defaultActionparamShouldBeFound("libelleactionparam.specified=true");

        // Get all the actionparamList where libelleactionparam is null
        defaultActionparamShouldNotBeFound("libelleactionparam.specified=false");
    }
                @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamContainsSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam contains DEFAULT_LIBELLEACTIONPARAM
        defaultActionparamShouldBeFound("libelleactionparam.contains=" + DEFAULT_LIBELLEACTIONPARAM);

        // Get all the actionparamList where libelleactionparam contains UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldNotBeFound("libelleactionparam.contains=" + UPDATED_LIBELLEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByLibelleactionparamNotContainsSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where libelleactionparam does not contain DEFAULT_LIBELLEACTIONPARAM
        defaultActionparamShouldNotBeFound("libelleactionparam.doesNotContain=" + DEFAULT_LIBELLEACTIONPARAM);

        // Get all the actionparamList where libelleactionparam does not contain UPDATED_LIBELLEACTIONPARAM
        defaultActionparamShouldBeFound("libelleactionparam.doesNotContain=" + UPDATED_LIBELLEACTIONPARAM);
    }


    @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamIsEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam equals to DEFAULT_CODEACTIONPARAM
        defaultActionparamShouldBeFound("codeactionparam.equals=" + DEFAULT_CODEACTIONPARAM);

        // Get all the actionparamList where codeactionparam equals to UPDATED_CODEACTIONPARAM
        defaultActionparamShouldNotBeFound("codeactionparam.equals=" + UPDATED_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam not equals to DEFAULT_CODEACTIONPARAM
        defaultActionparamShouldNotBeFound("codeactionparam.notEquals=" + DEFAULT_CODEACTIONPARAM);

        // Get all the actionparamList where codeactionparam not equals to UPDATED_CODEACTIONPARAM
        defaultActionparamShouldBeFound("codeactionparam.notEquals=" + UPDATED_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamIsInShouldWork() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam in DEFAULT_CODEACTIONPARAM or UPDATED_CODEACTIONPARAM
        defaultActionparamShouldBeFound("codeactionparam.in=" + DEFAULT_CODEACTIONPARAM + "," + UPDATED_CODEACTIONPARAM);

        // Get all the actionparamList where codeactionparam equals to UPDATED_CODEACTIONPARAM
        defaultActionparamShouldNotBeFound("codeactionparam.in=" + UPDATED_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam is not null
        defaultActionparamShouldBeFound("codeactionparam.specified=true");

        // Get all the actionparamList where codeactionparam is null
        defaultActionparamShouldNotBeFound("codeactionparam.specified=false");
    }
                @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamContainsSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam contains DEFAULT_CODEACTIONPARAM
        defaultActionparamShouldBeFound("codeactionparam.contains=" + DEFAULT_CODEACTIONPARAM);

        // Get all the actionparamList where codeactionparam contains UPDATED_CODEACTIONPARAM
        defaultActionparamShouldNotBeFound("codeactionparam.contains=" + UPDATED_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void getAllActionparamsByCodeactionparamNotContainsSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);

        // Get all the actionparamList where codeactionparam does not contain DEFAULT_CODEACTIONPARAM
        defaultActionparamShouldNotBeFound("codeactionparam.doesNotContain=" + DEFAULT_CODEACTIONPARAM);

        // Get all the actionparamList where codeactionparam does not contain UPDATED_CODEACTIONPARAM
        defaultActionparamShouldBeFound("codeactionparam.doesNotContain=" + UPDATED_CODEACTIONPARAM);
    }


    @Test
    @Transactional
    public void getAllActionparamsByIdetapeparamIsEqualToSomething() throws Exception {
        // Initialize the database
        actionparamRepository.saveAndFlush(actionparam);
        Etapeparam idetapeparam = EtapeparamResourceIT.createEntity(em);
        em.persist(idetapeparam);
        em.flush();
        actionparam.setIdetapeparam(idetapeparam);
        actionparamRepository.saveAndFlush(actionparam);
        Long idetapeparamId = idetapeparam.getId();

        // Get all the actionparamList where idetapeparam equals to idetapeparamId
        defaultActionparamShouldBeFound("idetapeparamId.equals=" + idetapeparamId);

        // Get all the actionparamList where idetapeparam equals to idetapeparamId + 1
        defaultActionparamShouldNotBeFound("idetapeparamId.equals=" + (idetapeparamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActionparamShouldBeFound(String filter) throws Exception {
        restActionparamMockMvc.perform(get("/api/actionparams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionparam.getId().intValue())))
            .andExpect(jsonPath("$.[*].idactionparam").value(hasItem(DEFAULT_IDACTIONPARAM.intValue())))
            .andExpect(jsonPath("$.[*].libelleactionparam").value(hasItem(DEFAULT_LIBELLEACTIONPARAM)))
            .andExpect(jsonPath("$.[*].codeactionparam").value(hasItem(DEFAULT_CODEACTIONPARAM)));

        // Check, that the count call also returns 1
        restActionparamMockMvc.perform(get("/api/actionparams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActionparamShouldNotBeFound(String filter) throws Exception {
        restActionparamMockMvc.perform(get("/api/actionparams?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActionparamMockMvc.perform(get("/api/actionparams/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingActionparam() throws Exception {
        // Get the actionparam
        restActionparamMockMvc.perform(get("/api/actionparams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionparam() throws Exception {
        // Initialize the database
        actionparamService.save(actionparam);

        int databaseSizeBeforeUpdate = actionparamRepository.findAll().size();

        // Update the actionparam
        Actionparam updatedActionparam = actionparamRepository.findById(actionparam.getId()).get();
        // Disconnect from session so that the updates on updatedActionparam are not directly saved in db
        em.detach(updatedActionparam);
        updatedActionparam
            .idactionparam(UPDATED_IDACTIONPARAM)
            .libelleactionparam(UPDATED_LIBELLEACTIONPARAM)
            .codeactionparam(UPDATED_CODEACTIONPARAM);

        restActionparamMockMvc.perform(put("/api/actionparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActionparam)))
            .andExpect(status().isOk());

        // Validate the Actionparam in the database
        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeUpdate);
        Actionparam testActionparam = actionparamList.get(actionparamList.size() - 1);
        assertThat(testActionparam.getIdactionparam()).isEqualTo(UPDATED_IDACTIONPARAM);
        assertThat(testActionparam.getLibelleactionparam()).isEqualTo(UPDATED_LIBELLEACTIONPARAM);
        assertThat(testActionparam.getCodeactionparam()).isEqualTo(UPDATED_CODEACTIONPARAM);
    }

    @Test
    @Transactional
    public void updateNonExistingActionparam() throws Exception {
        int databaseSizeBeforeUpdate = actionparamRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionparamMockMvc.perform(put("/api/actionparams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionparam)))
            .andExpect(status().isBadRequest());

        // Validate the Actionparam in the database
        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionparam() throws Exception {
        // Initialize the database
        actionparamService.save(actionparam);

        int databaseSizeBeforeDelete = actionparamRepository.findAll().size();

        // Delete the actionparam
        restActionparamMockMvc.perform(delete("/api/actionparams/{id}", actionparam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Actionparam> actionparamList = actionparamRepository.findAll();
        assertThat(actionparamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
