package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePole;
import com.projet.hpd.repository.TypePoleRepository;
import com.projet.hpd.service.TypePoleService;
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
 * Integration tests for the {@link TypePoleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePoleResourceIT {

    private static final String DEFAULT_CODE_TYPE_POLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_POLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_POLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_POLE = "BBBBBBBBBB";

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
    private TypePoleRepository typePoleRepository;

    @Autowired
    private TypePoleService typePoleService;

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

    private MockMvc restTypePoleMockMvc;

    private TypePole typePole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePoleResource typePoleResource = new TypePoleResource(typePoleService);
        this.restTypePoleMockMvc = MockMvcBuilders.standaloneSetup(typePoleResource)
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
    public static TypePole createEntity(EntityManager em) {
        TypePole typePole = new TypePole()
            .codeTypePole(DEFAULT_CODE_TYPE_POLE)
            .libelleTypePole(DEFAULT_LIBELLE_TYPE_POLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePole createUpdatedEntity(EntityManager em) {
        TypePole typePole = new TypePole()
            .codeTypePole(UPDATED_CODE_TYPE_POLE)
            .libelleTypePole(UPDATED_LIBELLE_TYPE_POLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePole;
    }

    @BeforeEach
    public void initTest() {
        typePole = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePole() throws Exception {
        int databaseSizeBeforeCreate = typePoleRepository.findAll().size();

        // Create the TypePole
        restTypePoleMockMvc.perform(post("/api/type-poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePole)))
            .andExpect(status().isCreated());

        // Validate the TypePole in the database
        List<TypePole> typePoleList = typePoleRepository.findAll();
        assertThat(typePoleList).hasSize(databaseSizeBeforeCreate + 1);
        TypePole testTypePole = typePoleList.get(typePoleList.size() - 1);
        assertThat(testTypePole.getCodeTypePole()).isEqualTo(DEFAULT_CODE_TYPE_POLE);
        assertThat(testTypePole.getLibelleTypePole()).isEqualTo(DEFAULT_LIBELLE_TYPE_POLE);
        assertThat(testTypePole.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePole.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePole.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePole.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePole.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePoleRepository.findAll().size();

        // Create the TypePole with an existing ID
        typePole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePoleMockMvc.perform(post("/api/type-poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePole)))
            .andExpect(status().isBadRequest());

        // Validate the TypePole in the database
        List<TypePole> typePoleList = typePoleRepository.findAll();
        assertThat(typePoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePoles() throws Exception {
        // Initialize the database
        typePoleRepository.saveAndFlush(typePole);

        // Get all the typePoleList
        restTypePoleMockMvc.perform(get("/api/type-poles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePole.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePole").value(hasItem(DEFAULT_CODE_TYPE_POLE)))
            .andExpect(jsonPath("$.[*].libelleTypePole").value(hasItem(DEFAULT_LIBELLE_TYPE_POLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePole() throws Exception {
        // Initialize the database
        typePoleRepository.saveAndFlush(typePole);

        // Get the typePole
        restTypePoleMockMvc.perform(get("/api/type-poles/{id}", typePole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePole.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePole").value(DEFAULT_CODE_TYPE_POLE))
            .andExpect(jsonPath("$.libelleTypePole").value(DEFAULT_LIBELLE_TYPE_POLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePole() throws Exception {
        // Get the typePole
        restTypePoleMockMvc.perform(get("/api/type-poles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePole() throws Exception {
        // Initialize the database
        typePoleService.save(typePole);

        int databaseSizeBeforeUpdate = typePoleRepository.findAll().size();

        // Update the typePole
        TypePole updatedTypePole = typePoleRepository.findById(typePole.getId()).get();
        // Disconnect from session so that the updates on updatedTypePole are not directly saved in db
        em.detach(updatedTypePole);
        updatedTypePole
            .codeTypePole(UPDATED_CODE_TYPE_POLE)
            .libelleTypePole(UPDATED_LIBELLE_TYPE_POLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePoleMockMvc.perform(put("/api/type-poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePole)))
            .andExpect(status().isOk());

        // Validate the TypePole in the database
        List<TypePole> typePoleList = typePoleRepository.findAll();
        assertThat(typePoleList).hasSize(databaseSizeBeforeUpdate);
        TypePole testTypePole = typePoleList.get(typePoleList.size() - 1);
        assertThat(testTypePole.getCodeTypePole()).isEqualTo(UPDATED_CODE_TYPE_POLE);
        assertThat(testTypePole.getLibelleTypePole()).isEqualTo(UPDATED_LIBELLE_TYPE_POLE);
        assertThat(testTypePole.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePole.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePole.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePole.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePole.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePole() throws Exception {
        int databaseSizeBeforeUpdate = typePoleRepository.findAll().size();

        // Create the TypePole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePoleMockMvc.perform(put("/api/type-poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePole)))
            .andExpect(status().isBadRequest());

        // Validate the TypePole in the database
        List<TypePole> typePoleList = typePoleRepository.findAll();
        assertThat(typePoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePole() throws Exception {
        // Initialize the database
        typePoleService.save(typePole);

        int databaseSizeBeforeDelete = typePoleRepository.findAll().size();

        // Delete the typePole
        restTypePoleMockMvc.perform(delete("/api/type-poles/{id}", typePole.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePole> typePoleList = typePoleRepository.findAll();
        assertThat(typePoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
