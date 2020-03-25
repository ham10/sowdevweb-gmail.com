package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeLit;
import com.projet.hpd.repository.TypeLitRepository;
import com.projet.hpd.service.TypeLitService;
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
 * Integration tests for the {@link TypeLitResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeLitResourceIT {

    private static final String DEFAULT_CODE_TYPE_LIT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_LIT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_LIT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_LIT = "BBBBBBBBBB";

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
    private TypeLitRepository typeLitRepository;

    @Autowired
    private TypeLitService typeLitService;

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

    private MockMvc restTypeLitMockMvc;

    private TypeLit typeLit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeLitResource typeLitResource = new TypeLitResource(typeLitService);
        this.restTypeLitMockMvc = MockMvcBuilders.standaloneSetup(typeLitResource)
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
    public static TypeLit createEntity(EntityManager em) {
        TypeLit typeLit = new TypeLit()
            .codeTypeLit(DEFAULT_CODE_TYPE_LIT)
            .libelleTypeLit(DEFAULT_LIBELLE_TYPE_LIT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeLit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeLit createUpdatedEntity(EntityManager em) {
        TypeLit typeLit = new TypeLit()
            .codeTypeLit(UPDATED_CODE_TYPE_LIT)
            .libelleTypeLit(UPDATED_LIBELLE_TYPE_LIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeLit;
    }

    @BeforeEach
    public void initTest() {
        typeLit = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeLit() throws Exception {
        int databaseSizeBeforeCreate = typeLitRepository.findAll().size();

        // Create the TypeLit
        restTypeLitMockMvc.perform(post("/api/type-lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeLit)))
            .andExpect(status().isCreated());

        // Validate the TypeLit in the database
        List<TypeLit> typeLitList = typeLitRepository.findAll();
        assertThat(typeLitList).hasSize(databaseSizeBeforeCreate + 1);
        TypeLit testTypeLit = typeLitList.get(typeLitList.size() - 1);
        assertThat(testTypeLit.getCodeTypeLit()).isEqualTo(DEFAULT_CODE_TYPE_LIT);
        assertThat(testTypeLit.getLibelleTypeLit()).isEqualTo(DEFAULT_LIBELLE_TYPE_LIT);
        assertThat(testTypeLit.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeLit.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeLit.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeLit.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeLit.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeLitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeLitRepository.findAll().size();

        // Create the TypeLit with an existing ID
        typeLit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeLitMockMvc.perform(post("/api/type-lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeLit)))
            .andExpect(status().isBadRequest());

        // Validate the TypeLit in the database
        List<TypeLit> typeLitList = typeLitRepository.findAll();
        assertThat(typeLitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeLits() throws Exception {
        // Initialize the database
        typeLitRepository.saveAndFlush(typeLit);

        // Get all the typeLitList
        restTypeLitMockMvc.perform(get("/api/type-lits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeLit.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeLit").value(hasItem(DEFAULT_CODE_TYPE_LIT)))
            .andExpect(jsonPath("$.[*].libelleTypeLit").value(hasItem(DEFAULT_LIBELLE_TYPE_LIT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeLit() throws Exception {
        // Initialize the database
        typeLitRepository.saveAndFlush(typeLit);

        // Get the typeLit
        restTypeLitMockMvc.perform(get("/api/type-lits/{id}", typeLit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeLit.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeLit").value(DEFAULT_CODE_TYPE_LIT))
            .andExpect(jsonPath("$.libelleTypeLit").value(DEFAULT_LIBELLE_TYPE_LIT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeLit() throws Exception {
        // Get the typeLit
        restTypeLitMockMvc.perform(get("/api/type-lits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeLit() throws Exception {
        // Initialize the database
        typeLitService.save(typeLit);

        int databaseSizeBeforeUpdate = typeLitRepository.findAll().size();

        // Update the typeLit
        TypeLit updatedTypeLit = typeLitRepository.findById(typeLit.getId()).get();
        // Disconnect from session so that the updates on updatedTypeLit are not directly saved in db
        em.detach(updatedTypeLit);
        updatedTypeLit
            .codeTypeLit(UPDATED_CODE_TYPE_LIT)
            .libelleTypeLit(UPDATED_LIBELLE_TYPE_LIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeLitMockMvc.perform(put("/api/type-lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeLit)))
            .andExpect(status().isOk());

        // Validate the TypeLit in the database
        List<TypeLit> typeLitList = typeLitRepository.findAll();
        assertThat(typeLitList).hasSize(databaseSizeBeforeUpdate);
        TypeLit testTypeLit = typeLitList.get(typeLitList.size() - 1);
        assertThat(testTypeLit.getCodeTypeLit()).isEqualTo(UPDATED_CODE_TYPE_LIT);
        assertThat(testTypeLit.getLibelleTypeLit()).isEqualTo(UPDATED_LIBELLE_TYPE_LIT);
        assertThat(testTypeLit.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeLit.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeLit.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeLit.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeLit.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeLit() throws Exception {
        int databaseSizeBeforeUpdate = typeLitRepository.findAll().size();

        // Create the TypeLit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeLitMockMvc.perform(put("/api/type-lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeLit)))
            .andExpect(status().isBadRequest());

        // Validate the TypeLit in the database
        List<TypeLit> typeLitList = typeLitRepository.findAll();
        assertThat(typeLitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeLit() throws Exception {
        // Initialize the database
        typeLitService.save(typeLit);

        int databaseSizeBeforeDelete = typeLitRepository.findAll().size();

        // Delete the typeLit
        restTypeLitMockMvc.perform(delete("/api/type-lits/{id}", typeLit.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeLit> typeLitList = typeLitRepository.findAll();
        assertThat(typeLitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
