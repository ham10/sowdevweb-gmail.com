package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Medecin;
import com.projet.hpd.repository.MedecinRepository;
import com.projet.hpd.service.MedecinService;
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
 * Integration tests for the {@link MedecinResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class MedecinResourceIT {

    private static final String DEFAULT_NOM_MEDECIN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MEDECIN = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENRE_MEDECIN = "AAAAAAAAAA";
    private static final String UPDATED_GENRE_MEDECIN = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANCIENNETE = 1;
    private static final Integer UPDATED_ANCIENNETE = 2;

    private static final Integer DEFAULT_NUMERO_PIECE = 1;
    private static final Integer UPDATED_NUMERO_PIECE = 2;

    private static final String DEFAULT_GRADE_MEDECIN = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_MEDECIN = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_EMBAUCHE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EMBAUCHE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDITE = LocalDate.now(ZoneId.systemDefault());

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
    private MedecinRepository medecinRepository;

    @Autowired
    private MedecinService medecinService;

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

    private MockMvc restMedecinMockMvc;

    private Medecin medecin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedecinResource medecinResource = new MedecinResource(medecinService);
        this.restMedecinMockMvc = MockMvcBuilders.standaloneSetup(medecinResource)
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
    public static Medecin createEntity(EntityManager em) {
        Medecin medecin = new Medecin()
            .nomMedecin(DEFAULT_NOM_MEDECIN)
            .prenom(DEFAULT_PRENOM)
            .adresse(DEFAULT_ADRESSE)
            .email(DEFAULT_EMAIL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .genreMedecin(DEFAULT_GENRE_MEDECIN)
            .nationalite(DEFAULT_NATIONALITE)
            .telephone(DEFAULT_TELEPHONE)
            .anciennete(DEFAULT_ANCIENNETE)
            .numeroPiece(DEFAULT_NUMERO_PIECE)
            .gradeMedecin(DEFAULT_GRADE_MEDECIN)
            .specialite(DEFAULT_SPECIALITE)
            .dateEmbauche(DEFAULT_DATE_EMBAUCHE)
            .dateValidite(DEFAULT_DATE_VALIDITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return medecin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createUpdatedEntity(EntityManager em) {
        Medecin medecin = new Medecin()
            .nomMedecin(UPDATED_NOM_MEDECIN)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genreMedecin(UPDATED_GENRE_MEDECIN)
            .nationalite(UPDATED_NATIONALITE)
            .telephone(UPDATED_TELEPHONE)
            .anciennete(UPDATED_ANCIENNETE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .gradeMedecin(UPDATED_GRADE_MEDECIN)
            .specialite(UPDATED_SPECIALITE)
            .dateEmbauche(UPDATED_DATE_EMBAUCHE)
            .dateValidite(UPDATED_DATE_VALIDITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return medecin;
    }

    @BeforeEach
    public void initTest() {
        medecin = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedecin() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // Create the Medecin
        restMedecinMockMvc.perform(post("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isCreated());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate + 1);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getNomMedecin()).isEqualTo(DEFAULT_NOM_MEDECIN);
        assertThat(testMedecin.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMedecin.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMedecin.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMedecin.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testMedecin.getGenreMedecin()).isEqualTo(DEFAULT_GENRE_MEDECIN);
        assertThat(testMedecin.getNationalite()).isEqualTo(DEFAULT_NATIONALITE);
        assertThat(testMedecin.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMedecin.getAnciennete()).isEqualTo(DEFAULT_ANCIENNETE);
        assertThat(testMedecin.getNumeroPiece()).isEqualTo(DEFAULT_NUMERO_PIECE);
        assertThat(testMedecin.getGradeMedecin()).isEqualTo(DEFAULT_GRADE_MEDECIN);
        assertThat(testMedecin.getSpecialite()).isEqualTo(DEFAULT_SPECIALITE);
        assertThat(testMedecin.getDateEmbauche()).isEqualTo(DEFAULT_DATE_EMBAUCHE);
        assertThat(testMedecin.getDateValidite()).isEqualTo(DEFAULT_DATE_VALIDITE);
        assertThat(testMedecin.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testMedecin.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testMedecin.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testMedecin.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testMedecin.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createMedecinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // Create the Medecin with an existing ID
        medecin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedecinMockMvc.perform(post("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedecins() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get all the medecinList
        restMedecinMockMvc.perform(get("/api/medecins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medecin.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMedecin").value(hasItem(DEFAULT_NOM_MEDECIN)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].genreMedecin").value(hasItem(DEFAULT_GENRE_MEDECIN)))
            .andExpect(jsonPath("$.[*].nationalite").value(hasItem(DEFAULT_NATIONALITE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].anciennete").value(hasItem(DEFAULT_ANCIENNETE)))
            .andExpect(jsonPath("$.[*].numeroPiece").value(hasItem(DEFAULT_NUMERO_PIECE)))
            .andExpect(jsonPath("$.[*].gradeMedecin").value(hasItem(DEFAULT_GRADE_MEDECIN)))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE)))
            .andExpect(jsonPath("$.[*].dateEmbauche").value(hasItem(DEFAULT_DATE_EMBAUCHE.toString())))
            .andExpect(jsonPath("$.[*].dateValidite").value(hasItem(DEFAULT_DATE_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get the medecin
        restMedecinMockMvc.perform(get("/api/medecins/{id}", medecin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medecin.getId().intValue()))
            .andExpect(jsonPath("$.nomMedecin").value(DEFAULT_NOM_MEDECIN))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.genreMedecin").value(DEFAULT_GENRE_MEDECIN))
            .andExpect(jsonPath("$.nationalite").value(DEFAULT_NATIONALITE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.anciennete").value(DEFAULT_ANCIENNETE))
            .andExpect(jsonPath("$.numeroPiece").value(DEFAULT_NUMERO_PIECE))
            .andExpect(jsonPath("$.gradeMedecin").value(DEFAULT_GRADE_MEDECIN))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE))
            .andExpect(jsonPath("$.dateEmbauche").value(DEFAULT_DATE_EMBAUCHE.toString()))
            .andExpect(jsonPath("$.dateValidite").value(DEFAULT_DATE_VALIDITE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedecin() throws Exception {
        // Get the medecin
        restMedecinMockMvc.perform(get("/api/medecins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedecin() throws Exception {
        // Initialize the database
        medecinService.save(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin
        Medecin updatedMedecin = medecinRepository.findById(medecin.getId()).get();
        // Disconnect from session so that the updates on updatedMedecin are not directly saved in db
        em.detach(updatedMedecin);
        updatedMedecin
            .nomMedecin(UPDATED_NOM_MEDECIN)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genreMedecin(UPDATED_GENRE_MEDECIN)
            .nationalite(UPDATED_NATIONALITE)
            .telephone(UPDATED_TELEPHONE)
            .anciennete(UPDATED_ANCIENNETE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .gradeMedecin(UPDATED_GRADE_MEDECIN)
            .specialite(UPDATED_SPECIALITE)
            .dateEmbauche(UPDATED_DATE_EMBAUCHE)
            .dateValidite(UPDATED_DATE_VALIDITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restMedecinMockMvc.perform(put("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedecin)))
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getNomMedecin()).isEqualTo(UPDATED_NOM_MEDECIN);
        assertThat(testMedecin.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMedecin.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMedecin.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMedecin.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testMedecin.getGenreMedecin()).isEqualTo(UPDATED_GENRE_MEDECIN);
        assertThat(testMedecin.getNationalite()).isEqualTo(UPDATED_NATIONALITE);
        assertThat(testMedecin.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMedecin.getAnciennete()).isEqualTo(UPDATED_ANCIENNETE);
        assertThat(testMedecin.getNumeroPiece()).isEqualTo(UPDATED_NUMERO_PIECE);
        assertThat(testMedecin.getGradeMedecin()).isEqualTo(UPDATED_GRADE_MEDECIN);
        assertThat(testMedecin.getSpecialite()).isEqualTo(UPDATED_SPECIALITE);
        assertThat(testMedecin.getDateEmbauche()).isEqualTo(UPDATED_DATE_EMBAUCHE);
        assertThat(testMedecin.getDateValidite()).isEqualTo(UPDATED_DATE_VALIDITE);
        assertThat(testMedecin.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMedecin.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testMedecin.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testMedecin.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testMedecin.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Create the Medecin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedecinMockMvc.perform(put("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedecin() throws Exception {
        // Initialize the database
        medecinService.save(medecin);

        int databaseSizeBeforeDelete = medecinRepository.findAll().size();

        // Delete the medecin
        restMedecinMockMvc.perform(delete("/api/medecins/{id}", medecin.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
