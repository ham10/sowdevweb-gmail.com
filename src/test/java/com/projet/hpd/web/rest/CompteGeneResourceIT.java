package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.CompteGene;
import com.projet.hpd.repository.CompteGeneRepository;
import com.projet.hpd.service.CompteGeneService;
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
 * Integration tests for the {@link CompteGeneResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CompteGeneResourceIT {

    private static final Integer DEFAULT_NUMERO_COMPTE_GENE = 1;
    private static final Integer UPDATED_NUMERO_COMPTE_GENE = 2;

    private static final String DEFAULT_LIBELLE_COMPTE_GENE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMPTE_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_SENS_COMPTE_GENE = "AAAAAAAAAA";
    private static final String UPDATED_SENS_COMPTE_GENE = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE_COMPTE_GENE = 1D;
    private static final Double UPDATED_SOLDE_COMPTE_GENE = 2D;

    private static final Double DEFAULT_CUMUL_MOUV_DEBIT_COMPTE_GENE = 1D;
    private static final Double UPDATED_CUMUL_MOUV_DEBIT_COMPTE_GENE = 2D;

    private static final Double DEFAULT_CUMUL_MOUV_CREDIT_COMPTE_GENE = 1D;
    private static final Double UPDATED_CUMUL_MOUV_CREDIT_COMPTE_GENE = 2D;

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
    private CompteGeneRepository compteGeneRepository;

    @Autowired
    private CompteGeneService compteGeneService;

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

    private MockMvc restCompteGeneMockMvc;

    private CompteGene compteGene;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteGeneResource compteGeneResource = new CompteGeneResource(compteGeneService);
        this.restCompteGeneMockMvc = MockMvcBuilders.standaloneSetup(compteGeneResource)
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
    public static CompteGene createEntity(EntityManager em) {
        CompteGene compteGene = new CompteGene()
            .numeroCompteGene(DEFAULT_NUMERO_COMPTE_GENE)
            .libelleCompteGene(DEFAULT_LIBELLE_COMPTE_GENE)
            .sensCompteGene(DEFAULT_SENS_COMPTE_GENE)
            .soldeCompteGene(DEFAULT_SOLDE_COMPTE_GENE)
            .cumulMouvDebitCompteGene(DEFAULT_CUMUL_MOUV_DEBIT_COMPTE_GENE)
            .cumulMouvCreditCompteGene(DEFAULT_CUMUL_MOUV_CREDIT_COMPTE_GENE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return compteGene;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteGene createUpdatedEntity(EntityManager em) {
        CompteGene compteGene = new CompteGene()
            .numeroCompteGene(UPDATED_NUMERO_COMPTE_GENE)
            .libelleCompteGene(UPDATED_LIBELLE_COMPTE_GENE)
            .sensCompteGene(UPDATED_SENS_COMPTE_GENE)
            .soldeCompteGene(UPDATED_SOLDE_COMPTE_GENE)
            .cumulMouvDebitCompteGene(UPDATED_CUMUL_MOUV_DEBIT_COMPTE_GENE)
            .cumulMouvCreditCompteGene(UPDATED_CUMUL_MOUV_CREDIT_COMPTE_GENE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return compteGene;
    }

    @BeforeEach
    public void initTest() {
        compteGene = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompteGene() throws Exception {
        int databaseSizeBeforeCreate = compteGeneRepository.findAll().size();

        // Create the CompteGene
        restCompteGeneMockMvc.perform(post("/api/compte-genes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteGene)))
            .andExpect(status().isCreated());

        // Validate the CompteGene in the database
        List<CompteGene> compteGeneList = compteGeneRepository.findAll();
        assertThat(compteGeneList).hasSize(databaseSizeBeforeCreate + 1);
        CompteGene testCompteGene = compteGeneList.get(compteGeneList.size() - 1);
        assertThat(testCompteGene.getNumeroCompteGene()).isEqualTo(DEFAULT_NUMERO_COMPTE_GENE);
        assertThat(testCompteGene.getLibelleCompteGene()).isEqualTo(DEFAULT_LIBELLE_COMPTE_GENE);
        assertThat(testCompteGene.getSensCompteGene()).isEqualTo(DEFAULT_SENS_COMPTE_GENE);
        assertThat(testCompteGene.getSoldeCompteGene()).isEqualTo(DEFAULT_SOLDE_COMPTE_GENE);
        assertThat(testCompteGene.getCumulMouvDebitCompteGene()).isEqualTo(DEFAULT_CUMUL_MOUV_DEBIT_COMPTE_GENE);
        assertThat(testCompteGene.getCumulMouvCreditCompteGene()).isEqualTo(DEFAULT_CUMUL_MOUV_CREDIT_COMPTE_GENE);
        assertThat(testCompteGene.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCompteGene.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCompteGene.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCompteGene.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCompteGene.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCompteGene.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCompteGeneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteGeneRepository.findAll().size();

        // Create the CompteGene with an existing ID
        compteGene.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteGeneMockMvc.perform(post("/api/compte-genes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteGene)))
            .andExpect(status().isBadRequest());

        // Validate the CompteGene in the database
        List<CompteGene> compteGeneList = compteGeneRepository.findAll();
        assertThat(compteGeneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompteGenes() throws Exception {
        // Initialize the database
        compteGeneRepository.saveAndFlush(compteGene);

        // Get all the compteGeneList
        restCompteGeneMockMvc.perform(get("/api/compte-genes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteGene.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCompteGene").value(hasItem(DEFAULT_NUMERO_COMPTE_GENE)))
            .andExpect(jsonPath("$.[*].libelleCompteGene").value(hasItem(DEFAULT_LIBELLE_COMPTE_GENE)))
            .andExpect(jsonPath("$.[*].sensCompteGene").value(hasItem(DEFAULT_SENS_COMPTE_GENE)))
            .andExpect(jsonPath("$.[*].soldeCompteGene").value(hasItem(DEFAULT_SOLDE_COMPTE_GENE.doubleValue())))
            .andExpect(jsonPath("$.[*].cumulMouvDebitCompteGene").value(hasItem(DEFAULT_CUMUL_MOUV_DEBIT_COMPTE_GENE.doubleValue())))
            .andExpect(jsonPath("$.[*].cumulMouvCreditCompteGene").value(hasItem(DEFAULT_CUMUL_MOUV_CREDIT_COMPTE_GENE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompteGene() throws Exception {
        // Initialize the database
        compteGeneRepository.saveAndFlush(compteGene);

        // Get the compteGene
        restCompteGeneMockMvc.perform(get("/api/compte-genes/{id}", compteGene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compteGene.getId().intValue()))
            .andExpect(jsonPath("$.numeroCompteGene").value(DEFAULT_NUMERO_COMPTE_GENE))
            .andExpect(jsonPath("$.libelleCompteGene").value(DEFAULT_LIBELLE_COMPTE_GENE))
            .andExpect(jsonPath("$.sensCompteGene").value(DEFAULT_SENS_COMPTE_GENE))
            .andExpect(jsonPath("$.soldeCompteGene").value(DEFAULT_SOLDE_COMPTE_GENE.doubleValue()))
            .andExpect(jsonPath("$.cumulMouvDebitCompteGene").value(DEFAULT_CUMUL_MOUV_DEBIT_COMPTE_GENE.doubleValue()))
            .andExpect(jsonPath("$.cumulMouvCreditCompteGene").value(DEFAULT_CUMUL_MOUV_CREDIT_COMPTE_GENE.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompteGene() throws Exception {
        // Get the compteGene
        restCompteGeneMockMvc.perform(get("/api/compte-genes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompteGene() throws Exception {
        // Initialize the database
        compteGeneService.save(compteGene);

        int databaseSizeBeforeUpdate = compteGeneRepository.findAll().size();

        // Update the compteGene
        CompteGene updatedCompteGene = compteGeneRepository.findById(compteGene.getId()).get();
        // Disconnect from session so that the updates on updatedCompteGene are not directly saved in db
        em.detach(updatedCompteGene);
        updatedCompteGene
            .numeroCompteGene(UPDATED_NUMERO_COMPTE_GENE)
            .libelleCompteGene(UPDATED_LIBELLE_COMPTE_GENE)
            .sensCompteGene(UPDATED_SENS_COMPTE_GENE)
            .soldeCompteGene(UPDATED_SOLDE_COMPTE_GENE)
            .cumulMouvDebitCompteGene(UPDATED_CUMUL_MOUV_DEBIT_COMPTE_GENE)
            .cumulMouvCreditCompteGene(UPDATED_CUMUL_MOUV_CREDIT_COMPTE_GENE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCompteGeneMockMvc.perform(put("/api/compte-genes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompteGene)))
            .andExpect(status().isOk());

        // Validate the CompteGene in the database
        List<CompteGene> compteGeneList = compteGeneRepository.findAll();
        assertThat(compteGeneList).hasSize(databaseSizeBeforeUpdate);
        CompteGene testCompteGene = compteGeneList.get(compteGeneList.size() - 1);
        assertThat(testCompteGene.getNumeroCompteGene()).isEqualTo(UPDATED_NUMERO_COMPTE_GENE);
        assertThat(testCompteGene.getLibelleCompteGene()).isEqualTo(UPDATED_LIBELLE_COMPTE_GENE);
        assertThat(testCompteGene.getSensCompteGene()).isEqualTo(UPDATED_SENS_COMPTE_GENE);
        assertThat(testCompteGene.getSoldeCompteGene()).isEqualTo(UPDATED_SOLDE_COMPTE_GENE);
        assertThat(testCompteGene.getCumulMouvDebitCompteGene()).isEqualTo(UPDATED_CUMUL_MOUV_DEBIT_COMPTE_GENE);
        assertThat(testCompteGene.getCumulMouvCreditCompteGene()).isEqualTo(UPDATED_CUMUL_MOUV_CREDIT_COMPTE_GENE);
        assertThat(testCompteGene.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCompteGene.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCompteGene.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCompteGene.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCompteGene.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCompteGene.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCompteGene() throws Exception {
        int databaseSizeBeforeUpdate = compteGeneRepository.findAll().size();

        // Create the CompteGene

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteGeneMockMvc.perform(put("/api/compte-genes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteGene)))
            .andExpect(status().isBadRequest());

        // Validate the CompteGene in the database
        List<CompteGene> compteGeneList = compteGeneRepository.findAll();
        assertThat(compteGeneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompteGene() throws Exception {
        // Initialize the database
        compteGeneService.save(compteGene);

        int databaseSizeBeforeDelete = compteGeneRepository.findAll().size();

        // Delete the compteGene
        restCompteGeneMockMvc.perform(delete("/api/compte-genes/{id}", compteGene.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompteGene> compteGeneList = compteGeneRepository.findAll();
        assertThat(compteGeneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
