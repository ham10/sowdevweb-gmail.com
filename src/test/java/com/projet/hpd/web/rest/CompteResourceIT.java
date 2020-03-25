package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Compte;
import com.projet.hpd.repository.CompteRepository;
import com.projet.hpd.service.CompteService;
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
 * Integration tests for the {@link CompteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CompteResourceIT {

    private static final Integer DEFAULT_NUMERO_COMPTE = 1;
    private static final Integer UPDATED_NUMERO_COMPTE = 2;

    private static final String DEFAULT_LIBELLE_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMPTE = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE_COMPTE = 1D;
    private static final Double UPDATED_SOLDE_COMPTE = 2D;

    private static final String DEFAULT_SENS_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_SENS_COMPTE = "BBBBBBBBBB";

    private static final Double DEFAULT_CUMUL_MOUV_DEBIT = 1D;
    private static final Double UPDATED_CUMUL_MOUV_DEBIT = 2D;

    private static final Double DEFAULT_CUMUL_MOUV_CREDIT = 1D;
    private static final Double UPDATED_CUMUL_MOUV_CREDIT = 2D;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DATE_DELETED = 1;
    private static final Integer UPDATED_DATE_DELETED = 2;

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteService compteService;

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

    private MockMvc restCompteMockMvc;

    private Compte compte;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteResource compteResource = new CompteResource(compteService);
        this.restCompteMockMvc = MockMvcBuilders.standaloneSetup(compteResource)
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
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .libelleCompte(DEFAULT_LIBELLE_COMPTE)
            .soldeCompte(DEFAULT_SOLDE_COMPTE)
            .sensCompte(DEFAULT_SENS_COMPTE)
            .cumulMouvDebit(DEFAULT_CUMUL_MOUV_DEBIT)
            .cumulMouvCredit(DEFAULT_CUMUL_MOUV_CREDIT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return compte;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createUpdatedEntity(EntityManager em) {
        Compte compte = new Compte()
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .libelleCompte(UPDATED_LIBELLE_COMPTE)
            .soldeCompte(UPDATED_SOLDE_COMPTE)
            .sensCompte(UPDATED_SENS_COMPTE)
            .cumulMouvDebit(UPDATED_CUMUL_MOUV_DEBIT)
            .cumulMouvCredit(UPDATED_CUMUL_MOUV_CREDIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return compte;
    }

    @BeforeEach
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testCompte.getLibelleCompte()).isEqualTo(DEFAULT_LIBELLE_COMPTE);
        assertThat(testCompte.getSoldeCompte()).isEqualTo(DEFAULT_SOLDE_COMPTE);
        assertThat(testCompte.getSensCompte()).isEqualTo(DEFAULT_SENS_COMPTE);
        assertThat(testCompte.getCumulMouvDebit()).isEqualTo(DEFAULT_CUMUL_MOUV_DEBIT);
        assertThat(testCompte.getCumulMouvCredit()).isEqualTo(DEFAULT_CUMUL_MOUV_CREDIT);
        assertThat(testCompte.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCompte.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCompte.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCompte.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCompte.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCompte.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE)))
            .andExpect(jsonPath("$.[*].libelleCompte").value(hasItem(DEFAULT_LIBELLE_COMPTE)))
            .andExpect(jsonPath("$.[*].soldeCompte").value(hasItem(DEFAULT_SOLDE_COMPTE.doubleValue())))
            .andExpect(jsonPath("$.[*].sensCompte").value(hasItem(DEFAULT_SENS_COMPTE)))
            .andExpect(jsonPath("$.[*].cumulMouvDebit").value(hasItem(DEFAULT_CUMUL_MOUV_DEBIT.doubleValue())))
            .andExpect(jsonPath("$.[*].cumulMouvCredit").value(hasItem(DEFAULT_CUMUL_MOUV_CREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED)))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE))
            .andExpect(jsonPath("$.libelleCompte").value(DEFAULT_LIBELLE_COMPTE))
            .andExpect(jsonPath("$.soldeCompte").value(DEFAULT_SOLDE_COMPTE.doubleValue()))
            .andExpect(jsonPath("$.sensCompte").value(DEFAULT_SENS_COMPTE))
            .andExpect(jsonPath("$.cumulMouvDebit").value(DEFAULT_CUMUL_MOUV_DEBIT.doubleValue()))
            .andExpect(jsonPath("$.cumulMouvCredit").value(DEFAULT_CUMUL_MOUV_CREDIT.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteService.save(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .libelleCompte(UPDATED_LIBELLE_COMPTE)
            .soldeCompte(UPDATED_SOLDE_COMPTE)
            .sensCompte(UPDATED_SENS_COMPTE)
            .cumulMouvDebit(UPDATED_CUMUL_MOUV_DEBIT)
            .cumulMouvCredit(UPDATED_CUMUL_MOUV_CREDIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompte)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testCompte.getLibelleCompte()).isEqualTo(UPDATED_LIBELLE_COMPTE);
        assertThat(testCompte.getSoldeCompte()).isEqualTo(UPDATED_SOLDE_COMPTE);
        assertThat(testCompte.getSensCompte()).isEqualTo(UPDATED_SENS_COMPTE);
        assertThat(testCompte.getCumulMouvDebit()).isEqualTo(UPDATED_CUMUL_MOUV_DEBIT);
        assertThat(testCompte.getCumulMouvCredit()).isEqualTo(UPDATED_CUMUL_MOUV_CREDIT);
        assertThat(testCompte.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCompte.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCompte.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCompte.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCompte.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCompte.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteService.save(compte);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Delete the compte
        restCompteMockMvc.perform(delete("/api/comptes/{id}", compte.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
