package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Fonctionnalite;
import com.projet.hpd.repository.FonctionnaliteRepository;
import com.projet.hpd.service.FonctionnaliteService;
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
 * Integration tests for the {@link FonctionnaliteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FonctionnaliteResourceIT {

    private static final String DEFAULT_LIBELLE_FONCTIONNALITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FONCTIONNALITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_FONCTIONNALITE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_FONCTIONNALITE = "BBBBBBBBBB";

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
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private FonctionnaliteService fonctionnaliteService;

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

    private MockMvc restFonctionnaliteMockMvc;

    private Fonctionnalite fonctionnalite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FonctionnaliteResource fonctionnaliteResource = new FonctionnaliteResource(fonctionnaliteService);
        this.restFonctionnaliteMockMvc = MockMvcBuilders.standaloneSetup(fonctionnaliteResource)
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
    public static Fonctionnalite createEntity(EntityManager em) {
        Fonctionnalite fonctionnalite = new Fonctionnalite()
            .libelleFonctionnalite(DEFAULT_LIBELLE_FONCTIONNALITE)
            .descriptionFonctionnalite(DEFAULT_DESCRIPTION_FONCTIONNALITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return fonctionnalite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fonctionnalite createUpdatedEntity(EntityManager em) {
        Fonctionnalite fonctionnalite = new Fonctionnalite()
            .libelleFonctionnalite(UPDATED_LIBELLE_FONCTIONNALITE)
            .descriptionFonctionnalite(UPDATED_DESCRIPTION_FONCTIONNALITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return fonctionnalite;
    }

    @BeforeEach
    public void initTest() {
        fonctionnalite = createEntity(em);
    }

    @Test
    @Transactional
    public void createFonctionnalite() throws Exception {
        int databaseSizeBeforeCreate = fonctionnaliteRepository.findAll().size();

        // Create the Fonctionnalite
        restFonctionnaliteMockMvc.perform(post("/api/fonctionnalites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonctionnalite)))
            .andExpect(status().isCreated());

        // Validate the Fonctionnalite in the database
        List<Fonctionnalite> fonctionnaliteList = fonctionnaliteRepository.findAll();
        assertThat(fonctionnaliteList).hasSize(databaseSizeBeforeCreate + 1);
        Fonctionnalite testFonctionnalite = fonctionnaliteList.get(fonctionnaliteList.size() - 1);
        assertThat(testFonctionnalite.getLibelleFonctionnalite()).isEqualTo(DEFAULT_LIBELLE_FONCTIONNALITE);
        assertThat(testFonctionnalite.getDescriptionFonctionnalite()).isEqualTo(DEFAULT_DESCRIPTION_FONCTIONNALITE);
        assertThat(testFonctionnalite.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFonctionnalite.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFonctionnalite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFonctionnalite.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFonctionnalite.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFonctionnaliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fonctionnaliteRepository.findAll().size();

        // Create the Fonctionnalite with an existing ID
        fonctionnalite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionnaliteMockMvc.perform(post("/api/fonctionnalites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonctionnalite)))
            .andExpect(status().isBadRequest());

        // Validate the Fonctionnalite in the database
        List<Fonctionnalite> fonctionnaliteList = fonctionnaliteRepository.findAll();
        assertThat(fonctionnaliteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFonctionnalites() throws Exception {
        // Initialize the database
        fonctionnaliteRepository.saveAndFlush(fonctionnalite);

        // Get all the fonctionnaliteList
        restFonctionnaliteMockMvc.perform(get("/api/fonctionnalites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonctionnalite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleFonctionnalite").value(hasItem(DEFAULT_LIBELLE_FONCTIONNALITE)))
            .andExpect(jsonPath("$.[*].descriptionFonctionnalite").value(hasItem(DEFAULT_DESCRIPTION_FONCTIONNALITE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getFonctionnalite() throws Exception {
        // Initialize the database
        fonctionnaliteRepository.saveAndFlush(fonctionnalite);

        // Get the fonctionnalite
        restFonctionnaliteMockMvc.perform(get("/api/fonctionnalites/{id}", fonctionnalite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fonctionnalite.getId().intValue()))
            .andExpect(jsonPath("$.libelleFonctionnalite").value(DEFAULT_LIBELLE_FONCTIONNALITE))
            .andExpect(jsonPath("$.descriptionFonctionnalite").value(DEFAULT_DESCRIPTION_FONCTIONNALITE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFonctionnalite() throws Exception {
        // Get the fonctionnalite
        restFonctionnaliteMockMvc.perform(get("/api/fonctionnalites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFonctionnalite() throws Exception {
        // Initialize the database
        fonctionnaliteService.save(fonctionnalite);

        int databaseSizeBeforeUpdate = fonctionnaliteRepository.findAll().size();

        // Update the fonctionnalite
        Fonctionnalite updatedFonctionnalite = fonctionnaliteRepository.findById(fonctionnalite.getId()).get();
        // Disconnect from session so that the updates on updatedFonctionnalite are not directly saved in db
        em.detach(updatedFonctionnalite);
        updatedFonctionnalite
            .libelleFonctionnalite(UPDATED_LIBELLE_FONCTIONNALITE)
            .descriptionFonctionnalite(UPDATED_DESCRIPTION_FONCTIONNALITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFonctionnaliteMockMvc.perform(put("/api/fonctionnalites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFonctionnalite)))
            .andExpect(status().isOk());

        // Validate the Fonctionnalite in the database
        List<Fonctionnalite> fonctionnaliteList = fonctionnaliteRepository.findAll();
        assertThat(fonctionnaliteList).hasSize(databaseSizeBeforeUpdate);
        Fonctionnalite testFonctionnalite = fonctionnaliteList.get(fonctionnaliteList.size() - 1);
        assertThat(testFonctionnalite.getLibelleFonctionnalite()).isEqualTo(UPDATED_LIBELLE_FONCTIONNALITE);
        assertThat(testFonctionnalite.getDescriptionFonctionnalite()).isEqualTo(UPDATED_DESCRIPTION_FONCTIONNALITE);
        assertThat(testFonctionnalite.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFonctionnalite.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFonctionnalite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFonctionnalite.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFonctionnalite.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFonctionnalite() throws Exception {
        int databaseSizeBeforeUpdate = fonctionnaliteRepository.findAll().size();

        // Create the Fonctionnalite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionnaliteMockMvc.perform(put("/api/fonctionnalites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonctionnalite)))
            .andExpect(status().isBadRequest());

        // Validate the Fonctionnalite in the database
        List<Fonctionnalite> fonctionnaliteList = fonctionnaliteRepository.findAll();
        assertThat(fonctionnaliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFonctionnalite() throws Exception {
        // Initialize the database
        fonctionnaliteService.save(fonctionnalite);

        int databaseSizeBeforeDelete = fonctionnaliteRepository.findAll().size();

        // Delete the fonctionnalite
        restFonctionnaliteMockMvc.perform(delete("/api/fonctionnalites/{id}", fonctionnalite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fonctionnalite> fonctionnaliteList = fonctionnaliteRepository.findAll();
        assertThat(fonctionnaliteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
