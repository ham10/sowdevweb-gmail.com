package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePlat;
import com.projet.hpd.repository.TypePlatRepository;
import com.projet.hpd.service.TypePlatService;
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
 * Integration tests for the {@link TypePlatResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePlatResourceIT {

    private static final String DEFAULT_CODE_TYPE_PLAT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PLAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PLAT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PLAT = "BBBBBBBBBB";

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
    private TypePlatRepository typePlatRepository;

    @Autowired
    private TypePlatService typePlatService;

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

    private MockMvc restTypePlatMockMvc;

    private TypePlat typePlat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePlatResource typePlatResource = new TypePlatResource(typePlatService);
        this.restTypePlatMockMvc = MockMvcBuilders.standaloneSetup(typePlatResource)
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
    public static TypePlat createEntity(EntityManager em) {
        TypePlat typePlat = new TypePlat()
            .codeTypePlat(DEFAULT_CODE_TYPE_PLAT)
            .libelleTypePlat(DEFAULT_LIBELLE_TYPE_PLAT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePlat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePlat createUpdatedEntity(EntityManager em) {
        TypePlat typePlat = new TypePlat()
            .codeTypePlat(UPDATED_CODE_TYPE_PLAT)
            .libelleTypePlat(UPDATED_LIBELLE_TYPE_PLAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePlat;
    }

    @BeforeEach
    public void initTest() {
        typePlat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePlat() throws Exception {
        int databaseSizeBeforeCreate = typePlatRepository.findAll().size();

        // Create the TypePlat
        restTypePlatMockMvc.perform(post("/api/type-plats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlat)))
            .andExpect(status().isCreated());

        // Validate the TypePlat in the database
        List<TypePlat> typePlatList = typePlatRepository.findAll();
        assertThat(typePlatList).hasSize(databaseSizeBeforeCreate + 1);
        TypePlat testTypePlat = typePlatList.get(typePlatList.size() - 1);
        assertThat(testTypePlat.getCodeTypePlat()).isEqualTo(DEFAULT_CODE_TYPE_PLAT);
        assertThat(testTypePlat.getLibelleTypePlat()).isEqualTo(DEFAULT_LIBELLE_TYPE_PLAT);
        assertThat(testTypePlat.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePlat.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePlat.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePlat.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePlat.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePlatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePlatRepository.findAll().size();

        // Create the TypePlat with an existing ID
        typePlat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePlatMockMvc.perform(post("/api/type-plats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlat)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlat in the database
        List<TypePlat> typePlatList = typePlatRepository.findAll();
        assertThat(typePlatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePlats() throws Exception {
        // Initialize the database
        typePlatRepository.saveAndFlush(typePlat);

        // Get all the typePlatList
        restTypePlatMockMvc.perform(get("/api/type-plats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePlat.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePlat").value(hasItem(DEFAULT_CODE_TYPE_PLAT)))
            .andExpect(jsonPath("$.[*].libelleTypePlat").value(hasItem(DEFAULT_LIBELLE_TYPE_PLAT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePlat() throws Exception {
        // Initialize the database
        typePlatRepository.saveAndFlush(typePlat);

        // Get the typePlat
        restTypePlatMockMvc.perform(get("/api/type-plats/{id}", typePlat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePlat.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePlat").value(DEFAULT_CODE_TYPE_PLAT))
            .andExpect(jsonPath("$.libelleTypePlat").value(DEFAULT_LIBELLE_TYPE_PLAT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePlat() throws Exception {
        // Get the typePlat
        restTypePlatMockMvc.perform(get("/api/type-plats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePlat() throws Exception {
        // Initialize the database
        typePlatService.save(typePlat);

        int databaseSizeBeforeUpdate = typePlatRepository.findAll().size();

        // Update the typePlat
        TypePlat updatedTypePlat = typePlatRepository.findById(typePlat.getId()).get();
        // Disconnect from session so that the updates on updatedTypePlat are not directly saved in db
        em.detach(updatedTypePlat);
        updatedTypePlat
            .codeTypePlat(UPDATED_CODE_TYPE_PLAT)
            .libelleTypePlat(UPDATED_LIBELLE_TYPE_PLAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePlatMockMvc.perform(put("/api/type-plats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePlat)))
            .andExpect(status().isOk());

        // Validate the TypePlat in the database
        List<TypePlat> typePlatList = typePlatRepository.findAll();
        assertThat(typePlatList).hasSize(databaseSizeBeforeUpdate);
        TypePlat testTypePlat = typePlatList.get(typePlatList.size() - 1);
        assertThat(testTypePlat.getCodeTypePlat()).isEqualTo(UPDATED_CODE_TYPE_PLAT);
        assertThat(testTypePlat.getLibelleTypePlat()).isEqualTo(UPDATED_LIBELLE_TYPE_PLAT);
        assertThat(testTypePlat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePlat.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePlat.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePlat.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePlat.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePlat() throws Exception {
        int databaseSizeBeforeUpdate = typePlatRepository.findAll().size();

        // Create the TypePlat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePlatMockMvc.perform(put("/api/type-plats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlat)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlat in the database
        List<TypePlat> typePlatList = typePlatRepository.findAll();
        assertThat(typePlatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePlat() throws Exception {
        // Initialize the database
        typePlatService.save(typePlat);

        int databaseSizeBeforeDelete = typePlatRepository.findAll().size();

        // Delete the typePlat
        restTypePlatMockMvc.perform(delete("/api/type-plats/{id}", typePlat.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePlat> typePlatList = typePlatRepository.findAll();
        assertThat(typePlatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
