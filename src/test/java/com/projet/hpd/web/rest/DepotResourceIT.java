package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Depot;
import com.projet.hpd.repository.DepotRepository;
import com.projet.hpd.service.DepotService;
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
 * Integration tests for the {@link DepotResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DepotResourceIT {

    private static final Integer DEFAULT_CODE_DEPOT = 1;
    private static final Integer UPDATED_CODE_DEPOT = 2;

    private static final String DEFAULT_LIBELLE_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DEPOT = "BBBBBBBBBB";

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private DepotService depotService;

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

    private MockMvc restDepotMockMvc;

    private Depot depot;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepotResource depotResource = new DepotResource(depotService);
        this.restDepotMockMvc = MockMvcBuilders.standaloneSetup(depotResource)
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
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot()
            .codeDepot(DEFAULT_CODE_DEPOT)
            .libelleDepot(DEFAULT_LIBELLE_DEPOT);
        return depot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createUpdatedEntity(EntityManager em) {
        Depot depot = new Depot()
            .codeDepot(UPDATED_CODE_DEPOT)
            .libelleDepot(UPDATED_LIBELLE_DEPOT);
        return depot;
    }

    @BeforeEach
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepot() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isCreated());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate + 1);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getCodeDepot()).isEqualTo(DEFAULT_CODE_DEPOT);
        assertThat(testDepot.getLibelleDepot()).isEqualTo(DEFAULT_LIBELLE_DEPOT);
    }

    @Test
    @Transactional
    public void createDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot with an existing ID
        depot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeDepot").value(hasItem(DEFAULT_CODE_DEPOT)))
            .andExpect(jsonPath("$.[*].libelleDepot").value(hasItem(DEFAULT_LIBELLE_DEPOT)));
    }
    
    @Test
    @Transactional
    public void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.codeDepot").value(DEFAULT_CODE_DEPOT))
            .andExpect(jsonPath("$.libelleDepot").value(DEFAULT_LIBELLE_DEPOT));
    }

    @Test
    @Transactional
    public void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).get();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot
            .codeDepot(UPDATED_CODE_DEPOT)
            .libelleDepot(UPDATED_LIBELLE_DEPOT);

        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepot)))
            .andExpect(status().isOk());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getCodeDepot()).isEqualTo(UPDATED_CODE_DEPOT);
        assertThat(testDepot.getLibelleDepot()).isEqualTo(UPDATED_LIBELLE_DEPOT);
    }

    @Test
    @Transactional
    public void updateNonExistingDepot() throws Exception {
        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Create the Depot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeDelete = depotRepository.findAll().size();

        // Delete the depot
        restDepotMockMvc.perform(delete("/api/depots/{id}", depot.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
