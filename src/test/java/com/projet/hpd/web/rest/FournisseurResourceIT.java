package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Fournisseur;
import com.projet.hpd.repository.FournisseurRepository;
import com.projet.hpd.service.FournisseurService;
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
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FournisseurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NINEA = "AAAAAAAAAA";
    private static final String UPDATED_NINEA = "BBBBBBBBBB";

    private static final String DEFAULT_RC = "AAAAAAAAAA";
    private static final String UPDATED_RC = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurService fournisseurService;

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

    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FournisseurResource fournisseurResource = new FournisseurResource(fournisseurService);
        this.restFournisseurMockMvc = MockMvcBuilders.standaloneSetup(fournisseurResource)
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
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(DEFAULT_NOM)
            .statut(DEFAULT_STATUT)
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .ninea(DEFAULT_NINEA)
            .rc(DEFAULT_RC)
            .ville(DEFAULT_VILLE)
            .email(DEFAULT_EMAIL)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return fournisseur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(UPDATED_NOM)
            .statut(UPDATED_STATUT)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .ninea(UPDATED_NINEA)
            .rc(UPDATED_RC)
            .ville(UPDATED_VILLE)
            .email(UPDATED_EMAIL)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    public void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseur.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testFournisseur.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testFournisseur.getRc()).isEqualTo(DEFAULT_RC);
        assertThat(testFournisseur.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testFournisseur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFournisseur.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFournisseur.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFournisseur.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFournisseur.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFournisseur.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFournisseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc.perform(get("/api/fournisseurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA)))
            .andExpect(jsonPath("$.[*].rc").value(hasItem(DEFAULT_RC)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.ninea").value(DEFAULT_NINEA))
            .andExpect(jsonPath("$.rc").value(DEFAULT_RC))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFournisseur() throws Exception {
        // Initialize the database
        fournisseurService.save(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .nom(UPDATED_NOM)
            .statut(UPDATED_STATUT)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .ninea(UPDATED_NINEA)
            .rc(UPDATED_RC)
            .ville(UPDATED_VILLE)
            .email(UPDATED_EMAIL)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur)))
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseur.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFournisseur.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testFournisseur.getRc()).isEqualTo(UPDATED_RC);
        assertThat(testFournisseur.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFournisseur.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFournisseur.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFournisseur.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFournisseur.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Create the Fournisseur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurService.save(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc.perform(delete("/api/fournisseurs/{id}", fournisseur.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
