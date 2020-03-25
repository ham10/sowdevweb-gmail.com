package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePatient;
import com.projet.hpd.repository.TypePatientRepository;
import com.projet.hpd.service.TypePatientService;
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
 * Integration tests for the {@link TypePatientResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePatientResourceIT {

    private static final String DEFAULT_CODE_TYPE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PATIENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PATIENT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PATIENT = "BBBBBBBBBB";

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
    private TypePatientRepository typePatientRepository;

    @Autowired
    private TypePatientService typePatientService;

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

    private MockMvc restTypePatientMockMvc;

    private TypePatient typePatient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePatientResource typePatientResource = new TypePatientResource(typePatientService);
        this.restTypePatientMockMvc = MockMvcBuilders.standaloneSetup(typePatientResource)
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
    public static TypePatient createEntity(EntityManager em) {
        TypePatient typePatient = new TypePatient()
            .codeTypePatient(DEFAULT_CODE_TYPE_PATIENT)
            .libelleTypePatient(DEFAULT_LIBELLE_TYPE_PATIENT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePatient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePatient createUpdatedEntity(EntityManager em) {
        TypePatient typePatient = new TypePatient()
            .codeTypePatient(UPDATED_CODE_TYPE_PATIENT)
            .libelleTypePatient(UPDATED_LIBELLE_TYPE_PATIENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePatient;
    }

    @BeforeEach
    public void initTest() {
        typePatient = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePatient() throws Exception {
        int databaseSizeBeforeCreate = typePatientRepository.findAll().size();

        // Create the TypePatient
        restTypePatientMockMvc.perform(post("/api/type-patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePatient)))
            .andExpect(status().isCreated());

        // Validate the TypePatient in the database
        List<TypePatient> typePatientList = typePatientRepository.findAll();
        assertThat(typePatientList).hasSize(databaseSizeBeforeCreate + 1);
        TypePatient testTypePatient = typePatientList.get(typePatientList.size() - 1);
        assertThat(testTypePatient.getCodeTypePatient()).isEqualTo(DEFAULT_CODE_TYPE_PATIENT);
        assertThat(testTypePatient.getLibelleTypePatient()).isEqualTo(DEFAULT_LIBELLE_TYPE_PATIENT);
        assertThat(testTypePatient.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePatient.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePatient.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePatient.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePatient.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePatientRepository.findAll().size();

        // Create the TypePatient with an existing ID
        typePatient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePatientMockMvc.perform(post("/api/type-patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePatient)))
            .andExpect(status().isBadRequest());

        // Validate the TypePatient in the database
        List<TypePatient> typePatientList = typePatientRepository.findAll();
        assertThat(typePatientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePatients() throws Exception {
        // Initialize the database
        typePatientRepository.saveAndFlush(typePatient);

        // Get all the typePatientList
        restTypePatientMockMvc.perform(get("/api/type-patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePatient").value(hasItem(DEFAULT_CODE_TYPE_PATIENT)))
            .andExpect(jsonPath("$.[*].libelleTypePatient").value(hasItem(DEFAULT_LIBELLE_TYPE_PATIENT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePatient() throws Exception {
        // Initialize the database
        typePatientRepository.saveAndFlush(typePatient);

        // Get the typePatient
        restTypePatientMockMvc.perform(get("/api/type-patients/{id}", typePatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePatient.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePatient").value(DEFAULT_CODE_TYPE_PATIENT))
            .andExpect(jsonPath("$.libelleTypePatient").value(DEFAULT_LIBELLE_TYPE_PATIENT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePatient() throws Exception {
        // Get the typePatient
        restTypePatientMockMvc.perform(get("/api/type-patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePatient() throws Exception {
        // Initialize the database
        typePatientService.save(typePatient);

        int databaseSizeBeforeUpdate = typePatientRepository.findAll().size();

        // Update the typePatient
        TypePatient updatedTypePatient = typePatientRepository.findById(typePatient.getId()).get();
        // Disconnect from session so that the updates on updatedTypePatient are not directly saved in db
        em.detach(updatedTypePatient);
        updatedTypePatient
            .codeTypePatient(UPDATED_CODE_TYPE_PATIENT)
            .libelleTypePatient(UPDATED_LIBELLE_TYPE_PATIENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePatientMockMvc.perform(put("/api/type-patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePatient)))
            .andExpect(status().isOk());

        // Validate the TypePatient in the database
        List<TypePatient> typePatientList = typePatientRepository.findAll();
        assertThat(typePatientList).hasSize(databaseSizeBeforeUpdate);
        TypePatient testTypePatient = typePatientList.get(typePatientList.size() - 1);
        assertThat(testTypePatient.getCodeTypePatient()).isEqualTo(UPDATED_CODE_TYPE_PATIENT);
        assertThat(testTypePatient.getLibelleTypePatient()).isEqualTo(UPDATED_LIBELLE_TYPE_PATIENT);
        assertThat(testTypePatient.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePatient.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePatient.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePatient.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePatient.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePatient() throws Exception {
        int databaseSizeBeforeUpdate = typePatientRepository.findAll().size();

        // Create the TypePatient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePatientMockMvc.perform(put("/api/type-patients")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePatient)))
            .andExpect(status().isBadRequest());

        // Validate the TypePatient in the database
        List<TypePatient> typePatientList = typePatientRepository.findAll();
        assertThat(typePatientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePatient() throws Exception {
        // Initialize the database
        typePatientService.save(typePatient);

        int databaseSizeBeforeDelete = typePatientRepository.findAll().size();

        // Delete the typePatient
        restTypePatientMockMvc.perform(delete("/api/type-patients/{id}", typePatient.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePatient> typePatientList = typePatientRepository.findAll();
        assertThat(typePatientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
