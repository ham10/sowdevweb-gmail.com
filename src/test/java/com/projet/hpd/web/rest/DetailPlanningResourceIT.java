package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.DetailPlanning;
import com.projet.hpd.repository.DetailPlanningRepository;
import com.projet.hpd.service.DetailPlanningService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DetailPlanningResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DetailPlanningResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DetailPlanningRepository detailPlanningRepository;

    @Autowired
    private DetailPlanningService detailPlanningService;

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

    private MockMvc restDetailPlanningMockMvc;

    private DetailPlanning detailPlanning;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailPlanningResource detailPlanningResource = new DetailPlanningResource(detailPlanningService);
        this.restDetailPlanningMockMvc = MockMvcBuilders.standaloneSetup(detailPlanningResource)
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
    public static DetailPlanning createEntity(EntityManager em) {
        DetailPlanning detailPlanning = new DetailPlanning()
            .titre(DEFAULT_TITRE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return detailPlanning;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailPlanning createUpdatedEntity(EntityManager em) {
        DetailPlanning detailPlanning = new DetailPlanning()
            .titre(UPDATED_TITRE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        return detailPlanning;
    }

    @BeforeEach
    public void initTest() {
        detailPlanning = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailPlanning() throws Exception {
        int databaseSizeBeforeCreate = detailPlanningRepository.findAll().size();

        // Create the DetailPlanning
        restDetailPlanningMockMvc.perform(post("/api/detail-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPlanning)))
            .andExpect(status().isCreated());

        // Validate the DetailPlanning in the database
        List<DetailPlanning> detailPlanningList = detailPlanningRepository.findAll();
        assertThat(detailPlanningList).hasSize(databaseSizeBeforeCreate + 1);
        DetailPlanning testDetailPlanning = detailPlanningList.get(detailPlanningList.size() - 1);
        assertThat(testDetailPlanning.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testDetailPlanning.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDetailPlanning.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createDetailPlanningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailPlanningRepository.findAll().size();

        // Create the DetailPlanning with an existing ID
        detailPlanning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailPlanningMockMvc.perform(post("/api/detail-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the DetailPlanning in the database
        List<DetailPlanning> detailPlanningList = detailPlanningRepository.findAll();
        assertThat(detailPlanningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetailPlannings() throws Exception {
        // Initialize the database
        detailPlanningRepository.saveAndFlush(detailPlanning);

        // Get all the detailPlanningList
        restDetailPlanningMockMvc.perform(get("/api/detail-plannings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailPlanning.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getDetailPlanning() throws Exception {
        // Initialize the database
        detailPlanningRepository.saveAndFlush(detailPlanning);

        // Get the detailPlanning
        restDetailPlanningMockMvc.perform(get("/api/detail-plannings/{id}", detailPlanning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailPlanning.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailPlanning() throws Exception {
        // Get the detailPlanning
        restDetailPlanningMockMvc.perform(get("/api/detail-plannings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailPlanning() throws Exception {
        // Initialize the database
        detailPlanningService.save(detailPlanning);

        int databaseSizeBeforeUpdate = detailPlanningRepository.findAll().size();

        // Update the detailPlanning
        DetailPlanning updatedDetailPlanning = detailPlanningRepository.findById(detailPlanning.getId()).get();
        // Disconnect from session so that the updates on updatedDetailPlanning are not directly saved in db
        em.detach(updatedDetailPlanning);
        updatedDetailPlanning
            .titre(UPDATED_TITRE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);

        restDetailPlanningMockMvc.perform(put("/api/detail-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetailPlanning)))
            .andExpect(status().isOk());

        // Validate the DetailPlanning in the database
        List<DetailPlanning> detailPlanningList = detailPlanningRepository.findAll();
        assertThat(detailPlanningList).hasSize(databaseSizeBeforeUpdate);
        DetailPlanning testDetailPlanning = detailPlanningList.get(detailPlanningList.size() - 1);
        assertThat(testDetailPlanning.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testDetailPlanning.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDetailPlanning.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailPlanning() throws Exception {
        int databaseSizeBeforeUpdate = detailPlanningRepository.findAll().size();

        // Create the DetailPlanning

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailPlanningMockMvc.perform(put("/api/detail-plannings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(detailPlanning)))
            .andExpect(status().isBadRequest());

        // Validate the DetailPlanning in the database
        List<DetailPlanning> detailPlanningList = detailPlanningRepository.findAll();
        assertThat(detailPlanningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetailPlanning() throws Exception {
        // Initialize the database
        detailPlanningService.save(detailPlanning);

        int databaseSizeBeforeDelete = detailPlanningRepository.findAll().size();

        // Delete the detailPlanning
        restDetailPlanningMockMvc.perform(delete("/api/detail-plannings/{id}", detailPlanning.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailPlanning> detailPlanningList = detailPlanningRepository.findAll();
        assertThat(detailPlanningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
