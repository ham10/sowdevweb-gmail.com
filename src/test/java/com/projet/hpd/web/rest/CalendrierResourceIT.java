package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Calendrier;
import com.projet.hpd.repository.CalendrierRepository;
import com.projet.hpd.service.CalendrierService;
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
 * Integration tests for the {@link CalendrierResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CalendrierResourceIT {

    private static final String DEFAULT_LIBELLE_CALENDRIER = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CALENDRIER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_HEURE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEURE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HEURE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEURE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_USER_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_USER_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private CalendrierRepository calendrierRepository;

    @Autowired
    private CalendrierService calendrierService;

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

    private MockMvc restCalendrierMockMvc;

    private Calendrier calendrier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendrierResource calendrierResource = new CalendrierResource(calendrierService);
        this.restCalendrierMockMvc = MockMvcBuilders.standaloneSetup(calendrierResource)
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
    public static Calendrier createEntity(EntityManager em) {
        Calendrier calendrier = new Calendrier()
            .libelleCalendrier(DEFAULT_LIBELLE_CALENDRIER)
            .heureDebut(DEFAULT_HEURE_DEBUT)
            .heureFin(DEFAULT_HEURE_FIN)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return calendrier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendrier createUpdatedEntity(EntityManager em) {
        Calendrier calendrier = new Calendrier()
            .libelleCalendrier(UPDATED_LIBELLE_CALENDRIER)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return calendrier;
    }

    @BeforeEach
    public void initTest() {
        calendrier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendrier() throws Exception {
        int databaseSizeBeforeCreate = calendrierRepository.findAll().size();

        // Create the Calendrier
        restCalendrierMockMvc.perform(post("/api/calendriers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendrier)))
            .andExpect(status().isCreated());

        // Validate the Calendrier in the database
        List<Calendrier> calendrierList = calendrierRepository.findAll();
        assertThat(calendrierList).hasSize(databaseSizeBeforeCreate + 1);
        Calendrier testCalendrier = calendrierList.get(calendrierList.size() - 1);
        assertThat(testCalendrier.getLibelleCalendrier()).isEqualTo(DEFAULT_LIBELLE_CALENDRIER);
        assertThat(testCalendrier.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testCalendrier.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
        assertThat(testCalendrier.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCalendrier.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCalendrier.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCalendrier.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCalendrier.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCalendrier.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCalendrier.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCalendrier.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCalendrierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendrierRepository.findAll().size();

        // Create the Calendrier with an existing ID
        calendrier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendrierMockMvc.perform(post("/api/calendriers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendrier)))
            .andExpect(status().isBadRequest());

        // Validate the Calendrier in the database
        List<Calendrier> calendrierList = calendrierRepository.findAll();
        assertThat(calendrierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalendriers() throws Exception {
        // Initialize the database
        calendrierRepository.saveAndFlush(calendrier);

        // Get all the calendrierList
        restCalendrierMockMvc.perform(get("/api/calendriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCalendrier").value(hasItem(DEFAULT_LIBELLE_CALENDRIER)))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.toString())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCalendrier() throws Exception {
        // Initialize the database
        calendrierRepository.saveAndFlush(calendrier);

        // Get the calendrier
        restCalendrierMockMvc.perform(get("/api/calendriers/{id}", calendrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendrier.getId().intValue()))
            .andExpect(jsonPath("$.libelleCalendrier").value(DEFAULT_LIBELLE_CALENDRIER))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT.toString()))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.toString()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCalendrier() throws Exception {
        // Get the calendrier
        restCalendrierMockMvc.perform(get("/api/calendriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendrier() throws Exception {
        // Initialize the database
        calendrierService.save(calendrier);

        int databaseSizeBeforeUpdate = calendrierRepository.findAll().size();

        // Update the calendrier
        Calendrier updatedCalendrier = calendrierRepository.findById(calendrier.getId()).get();
        // Disconnect from session so that the updates on updatedCalendrier are not directly saved in db
        em.detach(updatedCalendrier);
        updatedCalendrier
            .libelleCalendrier(UPDATED_LIBELLE_CALENDRIER)
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCalendrierMockMvc.perform(put("/api/calendriers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendrier)))
            .andExpect(status().isOk());

        // Validate the Calendrier in the database
        List<Calendrier> calendrierList = calendrierRepository.findAll();
        assertThat(calendrierList).hasSize(databaseSizeBeforeUpdate);
        Calendrier testCalendrier = calendrierList.get(calendrierList.size() - 1);
        assertThat(testCalendrier.getLibelleCalendrier()).isEqualTo(UPDATED_LIBELLE_CALENDRIER);
        assertThat(testCalendrier.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testCalendrier.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testCalendrier.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCalendrier.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCalendrier.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCalendrier.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCalendrier.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCalendrier.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCalendrier.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCalendrier.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendrier() throws Exception {
        int databaseSizeBeforeUpdate = calendrierRepository.findAll().size();

        // Create the Calendrier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendrierMockMvc.perform(put("/api/calendriers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendrier)))
            .andExpect(status().isBadRequest());

        // Validate the Calendrier in the database
        List<Calendrier> calendrierList = calendrierRepository.findAll();
        assertThat(calendrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendrier() throws Exception {
        // Initialize the database
        calendrierService.save(calendrier);

        int databaseSizeBeforeDelete = calendrierRepository.findAll().size();

        // Delete the calendrier
        restCalendrierMockMvc.perform(delete("/api/calendriers/{id}", calendrier.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendrier> calendrierList = calendrierRepository.findAll();
        assertThat(calendrierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
