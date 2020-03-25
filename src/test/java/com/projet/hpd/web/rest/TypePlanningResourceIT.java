package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePlanning;
import com.projet.hpd.repository.TypePlanningRepository;
import com.projet.hpd.service.TypePlanningService;
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
 * Integration tests for the {@link TypePlanningResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePlanningResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypePlanningRepository typePlanningRepository;

    @Autowired
    private TypePlanningService typePlanningService;

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

    private MockMvc restTypePlanningMockMvc;

    private TypePlanning typePlanning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePlanningResource typePlanningResource = new TypePlanningResource(typePlanningService);
        this.restTypePlanningMockMvc = MockMvcBuilders.standaloneSetup(typePlanningResource)
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
    public static TypePlanning createEntity(EntityManager em) {
        TypePlanning typePlanning = new TypePlanning()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return typePlanning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePlanning createUpdatedEntity(EntityManager em) {
        TypePlanning typePlanning = new TypePlanning()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return typePlanning;
    }

    @BeforeEach
    public void initTest() {
        typePlanning = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePlanning() throws Exception {
        int databaseSizeBeforeCreate = typePlanningRepository.findAll().size();

        // Create the TypePlanning
        restTypePlanningMockMvc.perform(post("/api/type-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlanning)))
            .andExpect(status().isCreated());

        // Validate the TypePlanning in the database
        List<TypePlanning> typePlanningList = typePlanningRepository.findAll();
        assertThat(typePlanningList).hasSize(databaseSizeBeforeCreate + 1);
        TypePlanning testTypePlanning = typePlanningList.get(typePlanningList.size() - 1);
        assertThat(testTypePlanning.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypePlanning.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypePlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePlanningRepository.findAll().size();

        // Create the TypePlanning with an existing ID
        typePlanning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePlanningMockMvc.perform(post("/api/type-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlanning)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlanning in the database
        List<TypePlanning> typePlanningList = typePlanningRepository.findAll();
        assertThat(typePlanningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePlannings() throws Exception {
        // Initialize the database
        typePlanningRepository.saveAndFlush(typePlanning);

        // Get all the typePlanningList
        restTypePlanningMockMvc.perform(get("/api/type-plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePlanning.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getTypePlanning() throws Exception {
        // Initialize the database
        typePlanningRepository.saveAndFlush(typePlanning);

        // Get the typePlanning
        restTypePlanningMockMvc.perform(get("/api/type-plannings/{id}", typePlanning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePlanning.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingTypePlanning() throws Exception {
        // Get the typePlanning
        restTypePlanningMockMvc.perform(get("/api/type-plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePlanning() throws Exception {
        // Initialize the database
        typePlanningService.save(typePlanning);

        int databaseSizeBeforeUpdate = typePlanningRepository.findAll().size();

        // Update the typePlanning
        TypePlanning updatedTypePlanning = typePlanningRepository.findById(typePlanning.getId()).get();
        // Disconnect from session so that the updates on updatedTypePlanning are not directly saved in db
        em.detach(updatedTypePlanning);
        updatedTypePlanning
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restTypePlanningMockMvc.perform(put("/api/type-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePlanning)))
            .andExpect(status().isOk());

        // Validate the TypePlanning in the database
        List<TypePlanning> typePlanningList = typePlanningRepository.findAll();
        assertThat(typePlanningList).hasSize(databaseSizeBeforeUpdate);
        TypePlanning testTypePlanning = typePlanningList.get(typePlanningList.size() - 1);
        assertThat(testTypePlanning.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypePlanning.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePlanning() throws Exception {
        int databaseSizeBeforeUpdate = typePlanningRepository.findAll().size();

        // Create the TypePlanning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePlanningMockMvc.perform(put("/api/type-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlanning)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlanning in the database
        List<TypePlanning> typePlanningList = typePlanningRepository.findAll();
        assertThat(typePlanningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePlanning() throws Exception {
        // Initialize the database
        typePlanningService.save(typePlanning);

        int databaseSizeBeforeDelete = typePlanningRepository.findAll().size();

        // Delete the typePlanning
        restTypePlanningMockMvc.perform(delete("/api/type-plannings/{id}", typePlanning.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePlanning> typePlanningList = typePlanningRepository.findAll();
        assertThat(typePlanningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
