package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Facture;
import com.projet.hpd.repository.FactureRepository;
import com.projet.hpd.service.FactureService;
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
 * Integration tests for the {@link FactureResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FactureResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_FACT = 1D;
    private static final Double UPDATED_MONTANT_FACT = 2D;

    private static final Double DEFAULT_MONTANT_PAYE = 1D;
    private static final Double UPDATED_MONTANT_PAYE = 2D;

    private static final Double DEFAULT_MONTANT_GLOBAL = 1D;
    private static final Double UPDATED_MONTANT_GLOBAL = 2D;

    private static final Boolean DEFAULT_MORATOIRE = false;
    private static final Boolean UPDATED_MORATOIRE = true;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureService factureService;

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

    private MockMvc restFactureMockMvc;

    private Facture facture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FactureResource factureResource = new FactureResource(factureService);
        this.restFactureMockMvc = MockMvcBuilders.standaloneSetup(factureResource)
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
    public static Facture createEntity(EntityManager em) {
        Facture facture = new Facture()
            .designation(DEFAULT_DESIGNATION)
            .montantFact(DEFAULT_MONTANT_FACT)
            .montantPaye(DEFAULT_MONTANT_PAYE)
            .montantGlobal(DEFAULT_MONTANT_GLOBAL)
            .moratoire(DEFAULT_MORATOIRE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return facture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createUpdatedEntity(EntityManager em) {
        Facture facture = new Facture()
            .designation(UPDATED_DESIGNATION)
            .montantFact(UPDATED_MONTANT_FACT)
            .montantPaye(UPDATED_MONTANT_PAYE)
            .montantGlobal(UPDATED_MONTANT_GLOBAL)
            .moratoire(UPDATED_MORATOIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return facture;
    }

    @BeforeEach
    public void initTest() {
        facture = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacture() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // Create the Facture
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isCreated());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate + 1);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testFacture.getMontantFact()).isEqualTo(DEFAULT_MONTANT_FACT);
        assertThat(testFacture.getMontantPaye()).isEqualTo(DEFAULT_MONTANT_PAYE);
        assertThat(testFacture.getMontantGlobal()).isEqualTo(DEFAULT_MONTANT_GLOBAL);
        assertThat(testFacture.isMoratoire()).isEqualTo(DEFAULT_MORATOIRE);
        assertThat(testFacture.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFacture.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFacture.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testFacture.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFacture.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFacture.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // Create the Facture with an existing ID
        facture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFactures() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].montantFact").value(hasItem(DEFAULT_MONTANT_FACT.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPaye").value(hasItem(DEFAULT_MONTANT_PAYE.doubleValue())))
            .andExpect(jsonPath("$.[*].montantGlobal").value(hasItem(DEFAULT_MONTANT_GLOBAL.doubleValue())))
            .andExpect(jsonPath("$.[*].moratoire").value(hasItem(DEFAULT_MORATOIRE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", facture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facture.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.montantFact").value(DEFAULT_MONTANT_FACT.doubleValue()))
            .andExpect(jsonPath("$.montantPaye").value(DEFAULT_MONTANT_PAYE.doubleValue()))
            .andExpect(jsonPath("$.montantGlobal").value(DEFAULT_MONTANT_GLOBAL.doubleValue()))
            .andExpect(jsonPath("$.moratoire").value(DEFAULT_MORATOIRE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFacture() throws Exception {
        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacture() throws Exception {
        // Initialize the database
        factureService.save(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture
        Facture updatedFacture = factureRepository.findById(facture.getId()).get();
        // Disconnect from session so that the updates on updatedFacture are not directly saved in db
        em.detach(updatedFacture);
        updatedFacture
            .designation(UPDATED_DESIGNATION)
            .montantFact(UPDATED_MONTANT_FACT)
            .montantPaye(UPDATED_MONTANT_PAYE)
            .montantGlobal(UPDATED_MONTANT_GLOBAL)
            .moratoire(UPDATED_MORATOIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFactureMockMvc.perform(put("/api/factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacture)))
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testFacture.getMontantFact()).isEqualTo(UPDATED_MONTANT_FACT);
        assertThat(testFacture.getMontantPaye()).isEqualTo(UPDATED_MONTANT_PAYE);
        assertThat(testFacture.getMontantGlobal()).isEqualTo(UPDATED_MONTANT_GLOBAL);
        assertThat(testFacture.isMoratoire()).isEqualTo(UPDATED_MORATOIRE);
        assertThat(testFacture.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFacture.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFacture.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testFacture.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFacture.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFacture.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Create the Facture

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc.perform(put("/api/factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facture)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacture() throws Exception {
        // Initialize the database
        factureService.save(facture);

        int databaseSizeBeforeDelete = factureRepository.findAll().size();

        // Delete the facture
        restFactureMockMvc.perform(delete("/api/factures/{id}", facture.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
