package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Messageerreur;
import com.sorec.concentrateur.tfj.domain.Action;
import com.sorec.concentrateur.tfj.repository.MessageerreurRepository;
import com.sorec.concentrateur.tfj.service.MessageerreurService;
import com.sorec.concentrateur.tfj.service.dto.MessageerreurCriteria;
import com.sorec.concentrateur.tfj.service.MessageerreurQueryService;

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
 * Integration tests for the {@link MessageerreurResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MessageerreurResourceIT {

    private static final Long DEFAULT_IDMESSAGE = 1L;
    private static final Long UPDATED_IDMESSAGE = 2L;
    private static final Long SMALLER_IDMESSAGE = 1L - 1L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MessageerreurRepository messageerreurRepository;

    @Autowired
    private MessageerreurService messageerreurService;

    @Autowired
    private MessageerreurQueryService messageerreurQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageerreurMockMvc;

    private Messageerreur messageerreur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Messageerreur createEntity(EntityManager em) {
        Messageerreur messageerreur = new Messageerreur()
            .idmessage(DEFAULT_IDMESSAGE)
            .description(DEFAULT_DESCRIPTION);
        return messageerreur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Messageerreur createUpdatedEntity(EntityManager em) {
        Messageerreur messageerreur = new Messageerreur()
            .idmessage(UPDATED_IDMESSAGE)
            .description(UPDATED_DESCRIPTION);
        return messageerreur;
    }

    @BeforeEach
    public void initTest() {
        messageerreur = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageerreur() throws Exception {
        int databaseSizeBeforeCreate = messageerreurRepository.findAll().size();
        // Create the Messageerreur
        restMessageerreurMockMvc.perform(post("/api/messageerreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageerreur)))
            .andExpect(status().isCreated());

        // Validate the Messageerreur in the database
        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeCreate + 1);
        Messageerreur testMessageerreur = messageerreurList.get(messageerreurList.size() - 1);
        assertThat(testMessageerreur.getIdmessage()).isEqualTo(DEFAULT_IDMESSAGE);
        assertThat(testMessageerreur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMessageerreurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageerreurRepository.findAll().size();

        // Create the Messageerreur with an existing ID
        messageerreur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageerreurMockMvc.perform(post("/api/messageerreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageerreur)))
            .andExpect(status().isBadRequest());

        // Validate the Messageerreur in the database
        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdmessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageerreurRepository.findAll().size();
        // set the field null
        messageerreur.setIdmessage(null);

        // Create the Messageerreur, which fails.


        restMessageerreurMockMvc.perform(post("/api/messageerreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageerreur)))
            .andExpect(status().isBadRequest());

        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageerreurs() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList
        restMessageerreurMockMvc.perform(get("/api/messageerreurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageerreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmessage").value(hasItem(DEFAULT_IDMESSAGE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getMessageerreur() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get the messageerreur
        restMessageerreurMockMvc.perform(get("/api/messageerreurs/{id}", messageerreur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageerreur.getId().intValue()))
            .andExpect(jsonPath("$.idmessage").value(DEFAULT_IDMESSAGE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getMessageerreursByIdFiltering() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        Long id = messageerreur.getId();

        defaultMessageerreurShouldBeFound("id.equals=" + id);
        defaultMessageerreurShouldNotBeFound("id.notEquals=" + id);

        defaultMessageerreurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageerreurShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageerreurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageerreurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage equals to DEFAULT_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.equals=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage equals to UPDATED_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.equals=" + UPDATED_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage not equals to DEFAULT_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.notEquals=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage not equals to UPDATED_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.notEquals=" + UPDATED_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsInShouldWork() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage in DEFAULT_IDMESSAGE or UPDATED_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.in=" + DEFAULT_IDMESSAGE + "," + UPDATED_IDMESSAGE);

        // Get all the messageerreurList where idmessage equals to UPDATED_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.in=" + UPDATED_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage is not null
        defaultMessageerreurShouldBeFound("idmessage.specified=true");

        // Get all the messageerreurList where idmessage is null
        defaultMessageerreurShouldNotBeFound("idmessage.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage is greater than or equal to DEFAULT_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.greaterThanOrEqual=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage is greater than or equal to UPDATED_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.greaterThanOrEqual=" + UPDATED_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage is less than or equal to DEFAULT_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.lessThanOrEqual=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage is less than or equal to SMALLER_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.lessThanOrEqual=" + SMALLER_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsLessThanSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage is less than DEFAULT_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.lessThan=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage is less than UPDATED_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.lessThan=" + UPDATED_IDMESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByIdmessageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where idmessage is greater than DEFAULT_IDMESSAGE
        defaultMessageerreurShouldNotBeFound("idmessage.greaterThan=" + DEFAULT_IDMESSAGE);

        // Get all the messageerreurList where idmessage is greater than SMALLER_IDMESSAGE
        defaultMessageerreurShouldBeFound("idmessage.greaterThan=" + SMALLER_IDMESSAGE);
    }


    @Test
    @Transactional
    public void getAllMessageerreursByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description equals to DEFAULT_DESCRIPTION
        defaultMessageerreurShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the messageerreurList where description equals to UPDATED_DESCRIPTION
        defaultMessageerreurShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description not equals to DEFAULT_DESCRIPTION
        defaultMessageerreurShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the messageerreurList where description not equals to UPDATED_DESCRIPTION
        defaultMessageerreurShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMessageerreurShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the messageerreurList where description equals to UPDATED_DESCRIPTION
        defaultMessageerreurShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description is not null
        defaultMessageerreurShouldBeFound("description.specified=true");

        // Get all the messageerreurList where description is null
        defaultMessageerreurShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessageerreursByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description contains DEFAULT_DESCRIPTION
        defaultMessageerreurShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the messageerreurList where description contains UPDATED_DESCRIPTION
        defaultMessageerreurShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMessageerreursByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);

        // Get all the messageerreurList where description does not contain DEFAULT_DESCRIPTION
        defaultMessageerreurShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the messageerreurList where description does not contain UPDATED_DESCRIPTION
        defaultMessageerreurShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMessageerreursByIdactionIsEqualToSomething() throws Exception {
        // Initialize the database
        messageerreurRepository.saveAndFlush(messageerreur);
        Action idaction = ActionResourceIT.createEntity(em);
        em.persist(idaction);
        em.flush();
        messageerreur.setIdaction(idaction);
        messageerreurRepository.saveAndFlush(messageerreur);
        Long idactionId = idaction.getId();

        // Get all the messageerreurList where idaction equals to idactionId
        defaultMessageerreurShouldBeFound("idactionId.equals=" + idactionId);

        // Get all the messageerreurList where idaction equals to idactionId + 1
        defaultMessageerreurShouldNotBeFound("idactionId.equals=" + (idactionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageerreurShouldBeFound(String filter) throws Exception {
        restMessageerreurMockMvc.perform(get("/api/messageerreurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageerreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].idmessage").value(hasItem(DEFAULT_IDMESSAGE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restMessageerreurMockMvc.perform(get("/api/messageerreurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageerreurShouldNotBeFound(String filter) throws Exception {
        restMessageerreurMockMvc.perform(get("/api/messageerreurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageerreurMockMvc.perform(get("/api/messageerreurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMessageerreur() throws Exception {
        // Get the messageerreur
        restMessageerreurMockMvc.perform(get("/api/messageerreurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageerreur() throws Exception {
        // Initialize the database
        messageerreurService.save(messageerreur);

        int databaseSizeBeforeUpdate = messageerreurRepository.findAll().size();

        // Update the messageerreur
        Messageerreur updatedMessageerreur = messageerreurRepository.findById(messageerreur.getId()).get();
        // Disconnect from session so that the updates on updatedMessageerreur are not directly saved in db
        em.detach(updatedMessageerreur);
        updatedMessageerreur
            .idmessage(UPDATED_IDMESSAGE)
            .description(UPDATED_DESCRIPTION);

        restMessageerreurMockMvc.perform(put("/api/messageerreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageerreur)))
            .andExpect(status().isOk());

        // Validate the Messageerreur in the database
        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeUpdate);
        Messageerreur testMessageerreur = messageerreurList.get(messageerreurList.size() - 1);
        assertThat(testMessageerreur.getIdmessage()).isEqualTo(UPDATED_IDMESSAGE);
        assertThat(testMessageerreur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageerreur() throws Exception {
        int databaseSizeBeforeUpdate = messageerreurRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageerreurMockMvc.perform(put("/api/messageerreurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageerreur)))
            .andExpect(status().isBadRequest());

        // Validate the Messageerreur in the database
        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageerreur() throws Exception {
        // Initialize the database
        messageerreurService.save(messageerreur);

        int databaseSizeBeforeDelete = messageerreurRepository.findAll().size();

        // Delete the messageerreur
        restMessageerreurMockMvc.perform(delete("/api/messageerreurs/{id}", messageerreur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Messageerreur> messageerreurList = messageerreurRepository.findAll();
        assertThat(messageerreurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
