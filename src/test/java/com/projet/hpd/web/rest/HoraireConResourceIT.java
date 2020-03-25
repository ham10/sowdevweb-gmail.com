package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.HoraireCon;
import com.projet.hpd.repository.HoraireConRepository;
import com.projet.hpd.service.HoraireConService;
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
 * Integration tests for the {@link HoraireConResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class HoraireConResourceIT {

    private static final LocalDate DEFAULT_HEURE_DEBUT_HC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEURE_DEBUT_HC = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HEURE_FIN_HC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEURE_FIN_HC = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT_HC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_HC = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_HC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_HC = LocalDate.now(ZoneId.systemDefault());

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
    private HoraireConRepository horaireConRepository;

    @Autowired
    private HoraireConService horaireConService;

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

    private MockMvc restHoraireConMockMvc;

    private HoraireCon horaireCon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HoraireConResource horaireConResource = new HoraireConResource(horaireConService);
        this.restHoraireConMockMvc = MockMvcBuilders.standaloneSetup(horaireConResource)
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
    public static HoraireCon createEntity(EntityManager em) {
        HoraireCon horaireCon = new HoraireCon()
            .heureDebutHC(DEFAULT_HEURE_DEBUT_HC)
            .heureFinHC(DEFAULT_HEURE_FIN_HC)
            .dateDebutHC(DEFAULT_DATE_DEBUT_HC)
            .dateFinHC(DEFAULT_DATE_FIN_HC)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return horaireCon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoraireCon createUpdatedEntity(EntityManager em) {
        HoraireCon horaireCon = new HoraireCon()
            .heureDebutHC(UPDATED_HEURE_DEBUT_HC)
            .heureFinHC(UPDATED_HEURE_FIN_HC)
            .dateDebutHC(UPDATED_DATE_DEBUT_HC)
            .dateFinHC(UPDATED_DATE_FIN_HC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return horaireCon;
    }

    @BeforeEach
    public void initTest() {
        horaireCon = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoraireCon() throws Exception {
        int databaseSizeBeforeCreate = horaireConRepository.findAll().size();

        // Create the HoraireCon
        restHoraireConMockMvc.perform(post("/api/horaire-cons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireCon)))
            .andExpect(status().isCreated());

        // Validate the HoraireCon in the database
        List<HoraireCon> horaireConList = horaireConRepository.findAll();
        assertThat(horaireConList).hasSize(databaseSizeBeforeCreate + 1);
        HoraireCon testHoraireCon = horaireConList.get(horaireConList.size() - 1);
        assertThat(testHoraireCon.getHeureDebutHC()).isEqualTo(DEFAULT_HEURE_DEBUT_HC);
        assertThat(testHoraireCon.getHeureFinHC()).isEqualTo(DEFAULT_HEURE_FIN_HC);
        assertThat(testHoraireCon.getDateDebutHC()).isEqualTo(DEFAULT_DATE_DEBUT_HC);
        assertThat(testHoraireCon.getDateFinHC()).isEqualTo(DEFAULT_DATE_FIN_HC);
        assertThat(testHoraireCon.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testHoraireCon.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testHoraireCon.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testHoraireCon.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testHoraireCon.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testHoraireCon.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createHoraireConWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horaireConRepository.findAll().size();

        // Create the HoraireCon with an existing ID
        horaireCon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoraireConMockMvc.perform(post("/api/horaire-cons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireCon)))
            .andExpect(status().isBadRequest());

        // Validate the HoraireCon in the database
        List<HoraireCon> horaireConList = horaireConRepository.findAll();
        assertThat(horaireConList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHoraireCons() throws Exception {
        // Initialize the database
        horaireConRepository.saveAndFlush(horaireCon);

        // Get all the horaireConList
        restHoraireConMockMvc.perform(get("/api/horaire-cons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horaireCon.getId().intValue())))
            .andExpect(jsonPath("$.[*].heureDebutHC").value(hasItem(DEFAULT_HEURE_DEBUT_HC.toString())))
            .andExpect(jsonPath("$.[*].heureFinHC").value(hasItem(DEFAULT_HEURE_FIN_HC.toString())))
            .andExpect(jsonPath("$.[*].dateDebutHC").value(hasItem(DEFAULT_DATE_DEBUT_HC.toString())))
            .andExpect(jsonPath("$.[*].dateFinHC").value(hasItem(DEFAULT_DATE_FIN_HC.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getHoraireCon() throws Exception {
        // Initialize the database
        horaireConRepository.saveAndFlush(horaireCon);

        // Get the horaireCon
        restHoraireConMockMvc.perform(get("/api/horaire-cons/{id}", horaireCon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horaireCon.getId().intValue()))
            .andExpect(jsonPath("$.heureDebutHC").value(DEFAULT_HEURE_DEBUT_HC.toString()))
            .andExpect(jsonPath("$.heureFinHC").value(DEFAULT_HEURE_FIN_HC.toString()))
            .andExpect(jsonPath("$.dateDebutHC").value(DEFAULT_DATE_DEBUT_HC.toString()))
            .andExpect(jsonPath("$.dateFinHC").value(DEFAULT_DATE_FIN_HC.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHoraireCon() throws Exception {
        // Get the horaireCon
        restHoraireConMockMvc.perform(get("/api/horaire-cons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoraireCon() throws Exception {
        // Initialize the database
        horaireConService.save(horaireCon);

        int databaseSizeBeforeUpdate = horaireConRepository.findAll().size();

        // Update the horaireCon
        HoraireCon updatedHoraireCon = horaireConRepository.findById(horaireCon.getId()).get();
        // Disconnect from session so that the updates on updatedHoraireCon are not directly saved in db
        em.detach(updatedHoraireCon);
        updatedHoraireCon
            .heureDebutHC(UPDATED_HEURE_DEBUT_HC)
            .heureFinHC(UPDATED_HEURE_FIN_HC)
            .dateDebutHC(UPDATED_DATE_DEBUT_HC)
            .dateFinHC(UPDATED_DATE_FIN_HC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restHoraireConMockMvc.perform(put("/api/horaire-cons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHoraireCon)))
            .andExpect(status().isOk());

        // Validate the HoraireCon in the database
        List<HoraireCon> horaireConList = horaireConRepository.findAll();
        assertThat(horaireConList).hasSize(databaseSizeBeforeUpdate);
        HoraireCon testHoraireCon = horaireConList.get(horaireConList.size() - 1);
        assertThat(testHoraireCon.getHeureDebutHC()).isEqualTo(UPDATED_HEURE_DEBUT_HC);
        assertThat(testHoraireCon.getHeureFinHC()).isEqualTo(UPDATED_HEURE_FIN_HC);
        assertThat(testHoraireCon.getDateDebutHC()).isEqualTo(UPDATED_DATE_DEBUT_HC);
        assertThat(testHoraireCon.getDateFinHC()).isEqualTo(UPDATED_DATE_FIN_HC);
        assertThat(testHoraireCon.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testHoraireCon.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testHoraireCon.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testHoraireCon.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testHoraireCon.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testHoraireCon.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingHoraireCon() throws Exception {
        int databaseSizeBeforeUpdate = horaireConRepository.findAll().size();

        // Create the HoraireCon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoraireConMockMvc.perform(put("/api/horaire-cons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaireCon)))
            .andExpect(status().isBadRequest());

        // Validate the HoraireCon in the database
        List<HoraireCon> horaireConList = horaireConRepository.findAll();
        assertThat(horaireConList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoraireCon() throws Exception {
        // Initialize the database
        horaireConService.save(horaireCon);

        int databaseSizeBeforeDelete = horaireConRepository.findAll().size();

        // Delete the horaireCon
        restHoraireConMockMvc.perform(delete("/api/horaire-cons/{id}", horaireCon.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoraireCon> horaireConList = horaireConRepository.findAll();
        assertThat(horaireConList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
