package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Evenement;
import com.projet.hpd.repository.EvenementRepository;
import com.projet.hpd.service.EvenementService;
import com.projet.hpd.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EvenementResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EvenementResourceIT {

    private static final String DEFAULT_HEURE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CREATED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CREATED = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Integer DEFAULT_ID_ENTITY = 1;
    private static final Integer UPDATED_ID_ENTITY = 2;

    private static final String DEFAULT_ENTITY_TOUCHER = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TOUCHER = "BBBBBBBBBB";

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEvenementMockMvc;

    private Evenement evenement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvenementResource evenementResource = new EvenementResource(evenementService);
        this.restEvenementMockMvc = MockMvcBuilders.standaloneSetup(evenementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evenement createEntity(EntityManager em) {
        Evenement evenement = new Evenement()
            .heure(DEFAULT_HEURE)
            .eventName(DEFAULT_EVENT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .idEntity(DEFAULT_ID_ENTITY)
            .entityToucher(DEFAULT_ENTITY_TOUCHER);
        return evenement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evenement createUpdatedEntity(EntityManager em) {
        Evenement evenement = new Evenement()
            .heure(UPDATED_HEURE)
            .eventName(UPDATED_EVENT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .idEntity(UPDATED_ID_ENTITY)
            .entityToucher(UPDATED_ENTITY_TOUCHER);
        return evenement;
    }

    @BeforeEach
    public void initTest() {
        evenement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvenement() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isCreated());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate + 1);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testEvenement.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvenement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvenement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEvenement.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEvenement.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEvenement.getIdEntity()).isEqualTo(DEFAULT_ID_ENTITY);
        assertThat(testEvenement.getEntityToucher()).isEqualTo(DEFAULT_ENTITY_TOUCHER);
    }

    @Test
    @Transactional
    public void createEvenementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement with an existing ID
        evenement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEvenements() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList
        restEvenementMockMvc.perform(get("/api/evenements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenement.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE)))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED)))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].idEntity").value(hasItem(DEFAULT_ID_ENTITY)))
            .andExpect(jsonPath("$.[*].entityToucher").value(hasItem(DEFAULT_ENTITY_TOUCHER)));
    }
    
    @Test
    @Transactional
    public void getEvenement() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", evenement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evenement.getId().intValue()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.idEntity").value(DEFAULT_ID_ENTITY))
            .andExpect(jsonPath("$.entityToucher").value(DEFAULT_ENTITY_TOUCHER));
    }

    @Test
    @Transactional
    public void getNonExistingEvenement() throws Exception {
        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvenement() throws Exception {
        // Initialize the database
        evenementService.save(evenement);

        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Update the evenement
        Evenement updatedEvenement = evenementRepository.findById(evenement.getId()).get();
        // Disconnect from session so that the updates on updatedEvenement are not directly saved in db
        em.detach(updatedEvenement);
        updatedEvenement
            .heure(UPDATED_HEURE)
            .eventName(UPDATED_EVENT_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .idEntity(UPDATED_ID_ENTITY)
            .entityToucher(UPDATED_ENTITY_TOUCHER);

        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvenement)))
            .andExpect(status().isOk());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testEvenement.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvenement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvenement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEvenement.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEvenement.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEvenement.getIdEntity()).isEqualTo(UPDATED_ID_ENTITY);
        assertThat(testEvenement.getEntityToucher()).isEqualTo(UPDATED_ENTITY_TOUCHER);
    }

    @Test
    @Transactional
    public void updateNonExistingEvenement() throws Exception {
        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Create the Evenement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvenement() throws Exception {
        // Initialize the database
        evenementService.save(evenement);

        int databaseSizeBeforeDelete = evenementRepository.findAll().size();

        // Delete the evenement
        restEvenementMockMvc.perform(delete("/api/evenements/{id}", evenement.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
