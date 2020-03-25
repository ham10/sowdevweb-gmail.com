package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Planning;
import com.projet.hpd.repository.PlanningRepository;
import com.projet.hpd.service.PlanningService;
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
 * Integration tests for the {@link PlanningResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class PlanningResourceIT {

    private static final String DEFAULT_NUM = "AAAAAAAAAA";
    private static final String UPDATED_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private PlanningService planningService;

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

    private MockMvc restPlanningMockMvc;

    private Planning planning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanningResource planningResource = new PlanningResource(planningService);
        this.restPlanningMockMvc = MockMvcBuilders.standaloneSetup(planningResource)
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
    public static Planning createEntity(EntityManager em) {
        Planning planning = new Planning()
            .num(DEFAULT_NUM)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED);
        return planning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planning createUpdatedEntity(EntityManager em) {
        Planning planning = new Planning()
            .num(UPDATED_NUM)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED);
        return planning;
    }

    @BeforeEach
    public void initTest() {
        planning = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanning() throws Exception {
        int databaseSizeBeforeCreate = planningRepository.findAll().size();

        // Create the Planning
        restPlanningMockMvc.perform(post("/api/plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planning)))
            .andExpect(status().isCreated());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeCreate + 1);
        Planning testPlanning = planningList.get(planningList.size() - 1);
        assertThat(testPlanning.getNum()).isEqualTo(DEFAULT_NUM);
        assertThat(testPlanning.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPlanning.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createPlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planningRepository.findAll().size();

        // Create the Planning with an existing ID
        planning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanningMockMvc.perform(post("/api/plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planning)))
            .andExpect(status().isBadRequest());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlannings() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get all the planningList
        restPlanningMockMvc.perform(get("/api/plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planning.getId().intValue())))
            .andExpect(jsonPath("$.[*].num").value(hasItem(DEFAULT_NUM)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanning() throws Exception {
        // Initialize the database
        planningRepository.saveAndFlush(planning);

        // Get the planning
        restPlanningMockMvc.perform(get("/api/plannings/{id}", planning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planning.getId().intValue()))
            .andExpect(jsonPath("$.num").value(DEFAULT_NUM))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanning() throws Exception {
        // Get the planning
        restPlanningMockMvc.perform(get("/api/plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanning() throws Exception {
        // Initialize the database
        planningService.save(planning);

        int databaseSizeBeforeUpdate = planningRepository.findAll().size();

        // Update the planning
        Planning updatedPlanning = planningRepository.findById(planning.getId()).get();
        // Disconnect from session so that the updates on updatedPlanning are not directly saved in db
        em.detach(updatedPlanning);
        updatedPlanning
            .num(UPDATED_NUM)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED);

        restPlanningMockMvc.perform(put("/api/plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanning)))
            .andExpect(status().isOk());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeUpdate);
        Planning testPlanning = planningList.get(planningList.size() - 1);
        assertThat(testPlanning.getNum()).isEqualTo(UPDATED_NUM);
        assertThat(testPlanning.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPlanning.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanning() throws Exception {
        int databaseSizeBeforeUpdate = planningRepository.findAll().size();

        // Create the Planning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanningMockMvc.perform(put("/api/plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planning)))
            .andExpect(status().isBadRequest());

        // Validate the Planning in the database
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanning() throws Exception {
        // Initialize the database
        planningService.save(planning);

        int databaseSizeBeforeDelete = planningRepository.findAll().size();

        // Delete the planning
        restPlanningMockMvc.perform(delete("/api/plannings/{id}", planning.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planning> planningList = planningRepository.findAll();
        assertThat(planningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
