package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TauxDevise;
import com.projet.hpd.repository.TauxDeviseRepository;
import com.projet.hpd.service.TauxDeviseService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TauxDeviseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TauxDeviseResourceIT {

    private static final String DEFAULT_SENS_TAUX_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_SENS_TAUX_DEVISE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private TauxDeviseRepository tauxDeviseRepository;

    @Autowired
    private TauxDeviseService tauxDeviseService;

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

    private MockMvc restTauxDeviseMockMvc;

    private TauxDevise tauxDevise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TauxDeviseResource tauxDeviseResource = new TauxDeviseResource(tauxDeviseService);
        this.restTauxDeviseMockMvc = MockMvcBuilders.standaloneSetup(tauxDeviseResource)
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
    public static TauxDevise createEntity(EntityManager em) {
        TauxDevise tauxDevise = new TauxDevise()
            .sensTauxDevise(DEFAULT_SENS_TAUX_DEVISE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return tauxDevise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TauxDevise createUpdatedEntity(EntityManager em) {
        TauxDevise tauxDevise = new TauxDevise()
            .sensTauxDevise(UPDATED_SENS_TAUX_DEVISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return tauxDevise;
    }

    @BeforeEach
    public void initTest() {
        tauxDevise = createEntity(em);
    }

    @Test
    @Transactional
    public void createTauxDevise() throws Exception {
        int databaseSizeBeforeCreate = tauxDeviseRepository.findAll().size();

        // Create the TauxDevise
        restTauxDeviseMockMvc.perform(post("/api/taux-devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tauxDevise)))
            .andExpect(status().isCreated());

        // Validate the TauxDevise in the database
        List<TauxDevise> tauxDeviseList = tauxDeviseRepository.findAll();
        assertThat(tauxDeviseList).hasSize(databaseSizeBeforeCreate + 1);
        TauxDevise testTauxDevise = tauxDeviseList.get(tauxDeviseList.size() - 1);
        assertThat(testTauxDevise.getSensTauxDevise()).isEqualTo(DEFAULT_SENS_TAUX_DEVISE);
        assertThat(testTauxDevise.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTauxDevise.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTauxDevise.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTauxDevise.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTauxDevise.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTauxDeviseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tauxDeviseRepository.findAll().size();

        // Create the TauxDevise with an existing ID
        tauxDevise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTauxDeviseMockMvc.perform(post("/api/taux-devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tauxDevise)))
            .andExpect(status().isBadRequest());

        // Validate the TauxDevise in the database
        List<TauxDevise> tauxDeviseList = tauxDeviseRepository.findAll();
        assertThat(tauxDeviseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTauxDevises() throws Exception {
        // Initialize the database
        tauxDeviseRepository.saveAndFlush(tauxDevise);

        // Get all the tauxDeviseList
        restTauxDeviseMockMvc.perform(get("/api/taux-devises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tauxDevise.getId().intValue())))
            .andExpect(jsonPath("$.[*].sensTauxDevise").value(hasItem(DEFAULT_SENS_TAUX_DEVISE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTauxDevise() throws Exception {
        // Initialize the database
        tauxDeviseRepository.saveAndFlush(tauxDevise);

        // Get the tauxDevise
        restTauxDeviseMockMvc.perform(get("/api/taux-devises/{id}", tauxDevise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tauxDevise.getId().intValue()))
            .andExpect(jsonPath("$.sensTauxDevise").value(DEFAULT_SENS_TAUX_DEVISE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTauxDevise() throws Exception {
        // Get the tauxDevise
        restTauxDeviseMockMvc.perform(get("/api/taux-devises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTauxDevise() throws Exception {
        // Initialize the database
        tauxDeviseService.save(tauxDevise);

        int databaseSizeBeforeUpdate = tauxDeviseRepository.findAll().size();

        // Update the tauxDevise
        TauxDevise updatedTauxDevise = tauxDeviseRepository.findById(tauxDevise.getId()).get();
        // Disconnect from session so that the updates on updatedTauxDevise are not directly saved in db
        em.detach(updatedTauxDevise);
        updatedTauxDevise
            .sensTauxDevise(UPDATED_SENS_TAUX_DEVISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTauxDeviseMockMvc.perform(put("/api/taux-devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTauxDevise)))
            .andExpect(status().isOk());

        // Validate the TauxDevise in the database
        List<TauxDevise> tauxDeviseList = tauxDeviseRepository.findAll();
        assertThat(tauxDeviseList).hasSize(databaseSizeBeforeUpdate);
        TauxDevise testTauxDevise = tauxDeviseList.get(tauxDeviseList.size() - 1);
        assertThat(testTauxDevise.getSensTauxDevise()).isEqualTo(UPDATED_SENS_TAUX_DEVISE);
        assertThat(testTauxDevise.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTauxDevise.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTauxDevise.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTauxDevise.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTauxDevise.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTauxDevise() throws Exception {
        int databaseSizeBeforeUpdate = tauxDeviseRepository.findAll().size();

        // Create the TauxDevise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTauxDeviseMockMvc.perform(put("/api/taux-devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tauxDevise)))
            .andExpect(status().isBadRequest());

        // Validate the TauxDevise in the database
        List<TauxDevise> tauxDeviseList = tauxDeviseRepository.findAll();
        assertThat(tauxDeviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTauxDevise() throws Exception {
        // Initialize the database
        tauxDeviseService.save(tauxDevise);

        int databaseSizeBeforeDelete = tauxDeviseRepository.findAll().size();

        // Delete the tauxDevise
        restTauxDeviseMockMvc.perform(delete("/api/taux-devises/{id}", tauxDevise.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TauxDevise> tauxDeviseList = tauxDeviseRepository.findAll();
        assertThat(tauxDeviseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
