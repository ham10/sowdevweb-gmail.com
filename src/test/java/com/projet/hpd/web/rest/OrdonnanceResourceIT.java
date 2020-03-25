package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Ordonnance;
import com.projet.hpd.repository.OrdonnanceRepository;
import com.projet.hpd.service.OrdonnanceService;
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
 * Integration tests for the {@link OrdonnanceResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class OrdonnanceResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRIPTION = "BBBBBBBBBB";

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
    private OrdonnanceRepository ordonnanceRepository;

    @Autowired
    private OrdonnanceService ordonnanceService;

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

    private MockMvc restOrdonnanceMockMvc;

    private Ordonnance ordonnance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdonnanceResource ordonnanceResource = new OrdonnanceResource(ordonnanceService);
        this.restOrdonnanceMockMvc = MockMvcBuilders.standaloneSetup(ordonnanceResource)
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
    public static Ordonnance createEntity(EntityManager em) {
        Ordonnance ordonnance = new Ordonnance()
            .numero(DEFAULT_NUMERO)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .prescription(DEFAULT_PRESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ordonnance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordonnance createUpdatedEntity(EntityManager em) {
        Ordonnance ordonnance = new Ordonnance()
            .numero(UPDATED_NUMERO)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .prescription(UPDATED_PRESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ordonnance;
    }

    @BeforeEach
    public void initTest() {
        ordonnance = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdonnance() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isCreated());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate + 1);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testOrdonnance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOrdonnance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrdonnance.getPrescription()).isEqualTo(DEFAULT_PRESCRIPTION);
        assertThat(testOrdonnance.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testOrdonnance.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testOrdonnance.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testOrdonnance.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testOrdonnance.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createOrdonnanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance with an existing ID
        ordonnance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrdonnances() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get all the ordonnanceList
        restOrdonnanceMockMvc.perform(get("/api/ordonnances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordonnance.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prescription").value(hasItem(DEFAULT_PRESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", ordonnance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordonnance.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.prescription").value(DEFAULT_PRESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdonnance() throws Exception {
        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceService.save(ordonnance);

        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // Update the ordonnance
        Ordonnance updatedOrdonnance = ordonnanceRepository.findById(ordonnance.getId()).get();
        // Disconnect from session so that the updates on updatedOrdonnance are not directly saved in db
        em.detach(updatedOrdonnance);
        updatedOrdonnance
            .numero(UPDATED_NUMERO)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .prescription(UPDATED_PRESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdonnance)))
            .andExpect(status().isOk());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testOrdonnance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOrdonnance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrdonnance.getPrescription()).isEqualTo(UPDATED_PRESCRIPTION);
        assertThat(testOrdonnance.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testOrdonnance.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testOrdonnance.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testOrdonnance.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testOrdonnance.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdonnance() throws Exception {
        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordonnance)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceService.save(ordonnance);

        int databaseSizeBeforeDelete = ordonnanceRepository.findAll().size();

        // Delete the ordonnance
        restOrdonnanceMockMvc.perform(delete("/api/ordonnances/{id}", ordonnance.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
