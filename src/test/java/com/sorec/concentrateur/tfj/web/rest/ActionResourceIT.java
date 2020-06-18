package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Action;
import com.sorec.concentrateur.tfj.domain.Etape;
import com.sorec.concentrateur.tfj.repository.ActionRepository;
import com.sorec.concentrateur.tfj.service.ActionService;
import com.sorec.concentrateur.tfj.service.dto.ActionCriteria;
import com.sorec.concentrateur.tfj.service.ActionQueryService;

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
 * Integration tests for the {@link ActionResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActionResourceIT {

    private static final Long DEFAULT_IDACTION = 1L;
    private static final Long UPDATED_IDACTION = 2L;
    private static final Long SMALLER_IDACTION = 1L - 1L;

    private static final String DEFAULT_LIBELLEACTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLEACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUTACTION = "AAAAAAAAAA";
    private static final String UPDATED_STATUTACTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODEACTION = "AAAAAAAAAA";
    private static final String UPDATED_CODEACTION = "BBBBBBBBBB";

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionService actionService;

    @Autowired
    private ActionQueryService actionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionMockMvc;

    private Action action;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .idaction(DEFAULT_IDACTION)
            .libelleaction(DEFAULT_LIBELLEACTION)
            .statutaction(DEFAULT_STATUTACTION)
            .codeaction(DEFAULT_CODEACTION);
        return action;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action()
            .idaction(UPDATED_IDACTION)
            .libelleaction(UPDATED_LIBELLEACTION)
            .statutaction(UPDATED_STATUTACTION)
            .codeaction(UPDATED_CODEACTION);
        return action;
    }

    @BeforeEach
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();
        // Create the Action
        restActionMockMvc.perform(post("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getIdaction()).isEqualTo(DEFAULT_IDACTION);
        assertThat(testAction.getLibelleaction()).isEqualTo(DEFAULT_LIBELLEACTION);
        assertThat(testAction.getStatutaction()).isEqualTo(DEFAULT_STATUTACTION);
        assertThat(testAction.getCodeaction()).isEqualTo(DEFAULT_CODEACTION);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc.perform(post("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setIdaction(null);

        // Create the Action, which fails.


        restActionMockMvc.perform(post("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc.perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].idaction").value(hasItem(DEFAULT_IDACTION.intValue())))
            .andExpect(jsonPath("$.[*].libelleaction").value(hasItem(DEFAULT_LIBELLEACTION)))
            .andExpect(jsonPath("$.[*].statutaction").value(hasItem(DEFAULT_STATUTACTION)))
            .andExpect(jsonPath("$.[*].codeaction").value(hasItem(DEFAULT_CODEACTION)));
    }
    
    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.idaction").value(DEFAULT_IDACTION.intValue()))
            .andExpect(jsonPath("$.libelleaction").value(DEFAULT_LIBELLEACTION))
            .andExpect(jsonPath("$.statutaction").value(DEFAULT_STATUTACTION))
            .andExpect(jsonPath("$.codeaction").value(DEFAULT_CODEACTION));
    }


    @Test
    @Transactional
    public void getActionsByIdFiltering() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        Long id = action.getId();

        defaultActionShouldBeFound("id.equals=" + id);
        defaultActionShouldNotBeFound("id.notEquals=" + id);

        defaultActionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActionShouldNotBeFound("id.greaterThan=" + id);

        defaultActionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllActionsByIdactionIsEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction equals to DEFAULT_IDACTION
        defaultActionShouldBeFound("idaction.equals=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction equals to UPDATED_IDACTION
        defaultActionShouldNotBeFound("idaction.equals=" + UPDATED_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction not equals to DEFAULT_IDACTION
        defaultActionShouldNotBeFound("idaction.notEquals=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction not equals to UPDATED_IDACTION
        defaultActionShouldBeFound("idaction.notEquals=" + UPDATED_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsInShouldWork() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction in DEFAULT_IDACTION or UPDATED_IDACTION
        defaultActionShouldBeFound("idaction.in=" + DEFAULT_IDACTION + "," + UPDATED_IDACTION);

        // Get all the actionList where idaction equals to UPDATED_IDACTION
        defaultActionShouldNotBeFound("idaction.in=" + UPDATED_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction is not null
        defaultActionShouldBeFound("idaction.specified=true");

        // Get all the actionList where idaction is null
        defaultActionShouldNotBeFound("idaction.specified=false");
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction is greater than or equal to DEFAULT_IDACTION
        defaultActionShouldBeFound("idaction.greaterThanOrEqual=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction is greater than or equal to UPDATED_IDACTION
        defaultActionShouldNotBeFound("idaction.greaterThanOrEqual=" + UPDATED_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction is less than or equal to DEFAULT_IDACTION
        defaultActionShouldBeFound("idaction.lessThanOrEqual=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction is less than or equal to SMALLER_IDACTION
        defaultActionShouldNotBeFound("idaction.lessThanOrEqual=" + SMALLER_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsLessThanSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction is less than DEFAULT_IDACTION
        defaultActionShouldNotBeFound("idaction.lessThan=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction is less than UPDATED_IDACTION
        defaultActionShouldBeFound("idaction.lessThan=" + UPDATED_IDACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByIdactionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where idaction is greater than DEFAULT_IDACTION
        defaultActionShouldNotBeFound("idaction.greaterThan=" + DEFAULT_IDACTION);

        // Get all the actionList where idaction is greater than SMALLER_IDACTION
        defaultActionShouldBeFound("idaction.greaterThan=" + SMALLER_IDACTION);
    }


    @Test
    @Transactional
    public void getAllActionsByLibelleactionIsEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction equals to DEFAULT_LIBELLEACTION
        defaultActionShouldBeFound("libelleaction.equals=" + DEFAULT_LIBELLEACTION);

        // Get all the actionList where libelleaction equals to UPDATED_LIBELLEACTION
        defaultActionShouldNotBeFound("libelleaction.equals=" + UPDATED_LIBELLEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByLibelleactionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction not equals to DEFAULT_LIBELLEACTION
        defaultActionShouldNotBeFound("libelleaction.notEquals=" + DEFAULT_LIBELLEACTION);

        // Get all the actionList where libelleaction not equals to UPDATED_LIBELLEACTION
        defaultActionShouldBeFound("libelleaction.notEquals=" + UPDATED_LIBELLEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByLibelleactionIsInShouldWork() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction in DEFAULT_LIBELLEACTION or UPDATED_LIBELLEACTION
        defaultActionShouldBeFound("libelleaction.in=" + DEFAULT_LIBELLEACTION + "," + UPDATED_LIBELLEACTION);

        // Get all the actionList where libelleaction equals to UPDATED_LIBELLEACTION
        defaultActionShouldNotBeFound("libelleaction.in=" + UPDATED_LIBELLEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByLibelleactionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction is not null
        defaultActionShouldBeFound("libelleaction.specified=true");

        // Get all the actionList where libelleaction is null
        defaultActionShouldNotBeFound("libelleaction.specified=false");
    }
                @Test
    @Transactional
    public void getAllActionsByLibelleactionContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction contains DEFAULT_LIBELLEACTION
        defaultActionShouldBeFound("libelleaction.contains=" + DEFAULT_LIBELLEACTION);

        // Get all the actionList where libelleaction contains UPDATED_LIBELLEACTION
        defaultActionShouldNotBeFound("libelleaction.contains=" + UPDATED_LIBELLEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByLibelleactionNotContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where libelleaction does not contain DEFAULT_LIBELLEACTION
        defaultActionShouldNotBeFound("libelleaction.doesNotContain=" + DEFAULT_LIBELLEACTION);

        // Get all the actionList where libelleaction does not contain UPDATED_LIBELLEACTION
        defaultActionShouldBeFound("libelleaction.doesNotContain=" + UPDATED_LIBELLEACTION);
    }


    @Test
    @Transactional
    public void getAllActionsByStatutactionIsEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction equals to DEFAULT_STATUTACTION
        defaultActionShouldBeFound("statutaction.equals=" + DEFAULT_STATUTACTION);

        // Get all the actionList where statutaction equals to UPDATED_STATUTACTION
        defaultActionShouldNotBeFound("statutaction.equals=" + UPDATED_STATUTACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByStatutactionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction not equals to DEFAULT_STATUTACTION
        defaultActionShouldNotBeFound("statutaction.notEquals=" + DEFAULT_STATUTACTION);

        // Get all the actionList where statutaction not equals to UPDATED_STATUTACTION
        defaultActionShouldBeFound("statutaction.notEquals=" + UPDATED_STATUTACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByStatutactionIsInShouldWork() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction in DEFAULT_STATUTACTION or UPDATED_STATUTACTION
        defaultActionShouldBeFound("statutaction.in=" + DEFAULT_STATUTACTION + "," + UPDATED_STATUTACTION);

        // Get all the actionList where statutaction equals to UPDATED_STATUTACTION
        defaultActionShouldNotBeFound("statutaction.in=" + UPDATED_STATUTACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByStatutactionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction is not null
        defaultActionShouldBeFound("statutaction.specified=true");

        // Get all the actionList where statutaction is null
        defaultActionShouldNotBeFound("statutaction.specified=false");
    }
                @Test
    @Transactional
    public void getAllActionsByStatutactionContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction contains DEFAULT_STATUTACTION
        defaultActionShouldBeFound("statutaction.contains=" + DEFAULT_STATUTACTION);

        // Get all the actionList where statutaction contains UPDATED_STATUTACTION
        defaultActionShouldNotBeFound("statutaction.contains=" + UPDATED_STATUTACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByStatutactionNotContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where statutaction does not contain DEFAULT_STATUTACTION
        defaultActionShouldNotBeFound("statutaction.doesNotContain=" + DEFAULT_STATUTACTION);

        // Get all the actionList where statutaction does not contain UPDATED_STATUTACTION
        defaultActionShouldBeFound("statutaction.doesNotContain=" + UPDATED_STATUTACTION);
    }


    @Test
    @Transactional
    public void getAllActionsByCodeactionIsEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction equals to DEFAULT_CODEACTION
        defaultActionShouldBeFound("codeaction.equals=" + DEFAULT_CODEACTION);

        // Get all the actionList where codeaction equals to UPDATED_CODEACTION
        defaultActionShouldNotBeFound("codeaction.equals=" + UPDATED_CODEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByCodeactionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction not equals to DEFAULT_CODEACTION
        defaultActionShouldNotBeFound("codeaction.notEquals=" + DEFAULT_CODEACTION);

        // Get all the actionList where codeaction not equals to UPDATED_CODEACTION
        defaultActionShouldBeFound("codeaction.notEquals=" + UPDATED_CODEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByCodeactionIsInShouldWork() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction in DEFAULT_CODEACTION or UPDATED_CODEACTION
        defaultActionShouldBeFound("codeaction.in=" + DEFAULT_CODEACTION + "," + UPDATED_CODEACTION);

        // Get all the actionList where codeaction equals to UPDATED_CODEACTION
        defaultActionShouldNotBeFound("codeaction.in=" + UPDATED_CODEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByCodeactionIsNullOrNotNull() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction is not null
        defaultActionShouldBeFound("codeaction.specified=true");

        // Get all the actionList where codeaction is null
        defaultActionShouldNotBeFound("codeaction.specified=false");
    }
                @Test
    @Transactional
    public void getAllActionsByCodeactionContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction contains DEFAULT_CODEACTION
        defaultActionShouldBeFound("codeaction.contains=" + DEFAULT_CODEACTION);

        // Get all the actionList where codeaction contains UPDATED_CODEACTION
        defaultActionShouldNotBeFound("codeaction.contains=" + UPDATED_CODEACTION);
    }

    @Test
    @Transactional
    public void getAllActionsByCodeactionNotContainsSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList where codeaction does not contain DEFAULT_CODEACTION
        defaultActionShouldNotBeFound("codeaction.doesNotContain=" + DEFAULT_CODEACTION);

        // Get all the actionList where codeaction does not contain UPDATED_CODEACTION
        defaultActionShouldBeFound("codeaction.doesNotContain=" + UPDATED_CODEACTION);
    }


    @Test
    @Transactional
    public void getAllActionsByIdetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);
        Etape idetape = EtapeResourceIT.createEntity(em);
        em.persist(idetape);
        em.flush();
        action.setIdetape(idetape);
        actionRepository.saveAndFlush(action);
        Long idetapeId = idetape.getId();

        // Get all the actionList where idetape equals to idetapeId
        defaultActionShouldBeFound("idetapeId.equals=" + idetapeId);

        // Get all the actionList where idetape equals to idetapeId + 1
        defaultActionShouldNotBeFound("idetapeId.equals=" + (idetapeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActionShouldBeFound(String filter) throws Exception {
        restActionMockMvc.perform(get("/api/actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].idaction").value(hasItem(DEFAULT_IDACTION.intValue())))
            .andExpect(jsonPath("$.[*].libelleaction").value(hasItem(DEFAULT_LIBELLEACTION)))
            .andExpect(jsonPath("$.[*].statutaction").value(hasItem(DEFAULT_STATUTACTION)))
            .andExpect(jsonPath("$.[*].codeaction").value(hasItem(DEFAULT_CODEACTION)));

        // Check, that the count call also returns 1
        restActionMockMvc.perform(get("/api/actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActionShouldNotBeFound(String filter) throws Exception {
        restActionMockMvc.perform(get("/api/actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActionMockMvc.perform(get("/api/actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionService.save(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .idaction(UPDATED_IDACTION)
            .libelleaction(UPDATED_LIBELLEACTION)
            .statutaction(UPDATED_STATUTACTION)
            .codeaction(UPDATED_CODEACTION);

        restActionMockMvc.perform(put("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAction)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getIdaction()).isEqualTo(UPDATED_IDACTION);
        assertThat(testAction.getLibelleaction()).isEqualTo(UPDATED_LIBELLEACTION);
        assertThat(testAction.getStatutaction()).isEqualTo(UPDATED_STATUTACTION);
        assertThat(testAction.getCodeaction()).isEqualTo(UPDATED_CODEACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc.perform(put("/api/actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionService.save(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Delete the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
