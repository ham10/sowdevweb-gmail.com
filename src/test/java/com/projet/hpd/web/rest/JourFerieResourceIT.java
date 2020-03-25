package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.JourFerie;
import com.projet.hpd.repository.JourFerieRepository;
import com.projet.hpd.service.JourFerieService;
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
 * Integration tests for the {@link JourFerieResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class JourFerieResourceIT {

    private static final String DEFAULT_LIBELLE_JOUR_FERIE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_JOUR_FERIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_JOUR_FERIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_JOUR_FERIE = LocalDate.now(ZoneId.systemDefault());

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
    private JourFerieRepository jourFerieRepository;

    @Autowired
    private JourFerieService jourFerieService;

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

    private MockMvc restJourFerieMockMvc;

    private JourFerie jourFerie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourFerieResource jourFerieResource = new JourFerieResource(jourFerieService);
        this.restJourFerieMockMvc = MockMvcBuilders.standaloneSetup(jourFerieResource)
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
    public static JourFerie createEntity(EntityManager em) {
        JourFerie jourFerie = new JourFerie()
            .libelleJourFerie(DEFAULT_LIBELLE_JOUR_FERIE)
            .dateJourFerie(DEFAULT_DATE_JOUR_FERIE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return jourFerie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourFerie createUpdatedEntity(EntityManager em) {
        JourFerie jourFerie = new JourFerie()
            .libelleJourFerie(UPDATED_LIBELLE_JOUR_FERIE)
            .dateJourFerie(UPDATED_DATE_JOUR_FERIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return jourFerie;
    }

    @BeforeEach
    public void initTest() {
        jourFerie = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourFerie() throws Exception {
        int databaseSizeBeforeCreate = jourFerieRepository.findAll().size();

        // Create the JourFerie
        restJourFerieMockMvc.perform(post("/api/jour-feries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jourFerie)))
            .andExpect(status().isCreated());

        // Validate the JourFerie in the database
        List<JourFerie> jourFerieList = jourFerieRepository.findAll();
        assertThat(jourFerieList).hasSize(databaseSizeBeforeCreate + 1);
        JourFerie testJourFerie = jourFerieList.get(jourFerieList.size() - 1);
        assertThat(testJourFerie.getLibelleJourFerie()).isEqualTo(DEFAULT_LIBELLE_JOUR_FERIE);
        assertThat(testJourFerie.getDateJourFerie()).isEqualTo(DEFAULT_DATE_JOUR_FERIE);
        assertThat(testJourFerie.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testJourFerie.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testJourFerie.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testJourFerie.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testJourFerie.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createJourFerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jourFerieRepository.findAll().size();

        // Create the JourFerie with an existing ID
        jourFerie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourFerieMockMvc.perform(post("/api/jour-feries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jourFerie)))
            .andExpect(status().isBadRequest());

        // Validate the JourFerie in the database
        List<JourFerie> jourFerieList = jourFerieRepository.findAll();
        assertThat(jourFerieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJourFeries() throws Exception {
        // Initialize the database
        jourFerieRepository.saveAndFlush(jourFerie);

        // Get all the jourFerieList
        restJourFerieMockMvc.perform(get("/api/jour-feries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jourFerie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleJourFerie").value(hasItem(DEFAULT_LIBELLE_JOUR_FERIE)))
            .andExpect(jsonPath("$.[*].dateJourFerie").value(hasItem(DEFAULT_DATE_JOUR_FERIE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getJourFerie() throws Exception {
        // Initialize the database
        jourFerieRepository.saveAndFlush(jourFerie);

        // Get the jourFerie
        restJourFerieMockMvc.perform(get("/api/jour-feries/{id}", jourFerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jourFerie.getId().intValue()))
            .andExpect(jsonPath("$.libelleJourFerie").value(DEFAULT_LIBELLE_JOUR_FERIE))
            .andExpect(jsonPath("$.dateJourFerie").value(DEFAULT_DATE_JOUR_FERIE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJourFerie() throws Exception {
        // Get the jourFerie
        restJourFerieMockMvc.perform(get("/api/jour-feries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourFerie() throws Exception {
        // Initialize the database
        jourFerieService.save(jourFerie);

        int databaseSizeBeforeUpdate = jourFerieRepository.findAll().size();

        // Update the jourFerie
        JourFerie updatedJourFerie = jourFerieRepository.findById(jourFerie.getId()).get();
        // Disconnect from session so that the updates on updatedJourFerie are not directly saved in db
        em.detach(updatedJourFerie);
        updatedJourFerie
            .libelleJourFerie(UPDATED_LIBELLE_JOUR_FERIE)
            .dateJourFerie(UPDATED_DATE_JOUR_FERIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restJourFerieMockMvc.perform(put("/api/jour-feries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJourFerie)))
            .andExpect(status().isOk());

        // Validate the JourFerie in the database
        List<JourFerie> jourFerieList = jourFerieRepository.findAll();
        assertThat(jourFerieList).hasSize(databaseSizeBeforeUpdate);
        JourFerie testJourFerie = jourFerieList.get(jourFerieList.size() - 1);
        assertThat(testJourFerie.getLibelleJourFerie()).isEqualTo(UPDATED_LIBELLE_JOUR_FERIE);
        assertThat(testJourFerie.getDateJourFerie()).isEqualTo(UPDATED_DATE_JOUR_FERIE);
        assertThat(testJourFerie.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testJourFerie.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testJourFerie.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testJourFerie.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testJourFerie.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingJourFerie() throws Exception {
        int databaseSizeBeforeUpdate = jourFerieRepository.findAll().size();

        // Create the JourFerie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourFerieMockMvc.perform(put("/api/jour-feries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jourFerie)))
            .andExpect(status().isBadRequest());

        // Validate the JourFerie in the database
        List<JourFerie> jourFerieList = jourFerieRepository.findAll();
        assertThat(jourFerieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJourFerie() throws Exception {
        // Initialize the database
        jourFerieService.save(jourFerie);

        int databaseSizeBeforeDelete = jourFerieRepository.findAll().size();

        // Delete the jourFerie
        restJourFerieMockMvc.perform(delete("/api/jour-feries/{id}", jourFerie.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourFerie> jourFerieList = jourFerieRepository.findAll();
        assertThat(jourFerieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
