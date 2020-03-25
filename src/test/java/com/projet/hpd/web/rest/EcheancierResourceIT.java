package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Echeancier;
import com.projet.hpd.repository.EcheancierRepository;
import com.projet.hpd.service.EcheancierService;
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
 * Integration tests for the {@link EcheancierResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EcheancierResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PAIEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PAIEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Double DEFAULT_MONTANT_PAYE = 1D;
    private static final Double UPDATED_MONTANT_PAYE = 2D;

    private static final Double DEFAULT_CAPITAL = 1D;
    private static final Double UPDATED_CAPITAL = 2D;

    private static final Double DEFAULT_FRAIS = 1D;
    private static final Double UPDATED_FRAIS = 2D;

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
    private EcheancierRepository echeancierRepository;

    @Autowired
    private EcheancierService echeancierService;

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

    private MockMvc restEcheancierMockMvc;

    private Echeancier echeancier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EcheancierResource echeancierResource = new EcheancierResource(echeancierService);
        this.restEcheancierMockMvc = MockMvcBuilders.standaloneSetup(echeancierResource)
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
    public static Echeancier createEntity(EntityManager em) {
        Echeancier echeancier = new Echeancier()
            .code(DEFAULT_CODE)
            .numero(DEFAULT_NUMERO)
            .date(DEFAULT_DATE)
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .montant(DEFAULT_MONTANT)
            .montantPaye(DEFAULT_MONTANT_PAYE)
            .capital(DEFAULT_CAPITAL)
            .frais(DEFAULT_FRAIS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return echeancier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Echeancier createUpdatedEntity(EntityManager em) {
        Echeancier echeancier = new Echeancier()
            .code(UPDATED_CODE)
            .numero(UPDATED_NUMERO)
            .date(UPDATED_DATE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .montantPaye(UPDATED_MONTANT_PAYE)
            .capital(UPDATED_CAPITAL)
            .frais(UPDATED_FRAIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return echeancier;
    }

    @BeforeEach
    public void initTest() {
        echeancier = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcheancier() throws Exception {
        int databaseSizeBeforeCreate = echeancierRepository.findAll().size();

        // Create the Echeancier
        restEcheancierMockMvc.perform(post("/api/echeanciers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(echeancier)))
            .andExpect(status().isCreated());

        // Validate the Echeancier in the database
        List<Echeancier> echeancierList = echeancierRepository.findAll();
        assertThat(echeancierList).hasSize(databaseSizeBeforeCreate + 1);
        Echeancier testEcheancier = echeancierList.get(echeancierList.size() - 1);
        assertThat(testEcheancier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEcheancier.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEcheancier.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEcheancier.getDatePaiement()).isEqualTo(DEFAULT_DATE_PAIEMENT);
        assertThat(testEcheancier.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testEcheancier.getMontantPaye()).isEqualTo(DEFAULT_MONTANT_PAYE);
        assertThat(testEcheancier.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testEcheancier.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testEcheancier.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEcheancier.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEcheancier.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEcheancier.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEcheancier.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEcheancier.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEcheancierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = echeancierRepository.findAll().size();

        // Create the Echeancier with an existing ID
        echeancier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcheancierMockMvc.perform(post("/api/echeanciers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(echeancier)))
            .andExpect(status().isBadRequest());

        // Validate the Echeancier in the database
        List<Echeancier> echeancierList = echeancierRepository.findAll();
        assertThat(echeancierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEcheanciers() throws Exception {
        // Initialize the database
        echeancierRepository.saveAndFlush(echeancier);

        // Get all the echeancierList
        restEcheancierMockMvc.perform(get("/api/echeanciers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(echeancier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(DEFAULT_DATE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPaye").value(hasItem(DEFAULT_MONTANT_PAYE.doubleValue())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEcheancier() throws Exception {
        // Initialize the database
        echeancierRepository.saveAndFlush(echeancier);

        // Get the echeancier
        restEcheancierMockMvc.perform(get("/api/echeanciers/{id}", echeancier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(echeancier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.datePaiement").value(DEFAULT_DATE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.montantPaye").value(DEFAULT_MONTANT_PAYE.doubleValue()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.doubleValue()))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEcheancier() throws Exception {
        // Get the echeancier
        restEcheancierMockMvc.perform(get("/api/echeanciers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcheancier() throws Exception {
        // Initialize the database
        echeancierService.save(echeancier);

        int databaseSizeBeforeUpdate = echeancierRepository.findAll().size();

        // Update the echeancier
        Echeancier updatedEcheancier = echeancierRepository.findById(echeancier.getId()).get();
        // Disconnect from session so that the updates on updatedEcheancier are not directly saved in db
        em.detach(updatedEcheancier);
        updatedEcheancier
            .code(UPDATED_CODE)
            .numero(UPDATED_NUMERO)
            .date(UPDATED_DATE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .montantPaye(UPDATED_MONTANT_PAYE)
            .capital(UPDATED_CAPITAL)
            .frais(UPDATED_FRAIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEcheancierMockMvc.perform(put("/api/echeanciers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEcheancier)))
            .andExpect(status().isOk());

        // Validate the Echeancier in the database
        List<Echeancier> echeancierList = echeancierRepository.findAll();
        assertThat(echeancierList).hasSize(databaseSizeBeforeUpdate);
        Echeancier testEcheancier = echeancierList.get(echeancierList.size() - 1);
        assertThat(testEcheancier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEcheancier.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEcheancier.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEcheancier.getDatePaiement()).isEqualTo(UPDATED_DATE_PAIEMENT);
        assertThat(testEcheancier.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcheancier.getMontantPaye()).isEqualTo(UPDATED_MONTANT_PAYE);
        assertThat(testEcheancier.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testEcheancier.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testEcheancier.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEcheancier.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEcheancier.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEcheancier.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEcheancier.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEcheancier.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEcheancier() throws Exception {
        int databaseSizeBeforeUpdate = echeancierRepository.findAll().size();

        // Create the Echeancier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcheancierMockMvc.perform(put("/api/echeanciers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(echeancier)))
            .andExpect(status().isBadRequest());

        // Validate the Echeancier in the database
        List<Echeancier> echeancierList = echeancierRepository.findAll();
        assertThat(echeancierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEcheancier() throws Exception {
        // Initialize the database
        echeancierService.save(echeancier);

        int databaseSizeBeforeDelete = echeancierRepository.findAll().size();

        // Delete the echeancier
        restEcheancierMockMvc.perform(delete("/api/echeanciers/{id}", echeancier.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Echeancier> echeancierList = echeancierRepository.findAll();
        assertThat(echeancierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
