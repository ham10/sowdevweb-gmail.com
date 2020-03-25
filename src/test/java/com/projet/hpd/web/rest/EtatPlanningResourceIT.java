package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatPlanning;
import com.projet.hpd.repository.EtatPlanningRepository;
import com.projet.hpd.service.EtatPlanningService;
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
 * Integration tests for the {@link EtatPlanningResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatPlanningResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EtatPlanningRepository etatPlanningRepository;

    @Autowired
    private EtatPlanningService etatPlanningService;

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

    private MockMvc restEtatPlanningMockMvc;

    private EtatPlanning etatPlanning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatPlanningResource etatPlanningResource = new EtatPlanningResource(etatPlanningService);
        this.restEtatPlanningMockMvc = MockMvcBuilders.standaloneSetup(etatPlanningResource)
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
    public static EtatPlanning createEntity(EntityManager em) {
        EtatPlanning etatPlanning = new EtatPlanning()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return etatPlanning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatPlanning createUpdatedEntity(EntityManager em) {
        EtatPlanning etatPlanning = new EtatPlanning()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return etatPlanning;
    }

    @BeforeEach
    public void initTest() {
        etatPlanning = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatPlanning() throws Exception {
        int databaseSizeBeforeCreate = etatPlanningRepository.findAll().size();

        // Create the EtatPlanning
        restEtatPlanningMockMvc.perform(post("/api/etat-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatPlanning)))
            .andExpect(status().isCreated());

        // Validate the EtatPlanning in the database
        List<EtatPlanning> etatPlanningList = etatPlanningRepository.findAll();
        assertThat(etatPlanningList).hasSize(databaseSizeBeforeCreate + 1);
        EtatPlanning testEtatPlanning = etatPlanningList.get(etatPlanningList.size() - 1);
        assertThat(testEtatPlanning.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatPlanning.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEtatPlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatPlanningRepository.findAll().size();

        // Create the EtatPlanning with an existing ID
        etatPlanning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatPlanningMockMvc.perform(post("/api/etat-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the EtatPlanning in the database
        List<EtatPlanning> etatPlanningList = etatPlanningRepository.findAll();
        assertThat(etatPlanningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatPlannings() throws Exception {
        // Initialize the database
        etatPlanningRepository.saveAndFlush(etatPlanning);

        // Get all the etatPlanningList
        restEtatPlanningMockMvc.perform(get("/api/etat-plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatPlanning.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getEtatPlanning() throws Exception {
        // Initialize the database
        etatPlanningRepository.saveAndFlush(etatPlanning);

        // Get the etatPlanning
        restEtatPlanningMockMvc.perform(get("/api/etat-plannings/{id}", etatPlanning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatPlanning.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingEtatPlanning() throws Exception {
        // Get the etatPlanning
        restEtatPlanningMockMvc.perform(get("/api/etat-plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatPlanning() throws Exception {
        // Initialize the database
        etatPlanningService.save(etatPlanning);

        int databaseSizeBeforeUpdate = etatPlanningRepository.findAll().size();

        // Update the etatPlanning
        EtatPlanning updatedEtatPlanning = etatPlanningRepository.findById(etatPlanning.getId()).get();
        // Disconnect from session so that the updates on updatedEtatPlanning are not directly saved in db
        em.detach(updatedEtatPlanning);
        updatedEtatPlanning
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restEtatPlanningMockMvc.perform(put("/api/etat-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatPlanning)))
            .andExpect(status().isOk());

        // Validate the EtatPlanning in the database
        List<EtatPlanning> etatPlanningList = etatPlanningRepository.findAll();
        assertThat(etatPlanningList).hasSize(databaseSizeBeforeUpdate);
        EtatPlanning testEtatPlanning = etatPlanningList.get(etatPlanningList.size() - 1);
        assertThat(testEtatPlanning.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatPlanning.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatPlanning() throws Exception {
        int databaseSizeBeforeUpdate = etatPlanningRepository.findAll().size();

        // Create the EtatPlanning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatPlanningMockMvc.perform(put("/api/etat-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the EtatPlanning in the database
        List<EtatPlanning> etatPlanningList = etatPlanningRepository.findAll();
        assertThat(etatPlanningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatPlanning() throws Exception {
        // Initialize the database
        etatPlanningService.save(etatPlanning);

        int databaseSizeBeforeDelete = etatPlanningRepository.findAll().size();

        // Delete the etatPlanning
        restEtatPlanningMockMvc.perform(delete("/api/etat-plannings/{id}", etatPlanning.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatPlanning> etatPlanningList = etatPlanningRepository.findAll();
        assertThat(etatPlanningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
