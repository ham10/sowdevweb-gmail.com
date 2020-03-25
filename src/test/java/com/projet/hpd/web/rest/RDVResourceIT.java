package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.RDV;
import com.projet.hpd.repository.RDVRepository;
import com.projet.hpd.service.RDVService;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RDVResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class RDVResourceIT {

    private static final String DEFAULT_NUM_RDV = "AAAAAAAAAA";
    private static final String UPDATED_NUM_RDV = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RDV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RDV = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HEURE_RDV = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEURE_RDV = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RDVRepository rDVRepository;

    @Autowired
    private RDVService rDVService;

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

    private MockMvc restRDVMockMvc;

    private RDV rDV;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RDVResource rDVResource = new RDVResource(rDVService);
        this.restRDVMockMvc = MockMvcBuilders.standaloneSetup(rDVResource)
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
    public static RDV createEntity(EntityManager em) {
        RDV rDV = new RDV()
            .numRdv(DEFAULT_NUM_RDV)
            .dateRdv(DEFAULT_DATE_RDV)
            .heureRdv(DEFAULT_HEURE_RDV);
        return rDV;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RDV createUpdatedEntity(EntityManager em) {
        RDV rDV = new RDV()
            .numRdv(UPDATED_NUM_RDV)
            .dateRdv(UPDATED_DATE_RDV)
            .heureRdv(UPDATED_HEURE_RDV);
        return rDV;
    }

    @BeforeEach
    public void initTest() {
        rDV = createEntity(em);
    }

    @Test
    @Transactional
    public void createRDV() throws Exception {
        int databaseSizeBeforeCreate = rDVRepository.findAll().size();

        // Create the RDV
        restRDVMockMvc.perform(post("/api/rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rDV)))
            .andExpect(status().isCreated());

        // Validate the RDV in the database
        List<RDV> rDVList = rDVRepository.findAll();
        assertThat(rDVList).hasSize(databaseSizeBeforeCreate + 1);
        RDV testRDV = rDVList.get(rDVList.size() - 1);
        assertThat(testRDV.getNumRdv()).isEqualTo(DEFAULT_NUM_RDV);
        assertThat(testRDV.getDateRdv()).isEqualTo(DEFAULT_DATE_RDV);
        assertThat(testRDV.getHeureRdv()).isEqualTo(DEFAULT_HEURE_RDV);
    }

    @Test
    @Transactional
    public void createRDVWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rDVRepository.findAll().size();

        // Create the RDV with an existing ID
        rDV.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRDVMockMvc.perform(post("/api/rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rDV)))
            .andExpect(status().isBadRequest());

        // Validate the RDV in the database
        List<RDV> rDVList = rDVRepository.findAll();
        assertThat(rDVList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRDVS() throws Exception {
        // Initialize the database
        rDVRepository.saveAndFlush(rDV);

        // Get all the rDVList
        restRDVMockMvc.perform(get("/api/rdvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rDV.getId().intValue())))
            .andExpect(jsonPath("$.[*].numRdv").value(hasItem(DEFAULT_NUM_RDV)))
            .andExpect(jsonPath("$.[*].dateRdv").value(hasItem(DEFAULT_DATE_RDV.toString())))
            .andExpect(jsonPath("$.[*].heureRdv").value(hasItem(DEFAULT_HEURE_RDV.toString())));
    }
    
    @Test
    @Transactional
    public void getRDV() throws Exception {
        // Initialize the database
        rDVRepository.saveAndFlush(rDV);

        // Get the rDV
        restRDVMockMvc.perform(get("/api/rdvs/{id}", rDV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rDV.getId().intValue()))
            .andExpect(jsonPath("$.numRdv").value(DEFAULT_NUM_RDV))
            .andExpect(jsonPath("$.dateRdv").value(DEFAULT_DATE_RDV.toString()))
            .andExpect(jsonPath("$.heureRdv").value(DEFAULT_HEURE_RDV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRDV() throws Exception {
        // Get the rDV
        restRDVMockMvc.perform(get("/api/rdvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRDV() throws Exception {
        // Initialize the database
        rDVService.save(rDV);

        int databaseSizeBeforeUpdate = rDVRepository.findAll().size();

        // Update the rDV
        RDV updatedRDV = rDVRepository.findById(rDV.getId()).get();
        // Disconnect from session so that the updates on updatedRDV are not directly saved in db
        em.detach(updatedRDV);
        updatedRDV
            .numRdv(UPDATED_NUM_RDV)
            .dateRdv(UPDATED_DATE_RDV)
            .heureRdv(UPDATED_HEURE_RDV);

        restRDVMockMvc.perform(put("/api/rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRDV)))
            .andExpect(status().isOk());

        // Validate the RDV in the database
        List<RDV> rDVList = rDVRepository.findAll();
        assertThat(rDVList).hasSize(databaseSizeBeforeUpdate);
        RDV testRDV = rDVList.get(rDVList.size() - 1);
        assertThat(testRDV.getNumRdv()).isEqualTo(UPDATED_NUM_RDV);
        assertThat(testRDV.getDateRdv()).isEqualTo(UPDATED_DATE_RDV);
        assertThat(testRDV.getHeureRdv()).isEqualTo(UPDATED_HEURE_RDV);
    }

    @Test
    @Transactional
    public void updateNonExistingRDV() throws Exception {
        int databaseSizeBeforeUpdate = rDVRepository.findAll().size();

        // Create the RDV

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRDVMockMvc.perform(put("/api/rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rDV)))
            .andExpect(status().isBadRequest());

        // Validate the RDV in the database
        List<RDV> rDVList = rDVRepository.findAll();
        assertThat(rDVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRDV() throws Exception {
        // Initialize the database
        rDVService.save(rDV);

        int databaseSizeBeforeDelete = rDVRepository.findAll().size();

        // Delete the rDV
        restRDVMockMvc.perform(delete("/api/rdvs/{id}", rDV.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RDV> rDVList = rDVRepository.findAll();
        assertThat(rDVList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
