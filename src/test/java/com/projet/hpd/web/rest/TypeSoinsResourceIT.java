package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeSoins;
import com.projet.hpd.repository.TypeSoinsRepository;
import com.projet.hpd.service.TypeSoinsService;
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
 * Integration tests for the {@link TypeSoinsResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeSoinsResourceIT {

    private static final String DEFAULT_CODE_TYPE_SOINS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_SOINS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_SOINS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_SOINS = "BBBBBBBBBB";

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
    private TypeSoinsRepository typeSoinsRepository;

    @Autowired
    private TypeSoinsService typeSoinsService;

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

    private MockMvc restTypeSoinsMockMvc;

    private TypeSoins typeSoins;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeSoinsResource typeSoinsResource = new TypeSoinsResource(typeSoinsService);
        this.restTypeSoinsMockMvc = MockMvcBuilders.standaloneSetup(typeSoinsResource)
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
    public static TypeSoins createEntity(EntityManager em) {
        TypeSoins typeSoins = new TypeSoins()
            .codeTypeSoins(DEFAULT_CODE_TYPE_SOINS)
            .libelleTypeSoins(DEFAULT_LIBELLE_TYPE_SOINS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeSoins;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeSoins createUpdatedEntity(EntityManager em) {
        TypeSoins typeSoins = new TypeSoins()
            .codeTypeSoins(UPDATED_CODE_TYPE_SOINS)
            .libelleTypeSoins(UPDATED_LIBELLE_TYPE_SOINS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeSoins;
    }

    @BeforeEach
    public void initTest() {
        typeSoins = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeSoins() throws Exception {
        int databaseSizeBeforeCreate = typeSoinsRepository.findAll().size();

        // Create the TypeSoins
        restTypeSoinsMockMvc.perform(post("/api/type-soins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSoins)))
            .andExpect(status().isCreated());

        // Validate the TypeSoins in the database
        List<TypeSoins> typeSoinsList = typeSoinsRepository.findAll();
        assertThat(typeSoinsList).hasSize(databaseSizeBeforeCreate + 1);
        TypeSoins testTypeSoins = typeSoinsList.get(typeSoinsList.size() - 1);
        assertThat(testTypeSoins.getCodeTypeSoins()).isEqualTo(DEFAULT_CODE_TYPE_SOINS);
        assertThat(testTypeSoins.getLibelleTypeSoins()).isEqualTo(DEFAULT_LIBELLE_TYPE_SOINS);
        assertThat(testTypeSoins.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeSoins.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeSoins.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeSoins.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeSoins.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeSoinsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeSoinsRepository.findAll().size();

        // Create the TypeSoins with an existing ID
        typeSoins.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeSoinsMockMvc.perform(post("/api/type-soins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSoins)))
            .andExpect(status().isBadRequest());

        // Validate the TypeSoins in the database
        List<TypeSoins> typeSoinsList = typeSoinsRepository.findAll();
        assertThat(typeSoinsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeSoins() throws Exception {
        // Initialize the database
        typeSoinsRepository.saveAndFlush(typeSoins);

        // Get all the typeSoinsList
        restTypeSoinsMockMvc.perform(get("/api/type-soins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeSoins.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeSoins").value(hasItem(DEFAULT_CODE_TYPE_SOINS)))
            .andExpect(jsonPath("$.[*].libelleTypeSoins").value(hasItem(DEFAULT_LIBELLE_TYPE_SOINS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeSoins() throws Exception {
        // Initialize the database
        typeSoinsRepository.saveAndFlush(typeSoins);

        // Get the typeSoins
        restTypeSoinsMockMvc.perform(get("/api/type-soins/{id}", typeSoins.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeSoins.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeSoins").value(DEFAULT_CODE_TYPE_SOINS))
            .andExpect(jsonPath("$.libelleTypeSoins").value(DEFAULT_LIBELLE_TYPE_SOINS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeSoins() throws Exception {
        // Get the typeSoins
        restTypeSoinsMockMvc.perform(get("/api/type-soins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeSoins() throws Exception {
        // Initialize the database
        typeSoinsService.save(typeSoins);

        int databaseSizeBeforeUpdate = typeSoinsRepository.findAll().size();

        // Update the typeSoins
        TypeSoins updatedTypeSoins = typeSoinsRepository.findById(typeSoins.getId()).get();
        // Disconnect from session so that the updates on updatedTypeSoins are not directly saved in db
        em.detach(updatedTypeSoins);
        updatedTypeSoins
            .codeTypeSoins(UPDATED_CODE_TYPE_SOINS)
            .libelleTypeSoins(UPDATED_LIBELLE_TYPE_SOINS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeSoinsMockMvc.perform(put("/api/type-soins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeSoins)))
            .andExpect(status().isOk());

        // Validate the TypeSoins in the database
        List<TypeSoins> typeSoinsList = typeSoinsRepository.findAll();
        assertThat(typeSoinsList).hasSize(databaseSizeBeforeUpdate);
        TypeSoins testTypeSoins = typeSoinsList.get(typeSoinsList.size() - 1);
        assertThat(testTypeSoins.getCodeTypeSoins()).isEqualTo(UPDATED_CODE_TYPE_SOINS);
        assertThat(testTypeSoins.getLibelleTypeSoins()).isEqualTo(UPDATED_LIBELLE_TYPE_SOINS);
        assertThat(testTypeSoins.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeSoins.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeSoins.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeSoins.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeSoins.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeSoins() throws Exception {
        int databaseSizeBeforeUpdate = typeSoinsRepository.findAll().size();

        // Create the TypeSoins

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeSoinsMockMvc.perform(put("/api/type-soins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSoins)))
            .andExpect(status().isBadRequest());

        // Validate the TypeSoins in the database
        List<TypeSoins> typeSoinsList = typeSoinsRepository.findAll();
        assertThat(typeSoinsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeSoins() throws Exception {
        // Initialize the database
        typeSoinsService.save(typeSoins);

        int databaseSizeBeforeDelete = typeSoinsRepository.findAll().size();

        // Delete the typeSoins
        restTypeSoinsMockMvc.perform(delete("/api/type-soins/{id}", typeSoins.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeSoins> typeSoinsList = typeSoinsRepository.findAll();
        assertThat(typeSoinsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
