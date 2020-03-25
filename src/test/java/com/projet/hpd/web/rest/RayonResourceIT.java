package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Rayon;
import com.projet.hpd.repository.RayonRepository;
import com.projet.hpd.service.RayonService;
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
 * Integration tests for the {@link RayonResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class RayonResourceIT {

    private static final String DEFAULT_CODE_RAYON = "AAAAAAAAAA";
    private static final String UPDATED_CODE_RAYON = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_RAYON = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_RAYON = "BBBBBBBBBB";

    @Autowired
    private RayonRepository rayonRepository;

    @Autowired
    private RayonService rayonService;

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

    private MockMvc restRayonMockMvc;

    private Rayon rayon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RayonResource rayonResource = new RayonResource(rayonService);
        this.restRayonMockMvc = MockMvcBuilders.standaloneSetup(rayonResource)
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
    public static Rayon createEntity(EntityManager em) {
        Rayon rayon = new Rayon()
            .codeRayon(DEFAULT_CODE_RAYON)
            .libelleRayon(DEFAULT_LIBELLE_RAYON);
        return rayon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rayon createUpdatedEntity(EntityManager em) {
        Rayon rayon = new Rayon()
            .codeRayon(UPDATED_CODE_RAYON)
            .libelleRayon(UPDATED_LIBELLE_RAYON);
        return rayon;
    }

    @BeforeEach
    public void initTest() {
        rayon = createEntity(em);
    }

    @Test
    @Transactional
    public void createRayon() throws Exception {
        int databaseSizeBeforeCreate = rayonRepository.findAll().size();

        // Create the Rayon
        restRayonMockMvc.perform(post("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isCreated());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeCreate + 1);
        Rayon testRayon = rayonList.get(rayonList.size() - 1);
        assertThat(testRayon.getCodeRayon()).isEqualTo(DEFAULT_CODE_RAYON);
        assertThat(testRayon.getLibelleRayon()).isEqualTo(DEFAULT_LIBELLE_RAYON);
    }

    @Test
    @Transactional
    public void createRayonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rayonRepository.findAll().size();

        // Create the Rayon with an existing ID
        rayon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRayonMockMvc.perform(post("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isBadRequest());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRayons() throws Exception {
        // Initialize the database
        rayonRepository.saveAndFlush(rayon);

        // Get all the rayonList
        restRayonMockMvc.perform(get("/api/rayons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rayon.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeRayon").value(hasItem(DEFAULT_CODE_RAYON)))
            .andExpect(jsonPath("$.[*].libelleRayon").value(hasItem(DEFAULT_LIBELLE_RAYON)));
    }
    
    @Test
    @Transactional
    public void getRayon() throws Exception {
        // Initialize the database
        rayonRepository.saveAndFlush(rayon);

        // Get the rayon
        restRayonMockMvc.perform(get("/api/rayons/{id}", rayon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rayon.getId().intValue()))
            .andExpect(jsonPath("$.codeRayon").value(DEFAULT_CODE_RAYON))
            .andExpect(jsonPath("$.libelleRayon").value(DEFAULT_LIBELLE_RAYON));
    }

    @Test
    @Transactional
    public void getNonExistingRayon() throws Exception {
        // Get the rayon
        restRayonMockMvc.perform(get("/api/rayons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRayon() throws Exception {
        // Initialize the database
        rayonService.save(rayon);

        int databaseSizeBeforeUpdate = rayonRepository.findAll().size();

        // Update the rayon
        Rayon updatedRayon = rayonRepository.findById(rayon.getId()).get();
        // Disconnect from session so that the updates on updatedRayon are not directly saved in db
        em.detach(updatedRayon);
        updatedRayon
            .codeRayon(UPDATED_CODE_RAYON)
            .libelleRayon(UPDATED_LIBELLE_RAYON);

        restRayonMockMvc.perform(put("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRayon)))
            .andExpect(status().isOk());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeUpdate);
        Rayon testRayon = rayonList.get(rayonList.size() - 1);
        assertThat(testRayon.getCodeRayon()).isEqualTo(UPDATED_CODE_RAYON);
        assertThat(testRayon.getLibelleRayon()).isEqualTo(UPDATED_LIBELLE_RAYON);
    }

    @Test
    @Transactional
    public void updateNonExistingRayon() throws Exception {
        int databaseSizeBeforeUpdate = rayonRepository.findAll().size();

        // Create the Rayon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRayonMockMvc.perform(put("/api/rayons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rayon)))
            .andExpect(status().isBadRequest());

        // Validate the Rayon in the database
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRayon() throws Exception {
        // Initialize the database
        rayonService.save(rayon);

        int databaseSizeBeforeDelete = rayonRepository.findAll().size();

        // Delete the rayon
        restRayonMockMvc.perform(delete("/api/rayons/{id}", rayon.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rayon> rayonList = rayonRepository.findAll();
        assertThat(rayonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
