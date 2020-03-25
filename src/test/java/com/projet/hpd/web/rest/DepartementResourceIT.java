package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Departement;
import com.projet.hpd.repository.DepartementRepository;
import com.projet.hpd.service.DepartementService;
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
 * Integration tests for the {@link DepartementResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DepartementResourceIT {

    private static final String DEFAULT_CODE_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_DEPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DEPARTEMENT = "BBBBBBBBBB";

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
    private DepartementRepository departementRepository;

    @Autowired
    private DepartementService departementService;

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

    private MockMvc restDepartementMockMvc;

    private Departement departement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartementResource departementResource = new DepartementResource(departementService);
        this.restDepartementMockMvc = MockMvcBuilders.standaloneSetup(departementResource)
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
    public static Departement createEntity(EntityManager em) {
        Departement departement = new Departement()
            .codeDepartement(DEFAULT_CODE_DEPARTEMENT)
            .libelleDepartement(DEFAULT_LIBELLE_DEPARTEMENT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return departement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departement createUpdatedEntity(EntityManager em) {
        Departement departement = new Departement()
            .codeDepartement(UPDATED_CODE_DEPARTEMENT)
            .libelleDepartement(UPDATED_LIBELLE_DEPARTEMENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return departement;
    }

    @BeforeEach
    public void initTest() {
        departement = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartement() throws Exception {
        int databaseSizeBeforeCreate = departementRepository.findAll().size();

        // Create the Departement
        restDepartementMockMvc.perform(post("/api/departements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departement)))
            .andExpect(status().isCreated());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeCreate + 1);
        Departement testDepartement = departementList.get(departementList.size() - 1);
        assertThat(testDepartement.getCodeDepartement()).isEqualTo(DEFAULT_CODE_DEPARTEMENT);
        assertThat(testDepartement.getLibelleDepartement()).isEqualTo(DEFAULT_LIBELLE_DEPARTEMENT);
        assertThat(testDepartement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDepartement.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testDepartement.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testDepartement.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testDepartement.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createDepartementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departementRepository.findAll().size();

        // Create the Departement with an existing ID
        departement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartementMockMvc.perform(post("/api/departements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departement)))
            .andExpect(status().isBadRequest());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepartements() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList
        restDepartementMockMvc.perform(get("/api/departements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departement.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeDepartement").value(hasItem(DEFAULT_CODE_DEPARTEMENT)))
            .andExpect(jsonPath("$.[*].libelleDepartement").value(hasItem(DEFAULT_LIBELLE_DEPARTEMENT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getDepartement() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get the departement
        restDepartementMockMvc.perform(get("/api/departements/{id}", departement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departement.getId().intValue()))
            .andExpect(jsonPath("$.codeDepartement").value(DEFAULT_CODE_DEPARTEMENT))
            .andExpect(jsonPath("$.libelleDepartement").value(DEFAULT_LIBELLE_DEPARTEMENT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartement() throws Exception {
        // Get the departement
        restDepartementMockMvc.perform(get("/api/departements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartement() throws Exception {
        // Initialize the database
        departementService.save(departement);

        int databaseSizeBeforeUpdate = departementRepository.findAll().size();

        // Update the departement
        Departement updatedDepartement = departementRepository.findById(departement.getId()).get();
        // Disconnect from session so that the updates on updatedDepartement are not directly saved in db
        em.detach(updatedDepartement);
        updatedDepartement
            .codeDepartement(UPDATED_CODE_DEPARTEMENT)
            .libelleDepartement(UPDATED_LIBELLE_DEPARTEMENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restDepartementMockMvc.perform(put("/api/departements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartement)))
            .andExpect(status().isOk());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeUpdate);
        Departement testDepartement = departementList.get(departementList.size() - 1);
        assertThat(testDepartement.getCodeDepartement()).isEqualTo(UPDATED_CODE_DEPARTEMENT);
        assertThat(testDepartement.getLibelleDepartement()).isEqualTo(UPDATED_LIBELLE_DEPARTEMENT);
        assertThat(testDepartement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDepartement.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testDepartement.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testDepartement.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testDepartement.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartement() throws Exception {
        int databaseSizeBeforeUpdate = departementRepository.findAll().size();

        // Create the Departement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartementMockMvc.perform(put("/api/departements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departement)))
            .andExpect(status().isBadRequest());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartement() throws Exception {
        // Initialize the database
        departementService.save(departement);

        int databaseSizeBeforeDelete = departementRepository.findAll().size();

        // Delete the departement
        restDepartementMockMvc.perform(delete("/api/departements/{id}", departement.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
