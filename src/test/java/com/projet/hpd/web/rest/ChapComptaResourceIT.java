package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ChapCompta;
import com.projet.hpd.repository.ChapComptaRepository;
import com.projet.hpd.service.ChapComptaService;
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
 * Integration tests for the {@link ChapComptaResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ChapComptaResourceIT {

    private static final Integer DEFAULT_NUMERO_CHAP_COMPTA = 1;
    private static final Integer UPDATED_NUMERO_CHAP_COMPTA = 2;

    private static final String DEFAULT_LIBELLE_CHAP_COMPTA = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CHAP_COMPTA = "BBBBBBBBBB";

    private static final String DEFAULT_SENS_CHAP_COMPTA = "AAAAAAAAAA";
    private static final String UPDATED_SENS_CHAP_COMPTA = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE_CHAP_COMPTA = 1D;
    private static final Double UPDATED_SOLDE_CHAP_COMPTA = 2D;

    private static final Double DEFAULT_CUMUL_MOUV_DEBIT_CHAP_COMPTA = 1D;
    private static final Double UPDATED_CUMUL_MOUV_DEBIT_CHAP_COMPTA = 2D;

    private static final Double DEFAULT_CUMUL_MOUV_CREDIT_CHAP_COMPTA = 1D;
    private static final Double UPDATED_CUMUL_MOUV_CREDIT_CHAP_COMPTA = 2D;

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
    private ChapComptaRepository chapComptaRepository;

    @Autowired
    private ChapComptaService chapComptaService;

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

    private MockMvc restChapComptaMockMvc;

    private ChapCompta chapCompta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChapComptaResource chapComptaResource = new ChapComptaResource(chapComptaService);
        this.restChapComptaMockMvc = MockMvcBuilders.standaloneSetup(chapComptaResource)
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
    public static ChapCompta createEntity(EntityManager em) {
        ChapCompta chapCompta = new ChapCompta()
            .numeroChapCompta(DEFAULT_NUMERO_CHAP_COMPTA)
            .libelleChapCompta(DEFAULT_LIBELLE_CHAP_COMPTA)
            .sensChapCompta(DEFAULT_SENS_CHAP_COMPTA)
            .soldeChapCompta(DEFAULT_SOLDE_CHAP_COMPTA)
            .cumulMouvDebitChapCompta(DEFAULT_CUMUL_MOUV_DEBIT_CHAP_COMPTA)
            .cumulMouvCreditChapCompta(DEFAULT_CUMUL_MOUV_CREDIT_CHAP_COMPTA)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return chapCompta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChapCompta createUpdatedEntity(EntityManager em) {
        ChapCompta chapCompta = new ChapCompta()
            .numeroChapCompta(UPDATED_NUMERO_CHAP_COMPTA)
            .libelleChapCompta(UPDATED_LIBELLE_CHAP_COMPTA)
            .sensChapCompta(UPDATED_SENS_CHAP_COMPTA)
            .soldeChapCompta(UPDATED_SOLDE_CHAP_COMPTA)
            .cumulMouvDebitChapCompta(UPDATED_CUMUL_MOUV_DEBIT_CHAP_COMPTA)
            .cumulMouvCreditChapCompta(UPDATED_CUMUL_MOUV_CREDIT_CHAP_COMPTA)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return chapCompta;
    }

    @BeforeEach
    public void initTest() {
        chapCompta = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapCompta() throws Exception {
        int databaseSizeBeforeCreate = chapComptaRepository.findAll().size();

        // Create the ChapCompta
        restChapComptaMockMvc.perform(post("/api/chap-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapCompta)))
            .andExpect(status().isCreated());

        // Validate the ChapCompta in the database
        List<ChapCompta> chapComptaList = chapComptaRepository.findAll();
        assertThat(chapComptaList).hasSize(databaseSizeBeforeCreate + 1);
        ChapCompta testChapCompta = chapComptaList.get(chapComptaList.size() - 1);
        assertThat(testChapCompta.getNumeroChapCompta()).isEqualTo(DEFAULT_NUMERO_CHAP_COMPTA);
        assertThat(testChapCompta.getLibelleChapCompta()).isEqualTo(DEFAULT_LIBELLE_CHAP_COMPTA);
        assertThat(testChapCompta.getSensChapCompta()).isEqualTo(DEFAULT_SENS_CHAP_COMPTA);
        assertThat(testChapCompta.getSoldeChapCompta()).isEqualTo(DEFAULT_SOLDE_CHAP_COMPTA);
        assertThat(testChapCompta.getCumulMouvDebitChapCompta()).isEqualTo(DEFAULT_CUMUL_MOUV_DEBIT_CHAP_COMPTA);
        assertThat(testChapCompta.getCumulMouvCreditChapCompta()).isEqualTo(DEFAULT_CUMUL_MOUV_CREDIT_CHAP_COMPTA);
        assertThat(testChapCompta.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testChapCompta.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testChapCompta.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testChapCompta.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testChapCompta.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testChapCompta.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createChapComptaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chapComptaRepository.findAll().size();

        // Create the ChapCompta with an existing ID
        chapCompta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChapComptaMockMvc.perform(post("/api/chap-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapCompta)))
            .andExpect(status().isBadRequest());

        // Validate the ChapCompta in the database
        List<ChapCompta> chapComptaList = chapComptaRepository.findAll();
        assertThat(chapComptaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChapComptas() throws Exception {
        // Initialize the database
        chapComptaRepository.saveAndFlush(chapCompta);

        // Get all the chapComptaList
        restChapComptaMockMvc.perform(get("/api/chap-comptas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapCompta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroChapCompta").value(hasItem(DEFAULT_NUMERO_CHAP_COMPTA)))
            .andExpect(jsonPath("$.[*].libelleChapCompta").value(hasItem(DEFAULT_LIBELLE_CHAP_COMPTA)))
            .andExpect(jsonPath("$.[*].sensChapCompta").value(hasItem(DEFAULT_SENS_CHAP_COMPTA)))
            .andExpect(jsonPath("$.[*].soldeChapCompta").value(hasItem(DEFAULT_SOLDE_CHAP_COMPTA.doubleValue())))
            .andExpect(jsonPath("$.[*].cumulMouvDebitChapCompta").value(hasItem(DEFAULT_CUMUL_MOUV_DEBIT_CHAP_COMPTA.doubleValue())))
            .andExpect(jsonPath("$.[*].cumulMouvCreditChapCompta").value(hasItem(DEFAULT_CUMUL_MOUV_CREDIT_CHAP_COMPTA.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getChapCompta() throws Exception {
        // Initialize the database
        chapComptaRepository.saveAndFlush(chapCompta);

        // Get the chapCompta
        restChapComptaMockMvc.perform(get("/api/chap-comptas/{id}", chapCompta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chapCompta.getId().intValue()))
            .andExpect(jsonPath("$.numeroChapCompta").value(DEFAULT_NUMERO_CHAP_COMPTA))
            .andExpect(jsonPath("$.libelleChapCompta").value(DEFAULT_LIBELLE_CHAP_COMPTA))
            .andExpect(jsonPath("$.sensChapCompta").value(DEFAULT_SENS_CHAP_COMPTA))
            .andExpect(jsonPath("$.soldeChapCompta").value(DEFAULT_SOLDE_CHAP_COMPTA.doubleValue()))
            .andExpect(jsonPath("$.cumulMouvDebitChapCompta").value(DEFAULT_CUMUL_MOUV_DEBIT_CHAP_COMPTA.doubleValue()))
            .andExpect(jsonPath("$.cumulMouvCreditChapCompta").value(DEFAULT_CUMUL_MOUV_CREDIT_CHAP_COMPTA.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChapCompta() throws Exception {
        // Get the chapCompta
        restChapComptaMockMvc.perform(get("/api/chap-comptas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapCompta() throws Exception {
        // Initialize the database
        chapComptaService.save(chapCompta);

        int databaseSizeBeforeUpdate = chapComptaRepository.findAll().size();

        // Update the chapCompta
        ChapCompta updatedChapCompta = chapComptaRepository.findById(chapCompta.getId()).get();
        // Disconnect from session so that the updates on updatedChapCompta are not directly saved in db
        em.detach(updatedChapCompta);
        updatedChapCompta
            .numeroChapCompta(UPDATED_NUMERO_CHAP_COMPTA)
            .libelleChapCompta(UPDATED_LIBELLE_CHAP_COMPTA)
            .sensChapCompta(UPDATED_SENS_CHAP_COMPTA)
            .soldeChapCompta(UPDATED_SOLDE_CHAP_COMPTA)
            .cumulMouvDebitChapCompta(UPDATED_CUMUL_MOUV_DEBIT_CHAP_COMPTA)
            .cumulMouvCreditChapCompta(UPDATED_CUMUL_MOUV_CREDIT_CHAP_COMPTA)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restChapComptaMockMvc.perform(put("/api/chap-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChapCompta)))
            .andExpect(status().isOk());

        // Validate the ChapCompta in the database
        List<ChapCompta> chapComptaList = chapComptaRepository.findAll();
        assertThat(chapComptaList).hasSize(databaseSizeBeforeUpdate);
        ChapCompta testChapCompta = chapComptaList.get(chapComptaList.size() - 1);
        assertThat(testChapCompta.getNumeroChapCompta()).isEqualTo(UPDATED_NUMERO_CHAP_COMPTA);
        assertThat(testChapCompta.getLibelleChapCompta()).isEqualTo(UPDATED_LIBELLE_CHAP_COMPTA);
        assertThat(testChapCompta.getSensChapCompta()).isEqualTo(UPDATED_SENS_CHAP_COMPTA);
        assertThat(testChapCompta.getSoldeChapCompta()).isEqualTo(UPDATED_SOLDE_CHAP_COMPTA);
        assertThat(testChapCompta.getCumulMouvDebitChapCompta()).isEqualTo(UPDATED_CUMUL_MOUV_DEBIT_CHAP_COMPTA);
        assertThat(testChapCompta.getCumulMouvCreditChapCompta()).isEqualTo(UPDATED_CUMUL_MOUV_CREDIT_CHAP_COMPTA);
        assertThat(testChapCompta.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testChapCompta.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testChapCompta.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testChapCompta.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testChapCompta.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testChapCompta.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingChapCompta() throws Exception {
        int databaseSizeBeforeUpdate = chapComptaRepository.findAll().size();

        // Create the ChapCompta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChapComptaMockMvc.perform(put("/api/chap-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chapCompta)))
            .andExpect(status().isBadRequest());

        // Validate the ChapCompta in the database
        List<ChapCompta> chapComptaList = chapComptaRepository.findAll();
        assertThat(chapComptaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChapCompta() throws Exception {
        // Initialize the database
        chapComptaService.save(chapCompta);

        int databaseSizeBeforeDelete = chapComptaRepository.findAll().size();

        // Delete the chapCompta
        restChapComptaMockMvc.perform(delete("/api/chap-comptas/{id}", chapCompta.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChapCompta> chapComptaList = chapComptaRepository.findAll();
        assertThat(chapComptaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
