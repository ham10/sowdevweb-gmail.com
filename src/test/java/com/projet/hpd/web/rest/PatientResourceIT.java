package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Patient;
import com.projet.hpd.repository.PatientRepository;
import com.projet.hpd.service.PatientService;
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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link PatientResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class PatientResourceIT {

    private static final String DEFAULT_CODE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_BARRE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BARRE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_ENTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTIER = "AAAAAAAAAA";
    private static final String UPDATED_QUARTIER = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_SITUATION_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_SITUATION_SOCIALE = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE = 1D;
    private static final Double UPDATED_SOLDE = 2D;

    private static final String DEFAULT_CARTE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_CARTE_PATIENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BLOQUE = false;
    private static final Boolean UPDATED_BLOQUE = true;

    private static final LocalDate DEFAULT_DATE_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIF_BLOCAGE = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_BLOCAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_PERE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_PERE_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MERE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MERE_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MERE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MERE_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF_ADMISSION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_ADMISSION = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONNE_A_CONTACTER = "AAAAAAAAAA";
    private static final String UPDATED_PERSONNE_A_CONTACTER = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_PERS_A_CONTACTER = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_PERS_A_CONTACTER = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PERS_A_CONTACTER = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PERS_A_CONTACTER = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_PARENTE_PERS_A_CONTACTER = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_PARENTE_PERS_A_CONTACTER = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ACCOMPAGNANT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ACCOMPAGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_ACCOMPAGNANT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_ACCOMPAGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_ACCOMPAGNANT = "AAAAAAAAAA";
    private static final String UPDATED_TEL_ACCOMPAGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_HABITUDE_DE_VIE = "AAAAAAAAAA";
    private static final String UPDATED_HABITUDE_DE_VIE = "BBBBBBBBBB";

    private static final String DEFAULT_PHYSIO_PATHOLOGIQUE = "AAAAAAAAAA";
    private static final String UPDATED_PHYSIO_PATHOLOGIQUE = "BBBBBBBBBB";

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
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

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

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientService);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .codePatient(DEFAULT_CODE_PATIENT)
            .nomPatient(DEFAULT_NOM_PATIENT)
            .prenom(DEFAULT_PRENOM)
            .adresse(DEFAULT_ADRESSE)
            .email(DEFAULT_EMAIL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .genre(DEFAULT_GENRE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .password(DEFAULT_PASSWORD)
            .telephone(DEFAULT_TELEPHONE)
            .numeroPiece(DEFAULT_NUMERO_PIECE)
            .codeBarre(DEFAULT_CODE_BARRE)
            .entreprise(DEFAULT_ENTREPRISE)
            .ville(DEFAULT_VILLE)
            .quartier(DEFAULT_QUARTIER)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .fonctionPatient(DEFAULT_FONCTION_PATIENT)
            .situationSociale(DEFAULT_SITUATION_SOCIALE)
            .solde(DEFAULT_SOLDE)
            .cartePatient(DEFAULT_CARTE_PATIENT)
            .bloque(DEFAULT_BLOQUE)
            .dateValidite(DEFAULT_DATE_VALIDITE)
            .motifBlocage(DEFAULT_MOTIF_BLOCAGE)
            .prenomPerePatient(DEFAULT_PRENOM_PERE_PATIENT)
            .nomMerePatient(DEFAULT_NOM_MERE_PATIENT)
            .prenomMerePatient(DEFAULT_PRENOM_MERE_PATIENT)
            .motifAdmission(DEFAULT_MOTIF_ADMISSION)
            .personneAContacter(DEFAULT_PERSONNE_A_CONTACTER)
            .adressePersAContacter(DEFAULT_ADRESSE_PERS_A_CONTACTER)
            .telPersAContacter(DEFAULT_TEL_PERS_A_CONTACTER)
            .lienParentePersAContacter(DEFAULT_LIEN_PARENTE_PERS_A_CONTACTER)
            .nomAccompagnant(DEFAULT_NOM_ACCOMPAGNANT)
            .prenomAccompagnant(DEFAULT_PRENOM_ACCOMPAGNANT)
            .telAccompagnant(DEFAULT_TEL_ACCOMPAGNANT)
            .habitudeDeVie(DEFAULT_HABITUDE_DE_VIE)
            .physioPathologique(DEFAULT_PHYSIO_PATHOLOGIQUE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return patient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .codePatient(UPDATED_CODE_PATIENT)
            .nomPatient(UPDATED_NOM_PATIENT)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genre(UPDATED_GENRE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .password(UPDATED_PASSWORD)
            .telephone(UPDATED_TELEPHONE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .codeBarre(UPDATED_CODE_BARRE)
            .entreprise(UPDATED_ENTREPRISE)
            .ville(UPDATED_VILLE)
            .quartier(UPDATED_QUARTIER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .fonctionPatient(UPDATED_FONCTION_PATIENT)
            .situationSociale(UPDATED_SITUATION_SOCIALE)
            .solde(UPDATED_SOLDE)
            .cartePatient(UPDATED_CARTE_PATIENT)
            .bloque(UPDATED_BLOQUE)
            .dateValidite(UPDATED_DATE_VALIDITE)
            .motifBlocage(UPDATED_MOTIF_BLOCAGE)
            .prenomPerePatient(UPDATED_PRENOM_PERE_PATIENT)
            .nomMerePatient(UPDATED_NOM_MERE_PATIENT)
            .prenomMerePatient(UPDATED_PRENOM_MERE_PATIENT)
            .motifAdmission(UPDATED_MOTIF_ADMISSION)
            .personneAContacter(UPDATED_PERSONNE_A_CONTACTER)
            .adressePersAContacter(UPDATED_ADRESSE_PERS_A_CONTACTER)
            .telPersAContacter(UPDATED_TEL_PERS_A_CONTACTER)
            .lienParentePersAContacter(UPDATED_LIEN_PARENTE_PERS_A_CONTACTER)
            .nomAccompagnant(UPDATED_NOM_ACCOMPAGNANT)
            .prenomAccompagnant(UPDATED_PRENOM_ACCOMPAGNANT)
            .telAccompagnant(UPDATED_TEL_ACCOMPAGNANT)
            .habitudeDeVie(UPDATED_HABITUDE_DE_VIE)
            .physioPathologique(UPDATED_PHYSIO_PATHOLOGIQUE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getCodePatient()).isEqualTo(DEFAULT_CODE_PATIENT);
        assertThat(testPatient.getNomPatient()).isEqualTo(DEFAULT_NOM_PATIENT);
        assertThat(testPatient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPatient.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testPatient.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testPatient.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPatient.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPatient.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPatient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPatient.getNumeroPiece()).isEqualTo(DEFAULT_NUMERO_PIECE);
        assertThat(testPatient.getCodeBarre()).isEqualTo(DEFAULT_CODE_BARRE);
        assertThat(testPatient.getEntreprise()).isEqualTo(DEFAULT_ENTREPRISE);
        assertThat(testPatient.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testPatient.getQuartier()).isEqualTo(DEFAULT_QUARTIER);
        assertThat(testPatient.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPatient.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPatient.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testPatient.getFonctionPatient()).isEqualTo(DEFAULT_FONCTION_PATIENT);
        assertThat(testPatient.getSituationSociale()).isEqualTo(DEFAULT_SITUATION_SOCIALE);
        assertThat(testPatient.getSolde()).isEqualTo(DEFAULT_SOLDE);
        assertThat(testPatient.getCartePatient()).isEqualTo(DEFAULT_CARTE_PATIENT);
        assertThat(testPatient.isBloque()).isEqualTo(DEFAULT_BLOQUE);
        assertThat(testPatient.getDateValidite()).isEqualTo(DEFAULT_DATE_VALIDITE);
        assertThat(testPatient.getMotifBlocage()).isEqualTo(DEFAULT_MOTIF_BLOCAGE);
        assertThat(testPatient.getPrenomPerePatient()).isEqualTo(DEFAULT_PRENOM_PERE_PATIENT);
        assertThat(testPatient.getNomMerePatient()).isEqualTo(DEFAULT_NOM_MERE_PATIENT);
        assertThat(testPatient.getPrenomMerePatient()).isEqualTo(DEFAULT_PRENOM_MERE_PATIENT);
        assertThat(testPatient.getMotifAdmission()).isEqualTo(DEFAULT_MOTIF_ADMISSION);
        assertThat(testPatient.getPersonneAContacter()).isEqualTo(DEFAULT_PERSONNE_A_CONTACTER);
        assertThat(testPatient.getAdressePersAContacter()).isEqualTo(DEFAULT_ADRESSE_PERS_A_CONTACTER);
        assertThat(testPatient.getTelPersAContacter()).isEqualTo(DEFAULT_TEL_PERS_A_CONTACTER);
        assertThat(testPatient.getLienParentePersAContacter()).isEqualTo(DEFAULT_LIEN_PARENTE_PERS_A_CONTACTER);
        assertThat(testPatient.getNomAccompagnant()).isEqualTo(DEFAULT_NOM_ACCOMPAGNANT);
        assertThat(testPatient.getPrenomAccompagnant()).isEqualTo(DEFAULT_PRENOM_ACCOMPAGNANT);
        assertThat(testPatient.getTelAccompagnant()).isEqualTo(DEFAULT_TEL_ACCOMPAGNANT);
        assertThat(testPatient.getHabitudeDeVie()).isEqualTo(DEFAULT_HABITUDE_DE_VIE);
        assertThat(testPatient.getPhysioPathologique()).isEqualTo(DEFAULT_PHYSIO_PATHOLOGIQUE);
        assertThat(testPatient.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPatient.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testPatient.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testPatient.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testPatient.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodePatientIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setCodePatient(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomPatientIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setNomPatient(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setPrenom(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setAdresse(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setEmail(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setDateNaissance(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenreIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setGenre(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setTelephone(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePatient").value(hasItem(DEFAULT_CODE_PATIENT)))
            .andExpect(jsonPath("$.[*].nomPatient").value(hasItem(DEFAULT_NOM_PATIENT)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].numeroPiece").value(hasItem(DEFAULT_NUMERO_PIECE)))
            .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].entreprise").value(hasItem(DEFAULT_ENTREPRISE)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].quartier").value(hasItem(DEFAULT_QUARTIER)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].fonctionPatient").value(hasItem(DEFAULT_FONCTION_PATIENT)))
            .andExpect(jsonPath("$.[*].situationSociale").value(hasItem(DEFAULT_SITUATION_SOCIALE)))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cartePatient").value(hasItem(DEFAULT_CARTE_PATIENT)))
            .andExpect(jsonPath("$.[*].bloque").value(hasItem(DEFAULT_BLOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidite").value(hasItem(DEFAULT_DATE_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].motifBlocage").value(hasItem(DEFAULT_MOTIF_BLOCAGE)))
            .andExpect(jsonPath("$.[*].prenomPerePatient").value(hasItem(DEFAULT_PRENOM_PERE_PATIENT)))
            .andExpect(jsonPath("$.[*].nomMerePatient").value(hasItem(DEFAULT_NOM_MERE_PATIENT)))
            .andExpect(jsonPath("$.[*].prenomMerePatient").value(hasItem(DEFAULT_PRENOM_MERE_PATIENT)))
            .andExpect(jsonPath("$.[*].motifAdmission").value(hasItem(DEFAULT_MOTIF_ADMISSION)))
            .andExpect(jsonPath("$.[*].personneAContacter").value(hasItem(DEFAULT_PERSONNE_A_CONTACTER)))
            .andExpect(jsonPath("$.[*].adressePersAContacter").value(hasItem(DEFAULT_ADRESSE_PERS_A_CONTACTER)))
            .andExpect(jsonPath("$.[*].telPersAContacter").value(hasItem(DEFAULT_TEL_PERS_A_CONTACTER)))
            .andExpect(jsonPath("$.[*].lienParentePersAContacter").value(hasItem(DEFAULT_LIEN_PARENTE_PERS_A_CONTACTER)))
            .andExpect(jsonPath("$.[*].nomAccompagnant").value(hasItem(DEFAULT_NOM_ACCOMPAGNANT)))
            .andExpect(jsonPath("$.[*].prenomAccompagnant").value(hasItem(DEFAULT_PRENOM_ACCOMPAGNANT)))
            .andExpect(jsonPath("$.[*].telAccompagnant").value(hasItem(DEFAULT_TEL_ACCOMPAGNANT)))
            .andExpect(jsonPath("$.[*].habitudeDeVie").value(hasItem(DEFAULT_HABITUDE_DE_VIE)))
            .andExpect(jsonPath("$.[*].physioPathologique").value(hasItem(DEFAULT_PHYSIO_PATHOLOGIQUE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.codePatient").value(DEFAULT_CODE_PATIENT))
            .andExpect(jsonPath("$.nomPatient").value(DEFAULT_NOM_PATIENT))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.numeroPiece").value(DEFAULT_NUMERO_PIECE))
            .andExpect(jsonPath("$.codeBarre").value(DEFAULT_CODE_BARRE))
            .andExpect(jsonPath("$.entreprise").value(DEFAULT_ENTREPRISE))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.quartier").value(DEFAULT_QUARTIER))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.fonctionPatient").value(DEFAULT_FONCTION_PATIENT))
            .andExpect(jsonPath("$.situationSociale").value(DEFAULT_SITUATION_SOCIALE))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()))
            .andExpect(jsonPath("$.cartePatient").value(DEFAULT_CARTE_PATIENT))
            .andExpect(jsonPath("$.bloque").value(DEFAULT_BLOQUE.booleanValue()))
            .andExpect(jsonPath("$.dateValidite").value(DEFAULT_DATE_VALIDITE.toString()))
            .andExpect(jsonPath("$.motifBlocage").value(DEFAULT_MOTIF_BLOCAGE))
            .andExpect(jsonPath("$.prenomPerePatient").value(DEFAULT_PRENOM_PERE_PATIENT))
            .andExpect(jsonPath("$.nomMerePatient").value(DEFAULT_NOM_MERE_PATIENT))
            .andExpect(jsonPath("$.prenomMerePatient").value(DEFAULT_PRENOM_MERE_PATIENT))
            .andExpect(jsonPath("$.motifAdmission").value(DEFAULT_MOTIF_ADMISSION))
            .andExpect(jsonPath("$.personneAContacter").value(DEFAULT_PERSONNE_A_CONTACTER))
            .andExpect(jsonPath("$.adressePersAContacter").value(DEFAULT_ADRESSE_PERS_A_CONTACTER))
            .andExpect(jsonPath("$.telPersAContacter").value(DEFAULT_TEL_PERS_A_CONTACTER))
            .andExpect(jsonPath("$.lienParentePersAContacter").value(DEFAULT_LIEN_PARENTE_PERS_A_CONTACTER))
            .andExpect(jsonPath("$.nomAccompagnant").value(DEFAULT_NOM_ACCOMPAGNANT))
            .andExpect(jsonPath("$.prenomAccompagnant").value(DEFAULT_PRENOM_ACCOMPAGNANT))
            .andExpect(jsonPath("$.telAccompagnant").value(DEFAULT_TEL_ACCOMPAGNANT))
            .andExpect(jsonPath("$.habitudeDeVie").value(DEFAULT_HABITUDE_DE_VIE))
            .andExpect(jsonPath("$.physioPathologique").value(DEFAULT_PHYSIO_PATHOLOGIQUE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .codePatient(UPDATED_CODE_PATIENT)
            .nomPatient(UPDATED_NOM_PATIENT)
            .prenom(UPDATED_PRENOM)
            .adresse(UPDATED_ADRESSE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .genre(UPDATED_GENRE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .password(UPDATED_PASSWORD)
            .telephone(UPDATED_TELEPHONE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .codeBarre(UPDATED_CODE_BARRE)
            .entreprise(UPDATED_ENTREPRISE)
            .ville(UPDATED_VILLE)
            .quartier(UPDATED_QUARTIER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .fonctionPatient(UPDATED_FONCTION_PATIENT)
            .situationSociale(UPDATED_SITUATION_SOCIALE)
            .solde(UPDATED_SOLDE)
            .cartePatient(UPDATED_CARTE_PATIENT)
            .bloque(UPDATED_BLOQUE)
            .dateValidite(UPDATED_DATE_VALIDITE)
            .motifBlocage(UPDATED_MOTIF_BLOCAGE)
            .prenomPerePatient(UPDATED_PRENOM_PERE_PATIENT)
            .nomMerePatient(UPDATED_NOM_MERE_PATIENT)
            .prenomMerePatient(UPDATED_PRENOM_MERE_PATIENT)
            .motifAdmission(UPDATED_MOTIF_ADMISSION)
            .personneAContacter(UPDATED_PERSONNE_A_CONTACTER)
            .adressePersAContacter(UPDATED_ADRESSE_PERS_A_CONTACTER)
            .telPersAContacter(UPDATED_TEL_PERS_A_CONTACTER)
            .lienParentePersAContacter(UPDATED_LIEN_PARENTE_PERS_A_CONTACTER)
            .nomAccompagnant(UPDATED_NOM_ACCOMPAGNANT)
            .prenomAccompagnant(UPDATED_PRENOM_ACCOMPAGNANT)
            .telAccompagnant(UPDATED_TEL_ACCOMPAGNANT)
            .habitudeDeVie(UPDATED_HABITUDE_DE_VIE)
            .physioPathologique(UPDATED_PHYSIO_PATHOLOGIQUE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getCodePatient()).isEqualTo(UPDATED_CODE_PATIENT);
        assertThat(testPatient.getNomPatient()).isEqualTo(UPDATED_NOM_PATIENT);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testPatient.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testPatient.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPatient.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPatient.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPatient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPatient.getNumeroPiece()).isEqualTo(UPDATED_NUMERO_PIECE);
        assertThat(testPatient.getCodeBarre()).isEqualTo(UPDATED_CODE_BARRE);
        assertThat(testPatient.getEntreprise()).isEqualTo(UPDATED_ENTREPRISE);
        assertThat(testPatient.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testPatient.getQuartier()).isEqualTo(UPDATED_QUARTIER);
        assertThat(testPatient.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPatient.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPatient.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testPatient.getFonctionPatient()).isEqualTo(UPDATED_FONCTION_PATIENT);
        assertThat(testPatient.getSituationSociale()).isEqualTo(UPDATED_SITUATION_SOCIALE);
        assertThat(testPatient.getSolde()).isEqualTo(UPDATED_SOLDE);
        assertThat(testPatient.getCartePatient()).isEqualTo(UPDATED_CARTE_PATIENT);
        assertThat(testPatient.isBloque()).isEqualTo(UPDATED_BLOQUE);
        assertThat(testPatient.getDateValidite()).isEqualTo(UPDATED_DATE_VALIDITE);
        assertThat(testPatient.getMotifBlocage()).isEqualTo(UPDATED_MOTIF_BLOCAGE);
        assertThat(testPatient.getPrenomPerePatient()).isEqualTo(UPDATED_PRENOM_PERE_PATIENT);
        assertThat(testPatient.getNomMerePatient()).isEqualTo(UPDATED_NOM_MERE_PATIENT);
        assertThat(testPatient.getPrenomMerePatient()).isEqualTo(UPDATED_PRENOM_MERE_PATIENT);
        assertThat(testPatient.getMotifAdmission()).isEqualTo(UPDATED_MOTIF_ADMISSION);
        assertThat(testPatient.getPersonneAContacter()).isEqualTo(UPDATED_PERSONNE_A_CONTACTER);
        assertThat(testPatient.getAdressePersAContacter()).isEqualTo(UPDATED_ADRESSE_PERS_A_CONTACTER);
        assertThat(testPatient.getTelPersAContacter()).isEqualTo(UPDATED_TEL_PERS_A_CONTACTER);
        assertThat(testPatient.getLienParentePersAContacter()).isEqualTo(UPDATED_LIEN_PARENTE_PERS_A_CONTACTER);
        assertThat(testPatient.getNomAccompagnant()).isEqualTo(UPDATED_NOM_ACCOMPAGNANT);
        assertThat(testPatient.getPrenomAccompagnant()).isEqualTo(UPDATED_PRENOM_ACCOMPAGNANT);
        assertThat(testPatient.getTelAccompagnant()).isEqualTo(UPDATED_TEL_ACCOMPAGNANT);
        assertThat(testPatient.getHabitudeDeVie()).isEqualTo(UPDATED_HABITUDE_DE_VIE);
        assertThat(testPatient.getPhysioPathologique()).isEqualTo(UPDATED_PHYSIO_PATHOLOGIQUE);
        assertThat(testPatient.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPatient.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testPatient.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testPatient.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testPatient.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
