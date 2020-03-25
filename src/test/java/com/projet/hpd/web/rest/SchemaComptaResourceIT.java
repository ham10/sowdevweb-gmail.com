package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.SchemaCompta;
import com.projet.hpd.repository.SchemaComptaRepository;
import com.projet.hpd.service.SchemaComptaService;
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
 * Integration tests for the {@link SchemaComptaResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class SchemaComptaResourceIT {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_SENS = "AAAAAAAAAA";
    private static final String UPDATED_SENS = "BBBBBBBBBB";

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
    private SchemaComptaRepository schemaComptaRepository;

    @Autowired
    private SchemaComptaService schemaComptaService;

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

    private MockMvc restSchemaComptaMockMvc;

    private SchemaCompta schemaCompta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchemaComptaResource schemaComptaResource = new SchemaComptaResource(schemaComptaService);
        this.restSchemaComptaMockMvc = MockMvcBuilders.standaloneSetup(schemaComptaResource)
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
    public static SchemaCompta createEntity(EntityManager em) {
        SchemaCompta schemaCompta = new SchemaCompta()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .sens(DEFAULT_SENS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return schemaCompta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaCompta createUpdatedEntity(EntityManager em) {
        SchemaCompta schemaCompta = new SchemaCompta()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .sens(UPDATED_SENS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return schemaCompta;
    }

    @BeforeEach
    public void initTest() {
        schemaCompta = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaCompta() throws Exception {
        int databaseSizeBeforeCreate = schemaComptaRepository.findAll().size();

        // Create the SchemaCompta
        restSchemaComptaMockMvc.perform(post("/api/schema-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaCompta)))
            .andExpect(status().isCreated());

        // Validate the SchemaCompta in the database
        List<SchemaCompta> schemaComptaList = schemaComptaRepository.findAll();
        assertThat(schemaComptaList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaCompta testSchemaCompta = schemaComptaList.get(schemaComptaList.size() - 1);
        assertThat(testSchemaCompta.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSchemaCompta.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSchemaCompta.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testSchemaCompta.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testSchemaCompta.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testSchemaCompta.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testSchemaCompta.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testSchemaCompta.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testSchemaCompta.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createSchemaComptaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaComptaRepository.findAll().size();

        // Create the SchemaCompta with an existing ID
        schemaCompta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaComptaMockMvc.perform(post("/api/schema-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaCompta)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaCompta in the database
        List<SchemaCompta> schemaComptaList = schemaComptaRepository.findAll();
        assertThat(schemaComptaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSchemaComptas() throws Exception {
        // Initialize the database
        schemaComptaRepository.saveAndFlush(schemaCompta);

        // Get all the schemaComptaList
        restSchemaComptaMockMvc.perform(get("/api/schema-comptas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaCompta.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getSchemaCompta() throws Exception {
        // Initialize the database
        schemaComptaRepository.saveAndFlush(schemaCompta);

        // Get the schemaCompta
        restSchemaComptaMockMvc.perform(get("/api/schema-comptas/{id}", schemaCompta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schemaCompta.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.sens").value(DEFAULT_SENS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSchemaCompta() throws Exception {
        // Get the schemaCompta
        restSchemaComptaMockMvc.perform(get("/api/schema-comptas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaCompta() throws Exception {
        // Initialize the database
        schemaComptaService.save(schemaCompta);

        int databaseSizeBeforeUpdate = schemaComptaRepository.findAll().size();

        // Update the schemaCompta
        SchemaCompta updatedSchemaCompta = schemaComptaRepository.findById(schemaCompta.getId()).get();
        // Disconnect from session so that the updates on updatedSchemaCompta are not directly saved in db
        em.detach(updatedSchemaCompta);
        updatedSchemaCompta
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .sens(UPDATED_SENS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restSchemaComptaMockMvc.perform(put("/api/schema-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchemaCompta)))
            .andExpect(status().isOk());

        // Validate the SchemaCompta in the database
        List<SchemaCompta> schemaComptaList = schemaComptaRepository.findAll();
        assertThat(schemaComptaList).hasSize(databaseSizeBeforeUpdate);
        SchemaCompta testSchemaCompta = schemaComptaList.get(schemaComptaList.size() - 1);
        assertThat(testSchemaCompta.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSchemaCompta.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSchemaCompta.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testSchemaCompta.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testSchemaCompta.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testSchemaCompta.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testSchemaCompta.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testSchemaCompta.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testSchemaCompta.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaCompta() throws Exception {
        int databaseSizeBeforeUpdate = schemaComptaRepository.findAll().size();

        // Create the SchemaCompta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemaComptaMockMvc.perform(put("/api/schema-comptas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemaCompta)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaCompta in the database
        List<SchemaCompta> schemaComptaList = schemaComptaRepository.findAll();
        assertThat(schemaComptaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchemaCompta() throws Exception {
        // Initialize the database
        schemaComptaService.save(schemaCompta);

        int databaseSizeBeforeDelete = schemaComptaRepository.findAll().size();

        // Delete the schemaCompta
        restSchemaComptaMockMvc.perform(delete("/api/schema-comptas/{id}", schemaCompta.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchemaCompta> schemaComptaList = schemaComptaRepository.findAll();
        assertThat(schemaComptaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
