package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeMagasin;
import com.projet.hpd.repository.TypeMagasinRepository;
import com.projet.hpd.service.TypeMagasinService;
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
 * Integration tests for the {@link TypeMagasinResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeMagasinResourceIT {

    private static final String DEFAULT_CODE_TYPE_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_MAGASIN = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_MAGASIN = "BBBBBBBBBB";

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
    private TypeMagasinRepository typeMagasinRepository;

    @Autowired
    private TypeMagasinService typeMagasinService;

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

    private MockMvc restTypeMagasinMockMvc;

    private TypeMagasin typeMagasin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeMagasinResource typeMagasinResource = new TypeMagasinResource(typeMagasinService);
        this.restTypeMagasinMockMvc = MockMvcBuilders.standaloneSetup(typeMagasinResource)
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
    public static TypeMagasin createEntity(EntityManager em) {
        TypeMagasin typeMagasin = new TypeMagasin()
            .codeTypeMagasin(DEFAULT_CODE_TYPE_MAGASIN)
            .libelleTypeMagasin(DEFAULT_LIBELLE_TYPE_MAGASIN)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeMagasin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeMagasin createUpdatedEntity(EntityManager em) {
        TypeMagasin typeMagasin = new TypeMagasin()
            .codeTypeMagasin(UPDATED_CODE_TYPE_MAGASIN)
            .libelleTypeMagasin(UPDATED_LIBELLE_TYPE_MAGASIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeMagasin;
    }

    @BeforeEach
    public void initTest() {
        typeMagasin = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeMagasin() throws Exception {
        int databaseSizeBeforeCreate = typeMagasinRepository.findAll().size();

        // Create the TypeMagasin
        restTypeMagasinMockMvc.perform(post("/api/type-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMagasin)))
            .andExpect(status().isCreated());

        // Validate the TypeMagasin in the database
        List<TypeMagasin> typeMagasinList = typeMagasinRepository.findAll();
        assertThat(typeMagasinList).hasSize(databaseSizeBeforeCreate + 1);
        TypeMagasin testTypeMagasin = typeMagasinList.get(typeMagasinList.size() - 1);
        assertThat(testTypeMagasin.getCodeTypeMagasin()).isEqualTo(DEFAULT_CODE_TYPE_MAGASIN);
        assertThat(testTypeMagasin.getLibelleTypeMagasin()).isEqualTo(DEFAULT_LIBELLE_TYPE_MAGASIN);
        assertThat(testTypeMagasin.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeMagasin.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeMagasin.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeMagasin.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeMagasin.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeMagasinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeMagasinRepository.findAll().size();

        // Create the TypeMagasin with an existing ID
        typeMagasin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMagasinMockMvc.perform(post("/api/type-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMagasin)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMagasin in the database
        List<TypeMagasin> typeMagasinList = typeMagasinRepository.findAll();
        assertThat(typeMagasinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeMagasins() throws Exception {
        // Initialize the database
        typeMagasinRepository.saveAndFlush(typeMagasin);

        // Get all the typeMagasinList
        restTypeMagasinMockMvc.perform(get("/api/type-magasins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMagasin.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeMagasin").value(hasItem(DEFAULT_CODE_TYPE_MAGASIN)))
            .andExpect(jsonPath("$.[*].libelleTypeMagasin").value(hasItem(DEFAULT_LIBELLE_TYPE_MAGASIN)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeMagasin() throws Exception {
        // Initialize the database
        typeMagasinRepository.saveAndFlush(typeMagasin);

        // Get the typeMagasin
        restTypeMagasinMockMvc.perform(get("/api/type-magasins/{id}", typeMagasin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeMagasin.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeMagasin").value(DEFAULT_CODE_TYPE_MAGASIN))
            .andExpect(jsonPath("$.libelleTypeMagasin").value(DEFAULT_LIBELLE_TYPE_MAGASIN))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeMagasin() throws Exception {
        // Get the typeMagasin
        restTypeMagasinMockMvc.perform(get("/api/type-magasins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeMagasin() throws Exception {
        // Initialize the database
        typeMagasinService.save(typeMagasin);

        int databaseSizeBeforeUpdate = typeMagasinRepository.findAll().size();

        // Update the typeMagasin
        TypeMagasin updatedTypeMagasin = typeMagasinRepository.findById(typeMagasin.getId()).get();
        // Disconnect from session so that the updates on updatedTypeMagasin are not directly saved in db
        em.detach(updatedTypeMagasin);
        updatedTypeMagasin
            .codeTypeMagasin(UPDATED_CODE_TYPE_MAGASIN)
            .libelleTypeMagasin(UPDATED_LIBELLE_TYPE_MAGASIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeMagasinMockMvc.perform(put("/api/type-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeMagasin)))
            .andExpect(status().isOk());

        // Validate the TypeMagasin in the database
        List<TypeMagasin> typeMagasinList = typeMagasinRepository.findAll();
        assertThat(typeMagasinList).hasSize(databaseSizeBeforeUpdate);
        TypeMagasin testTypeMagasin = typeMagasinList.get(typeMagasinList.size() - 1);
        assertThat(testTypeMagasin.getCodeTypeMagasin()).isEqualTo(UPDATED_CODE_TYPE_MAGASIN);
        assertThat(testTypeMagasin.getLibelleTypeMagasin()).isEqualTo(UPDATED_LIBELLE_TYPE_MAGASIN);
        assertThat(testTypeMagasin.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeMagasin.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeMagasin.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeMagasin.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeMagasin.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeMagasin() throws Exception {
        int databaseSizeBeforeUpdate = typeMagasinRepository.findAll().size();

        // Create the TypeMagasin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeMagasinMockMvc.perform(put("/api/type-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMagasin)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMagasin in the database
        List<TypeMagasin> typeMagasinList = typeMagasinRepository.findAll();
        assertThat(typeMagasinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeMagasin() throws Exception {
        // Initialize the database
        typeMagasinService.save(typeMagasin);

        int databaseSizeBeforeDelete = typeMagasinRepository.findAll().size();

        // Delete the typeMagasin
        restTypeMagasinMockMvc.perform(delete("/api/type-magasins/{id}", typeMagasin.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeMagasin> typeMagasinList = typeMagasinRepository.findAll();
        assertThat(typeMagasinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
