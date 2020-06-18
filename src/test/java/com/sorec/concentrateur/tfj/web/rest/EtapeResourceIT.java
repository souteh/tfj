package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Etape;
import com.sorec.concentrateur.tfj.domain.Tfj;
import com.sorec.concentrateur.tfj.repository.EtapeRepository;
import com.sorec.concentrateur.tfj.service.EtapeService;
import com.sorec.concentrateur.tfj.service.dto.EtapeCriteria;
import com.sorec.concentrateur.tfj.service.EtapeQueryService;

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
 * Integration tests for the {@link EtapeResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtapeResourceIT {

    private static final Long DEFAULT_IDETAPE = 1L;
    private static final Long UPDATED_IDETAPE = 2L;
    private static final Long SMALLER_IDETAPE = 1L - 1L;

    private static final String DEFAULT_LIBELLEETAPE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLEETAPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUTETAPE = "AAAAAAAAAA";
    private static final String UPDATED_STATUTETAPE = "BBBBBBBBBB";

    private static final String DEFAULT_HEUREEXECUTIONETAPE = "AAAAAAAAAA";
    private static final String UPDATED_HEUREEXECUTIONETAPE = "BBBBBBBBBB";

    private static final String DEFAULT_CODEETAPE = "AAAAAAAAAA";
    private static final String UPDATED_CODEETAPE = "BBBBBBBBBB";

    @Autowired
    private EtapeRepository etapeRepository;

    @Autowired
    private EtapeService etapeService;

    @Autowired
    private EtapeQueryService etapeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapeMockMvc;

    private Etape etape;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etape createEntity(EntityManager em) {
        Etape etape = new Etape()
            .idetape(DEFAULT_IDETAPE)
            .libelleetape(DEFAULT_LIBELLEETAPE)
            .statutetape(DEFAULT_STATUTETAPE)
            .heureexecutionetape(DEFAULT_HEUREEXECUTIONETAPE)
            .codeetape(DEFAULT_CODEETAPE);
        return etape;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etape createUpdatedEntity(EntityManager em) {
        Etape etape = new Etape()
            .idetape(UPDATED_IDETAPE)
            .libelleetape(UPDATED_LIBELLEETAPE)
            .statutetape(UPDATED_STATUTETAPE)
            .heureexecutionetape(UPDATED_HEUREEXECUTIONETAPE)
            .codeetape(UPDATED_CODEETAPE);
        return etape;
    }

    @BeforeEach
    public void initTest() {
        etape = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtape() throws Exception {
        int databaseSizeBeforeCreate = etapeRepository.findAll().size();
        // Create the Etape
        restEtapeMockMvc.perform(post("/api/etapes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etape)))
            .andExpect(status().isCreated());

        // Validate the Etape in the database
        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeCreate + 1);
        Etape testEtape = etapeList.get(etapeList.size() - 1);
        assertThat(testEtape.getIdetape()).isEqualTo(DEFAULT_IDETAPE);
        assertThat(testEtape.getLibelleetape()).isEqualTo(DEFAULT_LIBELLEETAPE);
        assertThat(testEtape.getStatutetape()).isEqualTo(DEFAULT_STATUTETAPE);
        assertThat(testEtape.getHeureexecutionetape()).isEqualTo(DEFAULT_HEUREEXECUTIONETAPE);
        assertThat(testEtape.getCodeetape()).isEqualTo(DEFAULT_CODEETAPE);
    }

    @Test
    @Transactional
    public void createEtapeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapeRepository.findAll().size();

        // Create the Etape with an existing ID
        etape.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapeMockMvc.perform(post("/api/etapes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etape)))
            .andExpect(status().isBadRequest());

        // Validate the Etape in the database
        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdetapeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etapeRepository.findAll().size();
        // set the field null
        etape.setIdetape(null);

        // Create the Etape, which fails.


        restEtapeMockMvc.perform(post("/api/etapes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etape)))
            .andExpect(status().isBadRequest());

        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtapes() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList
        restEtapeMockMvc.perform(get("/api/etapes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etape.getId().intValue())))
            .andExpect(jsonPath("$.[*].idetape").value(hasItem(DEFAULT_IDETAPE.intValue())))
            .andExpect(jsonPath("$.[*].libelleetape").value(hasItem(DEFAULT_LIBELLEETAPE)))
            .andExpect(jsonPath("$.[*].statutetape").value(hasItem(DEFAULT_STATUTETAPE)))
            .andExpect(jsonPath("$.[*].heureexecutionetape").value(hasItem(DEFAULT_HEUREEXECUTIONETAPE)))
            .andExpect(jsonPath("$.[*].codeetape").value(hasItem(DEFAULT_CODEETAPE)));
    }
    
    @Test
    @Transactional
    public void getEtape() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get the etape
        restEtapeMockMvc.perform(get("/api/etapes/{id}", etape.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etape.getId().intValue()))
            .andExpect(jsonPath("$.idetape").value(DEFAULT_IDETAPE.intValue()))
            .andExpect(jsonPath("$.libelleetape").value(DEFAULT_LIBELLEETAPE))
            .andExpect(jsonPath("$.statutetape").value(DEFAULT_STATUTETAPE))
            .andExpect(jsonPath("$.heureexecutionetape").value(DEFAULT_HEUREEXECUTIONETAPE))
            .andExpect(jsonPath("$.codeetape").value(DEFAULT_CODEETAPE));
    }


    @Test
    @Transactional
    public void getEtapesByIdFiltering() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        Long id = etape.getId();

        defaultEtapeShouldBeFound("id.equals=" + id);
        defaultEtapeShouldNotBeFound("id.notEquals=" + id);

        defaultEtapeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEtapeShouldNotBeFound("id.greaterThan=" + id);

        defaultEtapeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEtapeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape equals to DEFAULT_IDETAPE
        defaultEtapeShouldBeFound("idetape.equals=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape equals to UPDATED_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.equals=" + UPDATED_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape not equals to DEFAULT_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.notEquals=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape not equals to UPDATED_IDETAPE
        defaultEtapeShouldBeFound("idetape.notEquals=" + UPDATED_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsInShouldWork() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape in DEFAULT_IDETAPE or UPDATED_IDETAPE
        defaultEtapeShouldBeFound("idetape.in=" + DEFAULT_IDETAPE + "," + UPDATED_IDETAPE);

        // Get all the etapeList where idetape equals to UPDATED_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.in=" + UPDATED_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape is not null
        defaultEtapeShouldBeFound("idetape.specified=true");

        // Get all the etapeList where idetape is null
        defaultEtapeShouldNotBeFound("idetape.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape is greater than or equal to DEFAULT_IDETAPE
        defaultEtapeShouldBeFound("idetape.greaterThanOrEqual=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape is greater than or equal to UPDATED_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.greaterThanOrEqual=" + UPDATED_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape is less than or equal to DEFAULT_IDETAPE
        defaultEtapeShouldBeFound("idetape.lessThanOrEqual=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape is less than or equal to SMALLER_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.lessThanOrEqual=" + SMALLER_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsLessThanSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape is less than DEFAULT_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.lessThan=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape is less than UPDATED_IDETAPE
        defaultEtapeShouldBeFound("idetape.lessThan=" + UPDATED_IDETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByIdetapeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where idetape is greater than DEFAULT_IDETAPE
        defaultEtapeShouldNotBeFound("idetape.greaterThan=" + DEFAULT_IDETAPE);

        // Get all the etapeList where idetape is greater than SMALLER_IDETAPE
        defaultEtapeShouldBeFound("idetape.greaterThan=" + SMALLER_IDETAPE);
    }


    @Test
    @Transactional
    public void getAllEtapesByLibelleetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape equals to DEFAULT_LIBELLEETAPE
        defaultEtapeShouldBeFound("libelleetape.equals=" + DEFAULT_LIBELLEETAPE);

        // Get all the etapeList where libelleetape equals to UPDATED_LIBELLEETAPE
        defaultEtapeShouldNotBeFound("libelleetape.equals=" + UPDATED_LIBELLEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByLibelleetapeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape not equals to DEFAULT_LIBELLEETAPE
        defaultEtapeShouldNotBeFound("libelleetape.notEquals=" + DEFAULT_LIBELLEETAPE);

        // Get all the etapeList where libelleetape not equals to UPDATED_LIBELLEETAPE
        defaultEtapeShouldBeFound("libelleetape.notEquals=" + UPDATED_LIBELLEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByLibelleetapeIsInShouldWork() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape in DEFAULT_LIBELLEETAPE or UPDATED_LIBELLEETAPE
        defaultEtapeShouldBeFound("libelleetape.in=" + DEFAULT_LIBELLEETAPE + "," + UPDATED_LIBELLEETAPE);

        // Get all the etapeList where libelleetape equals to UPDATED_LIBELLEETAPE
        defaultEtapeShouldNotBeFound("libelleetape.in=" + UPDATED_LIBELLEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByLibelleetapeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape is not null
        defaultEtapeShouldBeFound("libelleetape.specified=true");

        // Get all the etapeList where libelleetape is null
        defaultEtapeShouldNotBeFound("libelleetape.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapesByLibelleetapeContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape contains DEFAULT_LIBELLEETAPE
        defaultEtapeShouldBeFound("libelleetape.contains=" + DEFAULT_LIBELLEETAPE);

        // Get all the etapeList where libelleetape contains UPDATED_LIBELLEETAPE
        defaultEtapeShouldNotBeFound("libelleetape.contains=" + UPDATED_LIBELLEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByLibelleetapeNotContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where libelleetape does not contain DEFAULT_LIBELLEETAPE
        defaultEtapeShouldNotBeFound("libelleetape.doesNotContain=" + DEFAULT_LIBELLEETAPE);

        // Get all the etapeList where libelleetape does not contain UPDATED_LIBELLEETAPE
        defaultEtapeShouldBeFound("libelleetape.doesNotContain=" + UPDATED_LIBELLEETAPE);
    }


    @Test
    @Transactional
    public void getAllEtapesByStatutetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape equals to DEFAULT_STATUTETAPE
        defaultEtapeShouldBeFound("statutetape.equals=" + DEFAULT_STATUTETAPE);

        // Get all the etapeList where statutetape equals to UPDATED_STATUTETAPE
        defaultEtapeShouldNotBeFound("statutetape.equals=" + UPDATED_STATUTETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByStatutetapeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape not equals to DEFAULT_STATUTETAPE
        defaultEtapeShouldNotBeFound("statutetape.notEquals=" + DEFAULT_STATUTETAPE);

        // Get all the etapeList where statutetape not equals to UPDATED_STATUTETAPE
        defaultEtapeShouldBeFound("statutetape.notEquals=" + UPDATED_STATUTETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByStatutetapeIsInShouldWork() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape in DEFAULT_STATUTETAPE or UPDATED_STATUTETAPE
        defaultEtapeShouldBeFound("statutetape.in=" + DEFAULT_STATUTETAPE + "," + UPDATED_STATUTETAPE);

        // Get all the etapeList where statutetape equals to UPDATED_STATUTETAPE
        defaultEtapeShouldNotBeFound("statutetape.in=" + UPDATED_STATUTETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByStatutetapeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape is not null
        defaultEtapeShouldBeFound("statutetape.specified=true");

        // Get all the etapeList where statutetape is null
        defaultEtapeShouldNotBeFound("statutetape.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapesByStatutetapeContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape contains DEFAULT_STATUTETAPE
        defaultEtapeShouldBeFound("statutetape.contains=" + DEFAULT_STATUTETAPE);

        // Get all the etapeList where statutetape contains UPDATED_STATUTETAPE
        defaultEtapeShouldNotBeFound("statutetape.contains=" + UPDATED_STATUTETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByStatutetapeNotContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where statutetape does not contain DEFAULT_STATUTETAPE
        defaultEtapeShouldNotBeFound("statutetape.doesNotContain=" + DEFAULT_STATUTETAPE);

        // Get all the etapeList where statutetape does not contain UPDATED_STATUTETAPE
        defaultEtapeShouldBeFound("statutetape.doesNotContain=" + UPDATED_STATUTETAPE);
    }


    @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape equals to DEFAULT_HEUREEXECUTIONETAPE
        defaultEtapeShouldBeFound("heureexecutionetape.equals=" + DEFAULT_HEUREEXECUTIONETAPE);

        // Get all the etapeList where heureexecutionetape equals to UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldNotBeFound("heureexecutionetape.equals=" + UPDATED_HEUREEXECUTIONETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape not equals to DEFAULT_HEUREEXECUTIONETAPE
        defaultEtapeShouldNotBeFound("heureexecutionetape.notEquals=" + DEFAULT_HEUREEXECUTIONETAPE);

        // Get all the etapeList where heureexecutionetape not equals to UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldBeFound("heureexecutionetape.notEquals=" + UPDATED_HEUREEXECUTIONETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeIsInShouldWork() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape in DEFAULT_HEUREEXECUTIONETAPE or UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldBeFound("heureexecutionetape.in=" + DEFAULT_HEUREEXECUTIONETAPE + "," + UPDATED_HEUREEXECUTIONETAPE);

        // Get all the etapeList where heureexecutionetape equals to UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldNotBeFound("heureexecutionetape.in=" + UPDATED_HEUREEXECUTIONETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape is not null
        defaultEtapeShouldBeFound("heureexecutionetape.specified=true");

        // Get all the etapeList where heureexecutionetape is null
        defaultEtapeShouldNotBeFound("heureexecutionetape.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape contains DEFAULT_HEUREEXECUTIONETAPE
        defaultEtapeShouldBeFound("heureexecutionetape.contains=" + DEFAULT_HEUREEXECUTIONETAPE);

        // Get all the etapeList where heureexecutionetape contains UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldNotBeFound("heureexecutionetape.contains=" + UPDATED_HEUREEXECUTIONETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByHeureexecutionetapeNotContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where heureexecutionetape does not contain DEFAULT_HEUREEXECUTIONETAPE
        defaultEtapeShouldNotBeFound("heureexecutionetape.doesNotContain=" + DEFAULT_HEUREEXECUTIONETAPE);

        // Get all the etapeList where heureexecutionetape does not contain UPDATED_HEUREEXECUTIONETAPE
        defaultEtapeShouldBeFound("heureexecutionetape.doesNotContain=" + UPDATED_HEUREEXECUTIONETAPE);
    }


    @Test
    @Transactional
    public void getAllEtapesByCodeetapeIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape equals to DEFAULT_CODEETAPE
        defaultEtapeShouldBeFound("codeetape.equals=" + DEFAULT_CODEETAPE);

        // Get all the etapeList where codeetape equals to UPDATED_CODEETAPE
        defaultEtapeShouldNotBeFound("codeetape.equals=" + UPDATED_CODEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByCodeetapeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape not equals to DEFAULT_CODEETAPE
        defaultEtapeShouldNotBeFound("codeetape.notEquals=" + DEFAULT_CODEETAPE);

        // Get all the etapeList where codeetape not equals to UPDATED_CODEETAPE
        defaultEtapeShouldBeFound("codeetape.notEquals=" + UPDATED_CODEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByCodeetapeIsInShouldWork() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape in DEFAULT_CODEETAPE or UPDATED_CODEETAPE
        defaultEtapeShouldBeFound("codeetape.in=" + DEFAULT_CODEETAPE + "," + UPDATED_CODEETAPE);

        // Get all the etapeList where codeetape equals to UPDATED_CODEETAPE
        defaultEtapeShouldNotBeFound("codeetape.in=" + UPDATED_CODEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByCodeetapeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape is not null
        defaultEtapeShouldBeFound("codeetape.specified=true");

        // Get all the etapeList where codeetape is null
        defaultEtapeShouldNotBeFound("codeetape.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtapesByCodeetapeContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape contains DEFAULT_CODEETAPE
        defaultEtapeShouldBeFound("codeetape.contains=" + DEFAULT_CODEETAPE);

        // Get all the etapeList where codeetape contains UPDATED_CODEETAPE
        defaultEtapeShouldNotBeFound("codeetape.contains=" + UPDATED_CODEETAPE);
    }

    @Test
    @Transactional
    public void getAllEtapesByCodeetapeNotContainsSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);

        // Get all the etapeList where codeetape does not contain DEFAULT_CODEETAPE
        defaultEtapeShouldNotBeFound("codeetape.doesNotContain=" + DEFAULT_CODEETAPE);

        // Get all the etapeList where codeetape does not contain UPDATED_CODEETAPE
        defaultEtapeShouldBeFound("codeetape.doesNotContain=" + UPDATED_CODEETAPE);
    }


    @Test
    @Transactional
    public void getAllEtapesByIdtfjIsEqualToSomething() throws Exception {
        // Initialize the database
        etapeRepository.saveAndFlush(etape);
        Tfj idtfj = TfjResourceIT.createEntity(em);
        em.persist(idtfj);
        em.flush();
        etape.setIdtfj(idtfj);
        etapeRepository.saveAndFlush(etape);
        Long idtfjId = idtfj.getId();

        // Get all the etapeList where idtfj equals to idtfjId
        defaultEtapeShouldBeFound("idtfjId.equals=" + idtfjId);

        // Get all the etapeList where idtfj equals to idtfjId + 1
        defaultEtapeShouldNotBeFound("idtfjId.equals=" + (idtfjId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEtapeShouldBeFound(String filter) throws Exception {
        restEtapeMockMvc.perform(get("/api/etapes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etape.getId().intValue())))
            .andExpect(jsonPath("$.[*].idetape").value(hasItem(DEFAULT_IDETAPE.intValue())))
            .andExpect(jsonPath("$.[*].libelleetape").value(hasItem(DEFAULT_LIBELLEETAPE)))
            .andExpect(jsonPath("$.[*].statutetape").value(hasItem(DEFAULT_STATUTETAPE)))
            .andExpect(jsonPath("$.[*].heureexecutionetape").value(hasItem(DEFAULT_HEUREEXECUTIONETAPE)))
            .andExpect(jsonPath("$.[*].codeetape").value(hasItem(DEFAULT_CODEETAPE)));

        // Check, that the count call also returns 1
        restEtapeMockMvc.perform(get("/api/etapes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEtapeShouldNotBeFound(String filter) throws Exception {
        restEtapeMockMvc.perform(get("/api/etapes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEtapeMockMvc.perform(get("/api/etapes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEtape() throws Exception {
        // Get the etape
        restEtapeMockMvc.perform(get("/api/etapes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtape() throws Exception {
        // Initialize the database
        etapeService.save(etape);

        int databaseSizeBeforeUpdate = etapeRepository.findAll().size();

        // Update the etape
        Etape updatedEtape = etapeRepository.findById(etape.getId()).get();
        // Disconnect from session so that the updates on updatedEtape are not directly saved in db
        em.detach(updatedEtape);
        updatedEtape
            .idetape(UPDATED_IDETAPE)
            .libelleetape(UPDATED_LIBELLEETAPE)
            .statutetape(UPDATED_STATUTETAPE)
            .heureexecutionetape(UPDATED_HEUREEXECUTIONETAPE)
            .codeetape(UPDATED_CODEETAPE);

        restEtapeMockMvc.perform(put("/api/etapes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtape)))
            .andExpect(status().isOk());

        // Validate the Etape in the database
        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeUpdate);
        Etape testEtape = etapeList.get(etapeList.size() - 1);
        assertThat(testEtape.getIdetape()).isEqualTo(UPDATED_IDETAPE);
        assertThat(testEtape.getLibelleetape()).isEqualTo(UPDATED_LIBELLEETAPE);
        assertThat(testEtape.getStatutetape()).isEqualTo(UPDATED_STATUTETAPE);
        assertThat(testEtape.getHeureexecutionetape()).isEqualTo(UPDATED_HEUREEXECUTIONETAPE);
        assertThat(testEtape.getCodeetape()).isEqualTo(UPDATED_CODEETAPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtape() throws Exception {
        int databaseSizeBeforeUpdate = etapeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeMockMvc.perform(put("/api/etapes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etape)))
            .andExpect(status().isBadRequest());

        // Validate the Etape in the database
        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtape() throws Exception {
        // Initialize the database
        etapeService.save(etape);

        int databaseSizeBeforeDelete = etapeRepository.findAll().size();

        // Delete the etape
        restEtapeMockMvc.perform(delete("/api/etapes/{id}", etape.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etape> etapeList = etapeRepository.findAll();
        assertThat(etapeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
