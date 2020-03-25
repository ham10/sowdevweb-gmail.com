package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Hospitalisation;
import com.projet.hpd.repository.HospitalisationRepository;
import com.projet.hpd.service.HospitalisationService;
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
 * Integration tests for the {@link HospitalisationResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class HospitalisationResourceIT {

    private static final LocalDate DEFAULT_DATE_ENTRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTRE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_MODE_SORTIE = "AAAAAAAAAA";
    private static final String UPDATED_MODE_SORTIE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJET_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_OBJET_PATIENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ACCOUCHEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ACCOUCHEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATE_OPERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OPERATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_REVEIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REVEIL = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_POIDS_BEBE = 1D;
    private static final Double UPDATED_POIDS_BEBE = 2D;

    private static final Double DEFAULT_TAILLE_BEBE = 1D;
    private static final Double UPDATED_TAILLE_BEBE = 2D;

    private static final Double DEFAULT_POIDS_PLACENTA = 1D;
    private static final Double UPDATED_POIDS_PLACENTA = 2D;

    private static final String DEFAULT_GENRE_BEBE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE_BEBE = "BBBBBBBBBB";

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
    private HospitalisationRepository hospitalisationRepository;

    @Autowired
    private HospitalisationService hospitalisationService;

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

    private MockMvc restHospitalisationMockMvc;

    private Hospitalisation hospitalisation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HospitalisationResource hospitalisationResource = new HospitalisationResource(hospitalisationService);
        this.restHospitalisationMockMvc = MockMvcBuilders.standaloneSetup(hospitalisationResource)
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
    public static Hospitalisation createEntity(EntityManager em) {
        Hospitalisation hospitalisation = new Hospitalisation()
            .dateEntre(DEFAULT_DATE_ENTRE)
            .dateSortie(DEFAULT_DATE_SORTIE)
            .observation(DEFAULT_OBSERVATION)
            .modeSortie(DEFAULT_MODE_SORTIE)
            .objetPatient(DEFAULT_OBJET_PATIENT)
            .dateAccouchement(DEFAULT_DATE_ACCOUCHEMENT)
            .dateOperation(DEFAULT_DATE_OPERATION)
            .dateReveil(DEFAULT_DATE_REVEIL)
            .poidsBebe(DEFAULT_POIDS_BEBE)
            .tailleBebe(DEFAULT_TAILLE_BEBE)
            .poidsPlacenta(DEFAULT_POIDS_PLACENTA)
            .genreBebe(DEFAULT_GENRE_BEBE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return hospitalisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospitalisation createUpdatedEntity(EntityManager em) {
        Hospitalisation hospitalisation = new Hospitalisation()
            .dateEntre(UPDATED_DATE_ENTRE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .observation(UPDATED_OBSERVATION)
            .modeSortie(UPDATED_MODE_SORTIE)
            .objetPatient(UPDATED_OBJET_PATIENT)
            .dateAccouchement(UPDATED_DATE_ACCOUCHEMENT)
            .dateOperation(UPDATED_DATE_OPERATION)
            .dateReveil(UPDATED_DATE_REVEIL)
            .poidsBebe(UPDATED_POIDS_BEBE)
            .tailleBebe(UPDATED_TAILLE_BEBE)
            .poidsPlacenta(UPDATED_POIDS_PLACENTA)
            .genreBebe(UPDATED_GENRE_BEBE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return hospitalisation;
    }

    @BeforeEach
    public void initTest() {
        hospitalisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createHospitalisation() throws Exception {
        int databaseSizeBeforeCreate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation
        restHospitalisationMockMvc.perform(post("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isCreated());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeCreate + 1);
        Hospitalisation testHospitalisation = hospitalisationList.get(hospitalisationList.size() - 1);
        assertThat(testHospitalisation.getDateEntre()).isEqualTo(DEFAULT_DATE_ENTRE);
        assertThat(testHospitalisation.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
        assertThat(testHospitalisation.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testHospitalisation.getModeSortie()).isEqualTo(DEFAULT_MODE_SORTIE);
        assertThat(testHospitalisation.getObjetPatient()).isEqualTo(DEFAULT_OBJET_PATIENT);
        assertThat(testHospitalisation.getDateAccouchement()).isEqualTo(DEFAULT_DATE_ACCOUCHEMENT);
        assertThat(testHospitalisation.getDateOperation()).isEqualTo(DEFAULT_DATE_OPERATION);
        assertThat(testHospitalisation.getDateReveil()).isEqualTo(DEFAULT_DATE_REVEIL);
        assertThat(testHospitalisation.getPoidsBebe()).isEqualTo(DEFAULT_POIDS_BEBE);
        assertThat(testHospitalisation.getTailleBebe()).isEqualTo(DEFAULT_TAILLE_BEBE);
        assertThat(testHospitalisation.getPoidsPlacenta()).isEqualTo(DEFAULT_POIDS_PLACENTA);
        assertThat(testHospitalisation.getGenreBebe()).isEqualTo(DEFAULT_GENRE_BEBE);
        assertThat(testHospitalisation.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testHospitalisation.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testHospitalisation.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testHospitalisation.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testHospitalisation.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createHospitalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation with an existing ID
        hospitalisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalisationMockMvc.perform(post("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHospitalisations() throws Exception {
        // Initialize the database
        hospitalisationRepository.saveAndFlush(hospitalisation);

        // Get all the hospitalisationList
        restHospitalisationMockMvc.perform(get("/api/hospitalisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEntre").value(hasItem(DEFAULT_DATE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].dateSortie").value(hasItem(DEFAULT_DATE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].modeSortie").value(hasItem(DEFAULT_MODE_SORTIE)))
            .andExpect(jsonPath("$.[*].objetPatient").value(hasItem(DEFAULT_OBJET_PATIENT)))
            .andExpect(jsonPath("$.[*].dateAccouchement").value(hasItem(DEFAULT_DATE_ACCOUCHEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateOperation").value(hasItem(DEFAULT_DATE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].dateReveil").value(hasItem(DEFAULT_DATE_REVEIL.toString())))
            .andExpect(jsonPath("$.[*].poidsBebe").value(hasItem(DEFAULT_POIDS_BEBE.doubleValue())))
            .andExpect(jsonPath("$.[*].tailleBebe").value(hasItem(DEFAULT_TAILLE_BEBE.doubleValue())))
            .andExpect(jsonPath("$.[*].poidsPlacenta").value(hasItem(DEFAULT_POIDS_PLACENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].genreBebe").value(hasItem(DEFAULT_GENRE_BEBE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationRepository.saveAndFlush(hospitalisation);

        // Get the hospitalisation
        restHospitalisationMockMvc.perform(get("/api/hospitalisations/{id}", hospitalisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospitalisation.getId().intValue()))
            .andExpect(jsonPath("$.dateEntre").value(DEFAULT_DATE_ENTRE.toString()))
            .andExpect(jsonPath("$.dateSortie").value(DEFAULT_DATE_SORTIE.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.modeSortie").value(DEFAULT_MODE_SORTIE))
            .andExpect(jsonPath("$.objetPatient").value(DEFAULT_OBJET_PATIENT))
            .andExpect(jsonPath("$.dateAccouchement").value(DEFAULT_DATE_ACCOUCHEMENT.toString()))
            .andExpect(jsonPath("$.dateOperation").value(DEFAULT_DATE_OPERATION.toString()))
            .andExpect(jsonPath("$.dateReveil").value(DEFAULT_DATE_REVEIL.toString()))
            .andExpect(jsonPath("$.poidsBebe").value(DEFAULT_POIDS_BEBE.doubleValue()))
            .andExpect(jsonPath("$.tailleBebe").value(DEFAULT_TAILLE_BEBE.doubleValue()))
            .andExpect(jsonPath("$.poidsPlacenta").value(DEFAULT_POIDS_PLACENTA.doubleValue()))
            .andExpect(jsonPath("$.genreBebe").value(DEFAULT_GENRE_BEBE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHospitalisation() throws Exception {
        // Get the hospitalisation
        restHospitalisationMockMvc.perform(get("/api/hospitalisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationService.save(hospitalisation);

        int databaseSizeBeforeUpdate = hospitalisationRepository.findAll().size();

        // Update the hospitalisation
        Hospitalisation updatedHospitalisation = hospitalisationRepository.findById(hospitalisation.getId()).get();
        // Disconnect from session so that the updates on updatedHospitalisation are not directly saved in db
        em.detach(updatedHospitalisation);
        updatedHospitalisation
            .dateEntre(UPDATED_DATE_ENTRE)
            .dateSortie(UPDATED_DATE_SORTIE)
            .observation(UPDATED_OBSERVATION)
            .modeSortie(UPDATED_MODE_SORTIE)
            .objetPatient(UPDATED_OBJET_PATIENT)
            .dateAccouchement(UPDATED_DATE_ACCOUCHEMENT)
            .dateOperation(UPDATED_DATE_OPERATION)
            .dateReveil(UPDATED_DATE_REVEIL)
            .poidsBebe(UPDATED_POIDS_BEBE)
            .tailleBebe(UPDATED_TAILLE_BEBE)
            .poidsPlacenta(UPDATED_POIDS_PLACENTA)
            .genreBebe(UPDATED_GENRE_BEBE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restHospitalisationMockMvc.perform(put("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHospitalisation)))
            .andExpect(status().isOk());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeUpdate);
        Hospitalisation testHospitalisation = hospitalisationList.get(hospitalisationList.size() - 1);
        assertThat(testHospitalisation.getDateEntre()).isEqualTo(UPDATED_DATE_ENTRE);
        assertThat(testHospitalisation.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
        assertThat(testHospitalisation.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testHospitalisation.getModeSortie()).isEqualTo(UPDATED_MODE_SORTIE);
        assertThat(testHospitalisation.getObjetPatient()).isEqualTo(UPDATED_OBJET_PATIENT);
        assertThat(testHospitalisation.getDateAccouchement()).isEqualTo(UPDATED_DATE_ACCOUCHEMENT);
        assertThat(testHospitalisation.getDateOperation()).isEqualTo(UPDATED_DATE_OPERATION);
        assertThat(testHospitalisation.getDateReveil()).isEqualTo(UPDATED_DATE_REVEIL);
        assertThat(testHospitalisation.getPoidsBebe()).isEqualTo(UPDATED_POIDS_BEBE);
        assertThat(testHospitalisation.getTailleBebe()).isEqualTo(UPDATED_TAILLE_BEBE);
        assertThat(testHospitalisation.getPoidsPlacenta()).isEqualTo(UPDATED_POIDS_PLACENTA);
        assertThat(testHospitalisation.getGenreBebe()).isEqualTo(UPDATED_GENRE_BEBE);
        assertThat(testHospitalisation.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testHospitalisation.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testHospitalisation.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testHospitalisation.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testHospitalisation.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingHospitalisation() throws Exception {
        int databaseSizeBeforeUpdate = hospitalisationRepository.findAll().size();

        // Create the Hospitalisation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalisationMockMvc.perform(put("/api/hospitalisations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hospitalisation)))
            .andExpect(status().isBadRequest());

        // Validate the Hospitalisation in the database
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHospitalisation() throws Exception {
        // Initialize the database
        hospitalisationService.save(hospitalisation);

        int databaseSizeBeforeDelete = hospitalisationRepository.findAll().size();

        // Delete the hospitalisation
        restHospitalisationMockMvc.perform(delete("/api/hospitalisations/{id}", hospitalisation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hospitalisation> hospitalisationList = hospitalisationRepository.findAll();
        assertThat(hospitalisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
