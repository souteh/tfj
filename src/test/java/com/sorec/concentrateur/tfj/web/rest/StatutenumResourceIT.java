package com.sorec.concentrateur.tfj.web.rest;

import com.sorec.concentrateur.tfj.TfjApp;
import com.sorec.concentrateur.tfj.domain.Statutenum;
import com.sorec.concentrateur.tfj.repository.StatutenumRepository;
import com.sorec.concentrateur.tfj.service.StatutenumService;
import com.sorec.concentrateur.tfj.service.dto.StatutenumCriteria;
import com.sorec.concentrateur.tfj.service.StatutenumQueryService;

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
 * Integration tests for the {@link StatutenumResource} REST controller.
 */
@SpringBootTest(classes = TfjApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatutenumResourceIT {

    private static final Long DEFAULT_IDSTATUT = 1L;
    private static final Long UPDATED_IDSTATUT = 2L;
    private static final Long SMALLER_IDSTATUT = 1L - 1L;

    private static final String DEFAULT_LIBELLESTATUT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLESTATUT = "BBBBBBBBBB";

    private static final String DEFAULT_CODESTATUT = "AAAAAAAAAA";
    private static final String UPDATED_CODESTATUT = "BBBBBBBBBB";

    @Autowired
    private StatutenumRepository statutenumRepository;

    @Autowired
    private StatutenumService statutenumService;

    @Autowired
    private StatutenumQueryService statutenumQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatutenumMockMvc;

    private Statutenum statutenum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statutenum createEntity(EntityManager em) {
        Statutenum statutenum = new Statutenum()
            .idstatut(DEFAULT_IDSTATUT)
            .libellestatut(DEFAULT_LIBELLESTATUT)
            .codestatut(DEFAULT_CODESTATUT);
        return statutenum;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statutenum createUpdatedEntity(EntityManager em) {
        Statutenum statutenum = new Statutenum()
            .idstatut(UPDATED_IDSTATUT)
            .libellestatut(UPDATED_LIBELLESTATUT)
            .codestatut(UPDATED_CODESTATUT);
        return statutenum;
    }

    @BeforeEach
    public void initTest() {
        statutenum = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatutenum() throws Exception {
        int databaseSizeBeforeCreate = statutenumRepository.findAll().size();
        // Create the Statutenum
        restStatutenumMockMvc.perform(post("/api/statutenums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutenum)))
            .andExpect(status().isCreated());

        // Validate the Statutenum in the database
        List<Statutenum> statutenumList = statutenumRepository.findAll();
        assertThat(statutenumList).hasSize(databaseSizeBeforeCreate + 1);
        Statutenum testStatutenum = statutenumList.get(statutenumList.size() - 1);
        assertThat(testStatutenum.getIdstatut()).isEqualTo(DEFAULT_IDSTATUT);
        assertThat(testStatutenum.getLibellestatut()).isEqualTo(DEFAULT_LIBELLESTATUT);
        assertThat(testStatutenum.getCodestatut()).isEqualTo(DEFAULT_CODESTATUT);
    }

    @Test
    @Transactional
    public void createStatutenumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statutenumRepository.findAll().size();

        // Create the Statutenum with an existing ID
        statutenum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatutenumMockMvc.perform(post("/api/statutenums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutenum)))
            .andExpect(status().isBadRequest());

        // Validate the Statutenum in the database
        List<Statutenum> statutenumList = statutenumRepository.findAll();
        assertThat(statutenumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatutenums() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList
        restStatutenumMockMvc.perform(get("/api/statutenums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statutenum.getId().intValue())))
            .andExpect(jsonPath("$.[*].idstatut").value(hasItem(DEFAULT_IDSTATUT.intValue())))
            .andExpect(jsonPath("$.[*].libellestatut").value(hasItem(DEFAULT_LIBELLESTATUT)))
            .andExpect(jsonPath("$.[*].codestatut").value(hasItem(DEFAULT_CODESTATUT)));
    }
    
    @Test
    @Transactional
    public void getStatutenum() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get the statutenum
        restStatutenumMockMvc.perform(get("/api/statutenums/{id}", statutenum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statutenum.getId().intValue()))
            .andExpect(jsonPath("$.idstatut").value(DEFAULT_IDSTATUT.intValue()))
            .andExpect(jsonPath("$.libellestatut").value(DEFAULT_LIBELLESTATUT))
            .andExpect(jsonPath("$.codestatut").value(DEFAULT_CODESTATUT));
    }


    @Test
    @Transactional
    public void getStatutenumsByIdFiltering() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        Long id = statutenum.getId();

        defaultStatutenumShouldBeFound("id.equals=" + id);
        defaultStatutenumShouldNotBeFound("id.notEquals=" + id);

        defaultStatutenumShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatutenumShouldNotBeFound("id.greaterThan=" + id);

        defaultStatutenumShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatutenumShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut equals to DEFAULT_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.equals=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut equals to UPDATED_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.equals=" + UPDATED_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut not equals to DEFAULT_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.notEquals=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut not equals to UPDATED_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.notEquals=" + UPDATED_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsInShouldWork() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut in DEFAULT_IDSTATUT or UPDATED_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.in=" + DEFAULT_IDSTATUT + "," + UPDATED_IDSTATUT);

        // Get all the statutenumList where idstatut equals to UPDATED_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.in=" + UPDATED_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut is not null
        defaultStatutenumShouldBeFound("idstatut.specified=true");

        // Get all the statutenumList where idstatut is null
        defaultStatutenumShouldNotBeFound("idstatut.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut is greater than or equal to DEFAULT_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.greaterThanOrEqual=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut is greater than or equal to UPDATED_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.greaterThanOrEqual=" + UPDATED_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut is less than or equal to DEFAULT_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.lessThanOrEqual=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut is less than or equal to SMALLER_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.lessThanOrEqual=" + SMALLER_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsLessThanSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut is less than DEFAULT_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.lessThan=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut is less than UPDATED_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.lessThan=" + UPDATED_IDSTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByIdstatutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where idstatut is greater than DEFAULT_IDSTATUT
        defaultStatutenumShouldNotBeFound("idstatut.greaterThan=" + DEFAULT_IDSTATUT);

        // Get all the statutenumList where idstatut is greater than SMALLER_IDSTATUT
        defaultStatutenumShouldBeFound("idstatut.greaterThan=" + SMALLER_IDSTATUT);
    }


    @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutIsEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut equals to DEFAULT_LIBELLESTATUT
        defaultStatutenumShouldBeFound("libellestatut.equals=" + DEFAULT_LIBELLESTATUT);

        // Get all the statutenumList where libellestatut equals to UPDATED_LIBELLESTATUT
        defaultStatutenumShouldNotBeFound("libellestatut.equals=" + UPDATED_LIBELLESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut not equals to DEFAULT_LIBELLESTATUT
        defaultStatutenumShouldNotBeFound("libellestatut.notEquals=" + DEFAULT_LIBELLESTATUT);

        // Get all the statutenumList where libellestatut not equals to UPDATED_LIBELLESTATUT
        defaultStatutenumShouldBeFound("libellestatut.notEquals=" + UPDATED_LIBELLESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutIsInShouldWork() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut in DEFAULT_LIBELLESTATUT or UPDATED_LIBELLESTATUT
        defaultStatutenumShouldBeFound("libellestatut.in=" + DEFAULT_LIBELLESTATUT + "," + UPDATED_LIBELLESTATUT);

        // Get all the statutenumList where libellestatut equals to UPDATED_LIBELLESTATUT
        defaultStatutenumShouldNotBeFound("libellestatut.in=" + UPDATED_LIBELLESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut is not null
        defaultStatutenumShouldBeFound("libellestatut.specified=true");

        // Get all the statutenumList where libellestatut is null
        defaultStatutenumShouldNotBeFound("libellestatut.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutContainsSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut contains DEFAULT_LIBELLESTATUT
        defaultStatutenumShouldBeFound("libellestatut.contains=" + DEFAULT_LIBELLESTATUT);

        // Get all the statutenumList where libellestatut contains UPDATED_LIBELLESTATUT
        defaultStatutenumShouldNotBeFound("libellestatut.contains=" + UPDATED_LIBELLESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByLibellestatutNotContainsSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where libellestatut does not contain DEFAULT_LIBELLESTATUT
        defaultStatutenumShouldNotBeFound("libellestatut.doesNotContain=" + DEFAULT_LIBELLESTATUT);

        // Get all the statutenumList where libellestatut does not contain UPDATED_LIBELLESTATUT
        defaultStatutenumShouldBeFound("libellestatut.doesNotContain=" + UPDATED_LIBELLESTATUT);
    }


    @Test
    @Transactional
    public void getAllStatutenumsByCodestatutIsEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut equals to DEFAULT_CODESTATUT
        defaultStatutenumShouldBeFound("codestatut.equals=" + DEFAULT_CODESTATUT);

        // Get all the statutenumList where codestatut equals to UPDATED_CODESTATUT
        defaultStatutenumShouldNotBeFound("codestatut.equals=" + UPDATED_CODESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByCodestatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut not equals to DEFAULT_CODESTATUT
        defaultStatutenumShouldNotBeFound("codestatut.notEquals=" + DEFAULT_CODESTATUT);

        // Get all the statutenumList where codestatut not equals to UPDATED_CODESTATUT
        defaultStatutenumShouldBeFound("codestatut.notEquals=" + UPDATED_CODESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByCodestatutIsInShouldWork() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut in DEFAULT_CODESTATUT or UPDATED_CODESTATUT
        defaultStatutenumShouldBeFound("codestatut.in=" + DEFAULT_CODESTATUT + "," + UPDATED_CODESTATUT);

        // Get all the statutenumList where codestatut equals to UPDATED_CODESTATUT
        defaultStatutenumShouldNotBeFound("codestatut.in=" + UPDATED_CODESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByCodestatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut is not null
        defaultStatutenumShouldBeFound("codestatut.specified=true");

        // Get all the statutenumList where codestatut is null
        defaultStatutenumShouldNotBeFound("codestatut.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatutenumsByCodestatutContainsSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut contains DEFAULT_CODESTATUT
        defaultStatutenumShouldBeFound("codestatut.contains=" + DEFAULT_CODESTATUT);

        // Get all the statutenumList where codestatut contains UPDATED_CODESTATUT
        defaultStatutenumShouldNotBeFound("codestatut.contains=" + UPDATED_CODESTATUT);
    }

    @Test
    @Transactional
    public void getAllStatutenumsByCodestatutNotContainsSomething() throws Exception {
        // Initialize the database
        statutenumRepository.saveAndFlush(statutenum);

        // Get all the statutenumList where codestatut does not contain DEFAULT_CODESTATUT
        defaultStatutenumShouldNotBeFound("codestatut.doesNotContain=" + DEFAULT_CODESTATUT);

        // Get all the statutenumList where codestatut does not contain UPDATED_CODESTATUT
        defaultStatutenumShouldBeFound("codestatut.doesNotContain=" + UPDATED_CODESTATUT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatutenumShouldBeFound(String filter) throws Exception {
        restStatutenumMockMvc.perform(get("/api/statutenums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statutenum.getId().intValue())))
            .andExpect(jsonPath("$.[*].idstatut").value(hasItem(DEFAULT_IDSTATUT.intValue())))
            .andExpect(jsonPath("$.[*].libellestatut").value(hasItem(DEFAULT_LIBELLESTATUT)))
            .andExpect(jsonPath("$.[*].codestatut").value(hasItem(DEFAULT_CODESTATUT)));

        // Check, that the count call also returns 1
        restStatutenumMockMvc.perform(get("/api/statutenums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatutenumShouldNotBeFound(String filter) throws Exception {
        restStatutenumMockMvc.perform(get("/api/statutenums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatutenumMockMvc.perform(get("/api/statutenums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatutenum() throws Exception {
        // Get the statutenum
        restStatutenumMockMvc.perform(get("/api/statutenums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatutenum() throws Exception {
        // Initialize the database
        statutenumService.save(statutenum);

        int databaseSizeBeforeUpdate = statutenumRepository.findAll().size();

        // Update the statutenum
        Statutenum updatedStatutenum = statutenumRepository.findById(statutenum.getId()).get();
        // Disconnect from session so that the updates on updatedStatutenum are not directly saved in db
        em.detach(updatedStatutenum);
        updatedStatutenum
            .idstatut(UPDATED_IDSTATUT)
            .libellestatut(UPDATED_LIBELLESTATUT)
            .codestatut(UPDATED_CODESTATUT);

        restStatutenumMockMvc.perform(put("/api/statutenums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatutenum)))
            .andExpect(status().isOk());

        // Validate the Statutenum in the database
        List<Statutenum> statutenumList = statutenumRepository.findAll();
        assertThat(statutenumList).hasSize(databaseSizeBeforeUpdate);
        Statutenum testStatutenum = statutenumList.get(statutenumList.size() - 1);
        assertThat(testStatutenum.getIdstatut()).isEqualTo(UPDATED_IDSTATUT);
        assertThat(testStatutenum.getLibellestatut()).isEqualTo(UPDATED_LIBELLESTATUT);
        assertThat(testStatutenum.getCodestatut()).isEqualTo(UPDATED_CODESTATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingStatutenum() throws Exception {
        int databaseSizeBeforeUpdate = statutenumRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatutenumMockMvc.perform(put("/api/statutenums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statutenum)))
            .andExpect(status().isBadRequest());

        // Validate the Statutenum in the database
        List<Statutenum> statutenumList = statutenumRepository.findAll();
        assertThat(statutenumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatutenum() throws Exception {
        // Initialize the database
        statutenumService.save(statutenum);

        int databaseSizeBeforeDelete = statutenumRepository.findAll().size();

        // Delete the statutenum
        restStatutenumMockMvc.perform(delete("/api/statutenums/{id}", statutenum.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statutenum> statutenumList = statutenumRepository.findAll();
        assertThat(statutenumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
