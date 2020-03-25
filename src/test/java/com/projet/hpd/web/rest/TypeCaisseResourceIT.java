package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeCaisse;
import com.projet.hpd.repository.TypeCaisseRepository;
import com.projet.hpd.service.TypeCaisseService;
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
 * Integration tests for the {@link TypeCaisseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeCaisseResourceIT {

    private static final String DEFAULT_CODE_TYPE_CAISSE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_CAISSE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_CAISSE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_CAISSE = "BBBBBBBBBB";

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
    private TypeCaisseRepository typeCaisseRepository;

    @Autowired
    private TypeCaisseService typeCaisseService;

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

    private MockMvc restTypeCaisseMockMvc;

    private TypeCaisse typeCaisse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCaisseResource typeCaisseResource = new TypeCaisseResource(typeCaisseService);
        this.restTypeCaisseMockMvc = MockMvcBuilders.standaloneSetup(typeCaisseResource)
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
    public static TypeCaisse createEntity(EntityManager em) {
        TypeCaisse typeCaisse = new TypeCaisse()
            .codeTypeCaisse(DEFAULT_CODE_TYPE_CAISSE)
            .libelleTypeCaisse(DEFAULT_LIBELLE_TYPE_CAISSE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeCaisse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCaisse createUpdatedEntity(EntityManager em) {
        TypeCaisse typeCaisse = new TypeCaisse()
            .codeTypeCaisse(UPDATED_CODE_TYPE_CAISSE)
            .libelleTypeCaisse(UPDATED_LIBELLE_TYPE_CAISSE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeCaisse;
    }

    @BeforeEach
    public void initTest() {
        typeCaisse = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCaisse() throws Exception {
        int databaseSizeBeforeCreate = typeCaisseRepository.findAll().size();

        // Create the TypeCaisse
        restTypeCaisseMockMvc.perform(post("/api/type-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCaisse)))
            .andExpect(status().isCreated());

        // Validate the TypeCaisse in the database
        List<TypeCaisse> typeCaisseList = typeCaisseRepository.findAll();
        assertThat(typeCaisseList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCaisse testTypeCaisse = typeCaisseList.get(typeCaisseList.size() - 1);
        assertThat(testTypeCaisse.getCodeTypeCaisse()).isEqualTo(DEFAULT_CODE_TYPE_CAISSE);
        assertThat(testTypeCaisse.getLibelleTypeCaisse()).isEqualTo(DEFAULT_LIBELLE_TYPE_CAISSE);
        assertThat(testTypeCaisse.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeCaisse.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeCaisse.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeCaisse.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeCaisse.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeCaisseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCaisseRepository.findAll().size();

        // Create the TypeCaisse with an existing ID
        typeCaisse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCaisseMockMvc.perform(post("/api/type-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCaisse)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCaisse in the database
        List<TypeCaisse> typeCaisseList = typeCaisseRepository.findAll();
        assertThat(typeCaisseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeCaisses() throws Exception {
        // Initialize the database
        typeCaisseRepository.saveAndFlush(typeCaisse);

        // Get all the typeCaisseList
        restTypeCaisseMockMvc.perform(get("/api/type-caisses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCaisse.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeCaisse").value(hasItem(DEFAULT_CODE_TYPE_CAISSE)))
            .andExpect(jsonPath("$.[*].libelleTypeCaisse").value(hasItem(DEFAULT_LIBELLE_TYPE_CAISSE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeCaisse() throws Exception {
        // Initialize the database
        typeCaisseRepository.saveAndFlush(typeCaisse);

        // Get the typeCaisse
        restTypeCaisseMockMvc.perform(get("/api/type-caisses/{id}", typeCaisse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeCaisse.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeCaisse").value(DEFAULT_CODE_TYPE_CAISSE))
            .andExpect(jsonPath("$.libelleTypeCaisse").value(DEFAULT_LIBELLE_TYPE_CAISSE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCaisse() throws Exception {
        // Get the typeCaisse
        restTypeCaisseMockMvc.perform(get("/api/type-caisses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCaisse() throws Exception {
        // Initialize the database
        typeCaisseService.save(typeCaisse);

        int databaseSizeBeforeUpdate = typeCaisseRepository.findAll().size();

        // Update the typeCaisse
        TypeCaisse updatedTypeCaisse = typeCaisseRepository.findById(typeCaisse.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCaisse are not directly saved in db
        em.detach(updatedTypeCaisse);
        updatedTypeCaisse
            .codeTypeCaisse(UPDATED_CODE_TYPE_CAISSE)
            .libelleTypeCaisse(UPDATED_LIBELLE_TYPE_CAISSE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeCaisseMockMvc.perform(put("/api/type-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeCaisse)))
            .andExpect(status().isOk());

        // Validate the TypeCaisse in the database
        List<TypeCaisse> typeCaisseList = typeCaisseRepository.findAll();
        assertThat(typeCaisseList).hasSize(databaseSizeBeforeUpdate);
        TypeCaisse testTypeCaisse = typeCaisseList.get(typeCaisseList.size() - 1);
        assertThat(testTypeCaisse.getCodeTypeCaisse()).isEqualTo(UPDATED_CODE_TYPE_CAISSE);
        assertThat(testTypeCaisse.getLibelleTypeCaisse()).isEqualTo(UPDATED_LIBELLE_TYPE_CAISSE);
        assertThat(testTypeCaisse.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeCaisse.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeCaisse.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeCaisse.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeCaisse.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCaisse() throws Exception {
        int databaseSizeBeforeUpdate = typeCaisseRepository.findAll().size();

        // Create the TypeCaisse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCaisseMockMvc.perform(put("/api/type-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCaisse)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCaisse in the database
        List<TypeCaisse> typeCaisseList = typeCaisseRepository.findAll();
        assertThat(typeCaisseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCaisse() throws Exception {
        // Initialize the database
        typeCaisseService.save(typeCaisse);

        int databaseSizeBeforeDelete = typeCaisseRepository.findAll().size();

        // Delete the typeCaisse
        restTypeCaisseMockMvc.perform(delete("/api/type-caisses/{id}", typeCaisse.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeCaisse> typeCaisseList = typeCaisseRepository.findAll();
        assertThat(typeCaisseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
