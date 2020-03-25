package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Civilite;
import com.projet.hpd.repository.CiviliteRepository;
import com.projet.hpd.service.CiviliteService;
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
 * Integration tests for the {@link CiviliteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CiviliteResourceIT {

    private static final String DEFAULT_LIBELLE_CIVILITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CIVILITE = "BBBBBBBBBB";

    private static final String DEFAULT_INTITULE_CIVILITE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_CIVILITE = "BBBBBBBBBB";

    @Autowired
    private CiviliteRepository civiliteRepository;

    @Autowired
    private CiviliteService civiliteService;

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

    private MockMvc restCiviliteMockMvc;

    private Civilite civilite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiviliteResource civiliteResource = new CiviliteResource(civiliteService);
        this.restCiviliteMockMvc = MockMvcBuilders.standaloneSetup(civiliteResource)
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
    public static Civilite createEntity(EntityManager em) {
        Civilite civilite = new Civilite()
            .libelleCivilite(DEFAULT_LIBELLE_CIVILITE)
            .intituleCivilite(DEFAULT_INTITULE_CIVILITE);
        return civilite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Civilite createUpdatedEntity(EntityManager em) {
        Civilite civilite = new Civilite()
            .libelleCivilite(UPDATED_LIBELLE_CIVILITE)
            .intituleCivilite(UPDATED_INTITULE_CIVILITE);
        return civilite;
    }

    @BeforeEach
    public void initTest() {
        civilite = createEntity(em);
    }

    @Test
    @Transactional
    public void createCivilite() throws Exception {
        int databaseSizeBeforeCreate = civiliteRepository.findAll().size();

        // Create the Civilite
        restCiviliteMockMvc.perform(post("/api/civilites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(civilite)))
            .andExpect(status().isCreated());

        // Validate the Civilite in the database
        List<Civilite> civiliteList = civiliteRepository.findAll();
        assertThat(civiliteList).hasSize(databaseSizeBeforeCreate + 1);
        Civilite testCivilite = civiliteList.get(civiliteList.size() - 1);
        assertThat(testCivilite.getLibelleCivilite()).isEqualTo(DEFAULT_LIBELLE_CIVILITE);
        assertThat(testCivilite.getIntituleCivilite()).isEqualTo(DEFAULT_INTITULE_CIVILITE);
    }

    @Test
    @Transactional
    public void createCiviliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = civiliteRepository.findAll().size();

        // Create the Civilite with an existing ID
        civilite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiviliteMockMvc.perform(post("/api/civilites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(civilite)))
            .andExpect(status().isBadRequest());

        // Validate the Civilite in the database
        List<Civilite> civiliteList = civiliteRepository.findAll();
        assertThat(civiliteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCivilites() throws Exception {
        // Initialize the database
        civiliteRepository.saveAndFlush(civilite);

        // Get all the civiliteList
        restCiviliteMockMvc.perform(get("/api/civilites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCivilite").value(hasItem(DEFAULT_LIBELLE_CIVILITE)))
            .andExpect(jsonPath("$.[*].intituleCivilite").value(hasItem(DEFAULT_INTITULE_CIVILITE)));
    }
    
    @Test
    @Transactional
    public void getCivilite() throws Exception {
        // Initialize the database
        civiliteRepository.saveAndFlush(civilite);

        // Get the civilite
        restCiviliteMockMvc.perform(get("/api/civilites/{id}", civilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(civilite.getId().intValue()))
            .andExpect(jsonPath("$.libelleCivilite").value(DEFAULT_LIBELLE_CIVILITE))
            .andExpect(jsonPath("$.intituleCivilite").value(DEFAULT_INTITULE_CIVILITE));
    }

    @Test
    @Transactional
    public void getNonExistingCivilite() throws Exception {
        // Get the civilite
        restCiviliteMockMvc.perform(get("/api/civilites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCivilite() throws Exception {
        // Initialize the database
        civiliteService.save(civilite);

        int databaseSizeBeforeUpdate = civiliteRepository.findAll().size();

        // Update the civilite
        Civilite updatedCivilite = civiliteRepository.findById(civilite.getId()).get();
        // Disconnect from session so that the updates on updatedCivilite are not directly saved in db
        em.detach(updatedCivilite);
        updatedCivilite
            .libelleCivilite(UPDATED_LIBELLE_CIVILITE)
            .intituleCivilite(UPDATED_INTITULE_CIVILITE);

        restCiviliteMockMvc.perform(put("/api/civilites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCivilite)))
            .andExpect(status().isOk());

        // Validate the Civilite in the database
        List<Civilite> civiliteList = civiliteRepository.findAll();
        assertThat(civiliteList).hasSize(databaseSizeBeforeUpdate);
        Civilite testCivilite = civiliteList.get(civiliteList.size() - 1);
        assertThat(testCivilite.getLibelleCivilite()).isEqualTo(UPDATED_LIBELLE_CIVILITE);
        assertThat(testCivilite.getIntituleCivilite()).isEqualTo(UPDATED_INTITULE_CIVILITE);
    }

    @Test
    @Transactional
    public void updateNonExistingCivilite() throws Exception {
        int databaseSizeBeforeUpdate = civiliteRepository.findAll().size();

        // Create the Civilite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiviliteMockMvc.perform(put("/api/civilites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(civilite)))
            .andExpect(status().isBadRequest());

        // Validate the Civilite in the database
        List<Civilite> civiliteList = civiliteRepository.findAll();
        assertThat(civiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCivilite() throws Exception {
        // Initialize the database
        civiliteService.save(civilite);

        int databaseSizeBeforeDelete = civiliteRepository.findAll().size();

        // Delete the civilite
        restCiviliteMockMvc.perform(delete("/api/civilites/{id}", civilite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Civilite> civiliteList = civiliteRepository.findAll();
        assertThat(civiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
