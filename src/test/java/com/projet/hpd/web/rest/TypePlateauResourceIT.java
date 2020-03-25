package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePlateau;
import com.projet.hpd.repository.TypePlateauRepository;
import com.projet.hpd.service.TypePlateauService;
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
 * Integration tests for the {@link TypePlateauResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePlateauResourceIT {

    private static final String DEFAULT_CODE_TYPE_PLATEAU = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PLATEAU = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PLATEAU = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PLATEAU = "BBBBBBBBBB";

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
    private TypePlateauRepository typePlateauRepository;

    @Autowired
    private TypePlateauService typePlateauService;

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

    private MockMvc restTypePlateauMockMvc;

    private TypePlateau typePlateau;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePlateauResource typePlateauResource = new TypePlateauResource(typePlateauService);
        this.restTypePlateauMockMvc = MockMvcBuilders.standaloneSetup(typePlateauResource)
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
    public static TypePlateau createEntity(EntityManager em) {
        TypePlateau typePlateau = new TypePlateau()
            .codeTypePlateau(DEFAULT_CODE_TYPE_PLATEAU)
            .libelleTypePlateau(DEFAULT_LIBELLE_TYPE_PLATEAU)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePlateau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePlateau createUpdatedEntity(EntityManager em) {
        TypePlateau typePlateau = new TypePlateau()
            .codeTypePlateau(UPDATED_CODE_TYPE_PLATEAU)
            .libelleTypePlateau(UPDATED_LIBELLE_TYPE_PLATEAU)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePlateau;
    }

    @BeforeEach
    public void initTest() {
        typePlateau = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePlateau() throws Exception {
        int databaseSizeBeforeCreate = typePlateauRepository.findAll().size();

        // Create the TypePlateau
        restTypePlateauMockMvc.perform(post("/api/type-plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlateau)))
            .andExpect(status().isCreated());

        // Validate the TypePlateau in the database
        List<TypePlateau> typePlateauList = typePlateauRepository.findAll();
        assertThat(typePlateauList).hasSize(databaseSizeBeforeCreate + 1);
        TypePlateau testTypePlateau = typePlateauList.get(typePlateauList.size() - 1);
        assertThat(testTypePlateau.getCodeTypePlateau()).isEqualTo(DEFAULT_CODE_TYPE_PLATEAU);
        assertThat(testTypePlateau.getLibelleTypePlateau()).isEqualTo(DEFAULT_LIBELLE_TYPE_PLATEAU);
        assertThat(testTypePlateau.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePlateau.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePlateau.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePlateau.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePlateau.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePlateauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePlateauRepository.findAll().size();

        // Create the TypePlateau with an existing ID
        typePlateau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePlateauMockMvc.perform(post("/api/type-plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlateau)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlateau in the database
        List<TypePlateau> typePlateauList = typePlateauRepository.findAll();
        assertThat(typePlateauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePlateaus() throws Exception {
        // Initialize the database
        typePlateauRepository.saveAndFlush(typePlateau);

        // Get all the typePlateauList
        restTypePlateauMockMvc.perform(get("/api/type-plateaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePlateau.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePlateau").value(hasItem(DEFAULT_CODE_TYPE_PLATEAU)))
            .andExpect(jsonPath("$.[*].libelleTypePlateau").value(hasItem(DEFAULT_LIBELLE_TYPE_PLATEAU)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePlateau() throws Exception {
        // Initialize the database
        typePlateauRepository.saveAndFlush(typePlateau);

        // Get the typePlateau
        restTypePlateauMockMvc.perform(get("/api/type-plateaus/{id}", typePlateau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePlateau.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePlateau").value(DEFAULT_CODE_TYPE_PLATEAU))
            .andExpect(jsonPath("$.libelleTypePlateau").value(DEFAULT_LIBELLE_TYPE_PLATEAU))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePlateau() throws Exception {
        // Get the typePlateau
        restTypePlateauMockMvc.perform(get("/api/type-plateaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePlateau() throws Exception {
        // Initialize the database
        typePlateauService.save(typePlateau);

        int databaseSizeBeforeUpdate = typePlateauRepository.findAll().size();

        // Update the typePlateau
        TypePlateau updatedTypePlateau = typePlateauRepository.findById(typePlateau.getId()).get();
        // Disconnect from session so that the updates on updatedTypePlateau are not directly saved in db
        em.detach(updatedTypePlateau);
        updatedTypePlateau
            .codeTypePlateau(UPDATED_CODE_TYPE_PLATEAU)
            .libelleTypePlateau(UPDATED_LIBELLE_TYPE_PLATEAU)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePlateauMockMvc.perform(put("/api/type-plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePlateau)))
            .andExpect(status().isOk());

        // Validate the TypePlateau in the database
        List<TypePlateau> typePlateauList = typePlateauRepository.findAll();
        assertThat(typePlateauList).hasSize(databaseSizeBeforeUpdate);
        TypePlateau testTypePlateau = typePlateauList.get(typePlateauList.size() - 1);
        assertThat(testTypePlateau.getCodeTypePlateau()).isEqualTo(UPDATED_CODE_TYPE_PLATEAU);
        assertThat(testTypePlateau.getLibelleTypePlateau()).isEqualTo(UPDATED_LIBELLE_TYPE_PLATEAU);
        assertThat(testTypePlateau.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePlateau.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePlateau.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePlateau.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePlateau.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePlateau() throws Exception {
        int databaseSizeBeforeUpdate = typePlateauRepository.findAll().size();

        // Create the TypePlateau

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePlateauMockMvc.perform(put("/api/type-plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePlateau)))
            .andExpect(status().isBadRequest());

        // Validate the TypePlateau in the database
        List<TypePlateau> typePlateauList = typePlateauRepository.findAll();
        assertThat(typePlateauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePlateau() throws Exception {
        // Initialize the database
        typePlateauService.save(typePlateau);

        int databaseSizeBeforeDelete = typePlateauRepository.findAll().size();

        // Delete the typePlateau
        restTypePlateauMockMvc.perform(delete("/api/type-plateaus/{id}", typePlateau.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePlateau> typePlateauList = typePlateauRepository.findAll();
        assertThat(typePlateauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
