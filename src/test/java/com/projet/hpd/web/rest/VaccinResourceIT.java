package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Vaccin;
import com.projet.hpd.repository.VaccinRepository;
import com.projet.hpd.service.VaccinService;
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
 * Integration tests for the {@link VaccinResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class VaccinResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RENOUV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RENOUV = LocalDate.now(ZoneId.systemDefault());

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
    private VaccinRepository vaccinRepository;

    @Autowired
    private VaccinService vaccinService;

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

    private MockMvc restVaccinMockMvc;

    private Vaccin vaccin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VaccinResource vaccinResource = new VaccinResource(vaccinService);
        this.restVaccinMockMvc = MockMvcBuilders.standaloneSetup(vaccinResource)
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
    public static Vaccin createEntity(EntityManager em) {
        Vaccin vaccin = new Vaccin()
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .dateRenouv(DEFAULT_DATE_RENOUV)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return vaccin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vaccin createUpdatedEntity(EntityManager em) {
        Vaccin vaccin = new Vaccin()
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateRenouv(UPDATED_DATE_RENOUV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return vaccin;
    }

    @BeforeEach
    public void initTest() {
        vaccin = createEntity(em);
    }

    @Test
    @Transactional
    public void createVaccin() throws Exception {
        int databaseSizeBeforeCreate = vaccinRepository.findAll().size();

        // Create the Vaccin
        restVaccinMockMvc.perform(post("/api/vaccins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccin)))
            .andExpect(status().isCreated());

        // Validate the Vaccin in the database
        List<Vaccin> vaccinList = vaccinRepository.findAll();
        assertThat(vaccinList).hasSize(databaseSizeBeforeCreate + 1);
        Vaccin testVaccin = vaccinList.get(vaccinList.size() - 1);
        assertThat(testVaccin.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVaccin.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVaccin.getDateRenouv()).isEqualTo(DEFAULT_DATE_RENOUV);
        assertThat(testVaccin.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testVaccin.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testVaccin.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testVaccin.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testVaccin.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createVaccinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vaccinRepository.findAll().size();

        // Create the Vaccin with an existing ID
        vaccin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVaccinMockMvc.perform(post("/api/vaccins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccin)))
            .andExpect(status().isBadRequest());

        // Validate the Vaccin in the database
        List<Vaccin> vaccinList = vaccinRepository.findAll();
        assertThat(vaccinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVaccins() throws Exception {
        // Initialize the database
        vaccinRepository.saveAndFlush(vaccin);

        // Get all the vaccinList
        restVaccinMockMvc.perform(get("/api/vaccins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vaccin.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateRenouv").value(hasItem(DEFAULT_DATE_RENOUV.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getVaccin() throws Exception {
        // Initialize the database
        vaccinRepository.saveAndFlush(vaccin);

        // Get the vaccin
        restVaccinMockMvc.perform(get("/api/vaccins/{id}", vaccin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vaccin.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dateRenouv").value(DEFAULT_DATE_RENOUV.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVaccin() throws Exception {
        // Get the vaccin
        restVaccinMockMvc.perform(get("/api/vaccins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVaccin() throws Exception {
        // Initialize the database
        vaccinService.save(vaccin);

        int databaseSizeBeforeUpdate = vaccinRepository.findAll().size();

        // Update the vaccin
        Vaccin updatedVaccin = vaccinRepository.findById(vaccin.getId()).get();
        // Disconnect from session so that the updates on updatedVaccin are not directly saved in db
        em.detach(updatedVaccin);
        updatedVaccin
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateRenouv(UPDATED_DATE_RENOUV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restVaccinMockMvc.perform(put("/api/vaccins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVaccin)))
            .andExpect(status().isOk());

        // Validate the Vaccin in the database
        List<Vaccin> vaccinList = vaccinRepository.findAll();
        assertThat(vaccinList).hasSize(databaseSizeBeforeUpdate);
        Vaccin testVaccin = vaccinList.get(vaccinList.size() - 1);
        assertThat(testVaccin.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVaccin.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVaccin.getDateRenouv()).isEqualTo(UPDATED_DATE_RENOUV);
        assertThat(testVaccin.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testVaccin.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testVaccin.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testVaccin.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testVaccin.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingVaccin() throws Exception {
        int databaseSizeBeforeUpdate = vaccinRepository.findAll().size();

        // Create the Vaccin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVaccinMockMvc.perform(put("/api/vaccins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vaccin)))
            .andExpect(status().isBadRequest());

        // Validate the Vaccin in the database
        List<Vaccin> vaccinList = vaccinRepository.findAll();
        assertThat(vaccinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVaccin() throws Exception {
        // Initialize the database
        vaccinService.save(vaccin);

        int databaseSizeBeforeDelete = vaccinRepository.findAll().size();

        // Delete the vaccin
        restVaccinMockMvc.perform(delete("/api/vaccins/{id}", vaccin.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vaccin> vaccinList = vaccinRepository.findAll();
        assertThat(vaccinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
