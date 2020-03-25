package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeFournis;
import com.projet.hpd.repository.TypeFournisRepository;
import com.projet.hpd.service.TypeFournisService;
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
 * Integration tests for the {@link TypeFournisResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeFournisResourceIT {

    private static final String DEFAULT_CODE_TYPE_FOURNIS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_FOURNIS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_FOURNIS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_FOURNIS = "BBBBBBBBBB";

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
    private TypeFournisRepository typeFournisRepository;

    @Autowired
    private TypeFournisService typeFournisService;

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

    private MockMvc restTypeFournisMockMvc;

    private TypeFournis typeFournis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeFournisResource typeFournisResource = new TypeFournisResource(typeFournisService);
        this.restTypeFournisMockMvc = MockMvcBuilders.standaloneSetup(typeFournisResource)
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
    public static TypeFournis createEntity(EntityManager em) {
        TypeFournis typeFournis = new TypeFournis()
            .codeTypeFournis(DEFAULT_CODE_TYPE_FOURNIS)
            .libelleTypeFournis(DEFAULT_LIBELLE_TYPE_FOURNIS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeFournis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeFournis createUpdatedEntity(EntityManager em) {
        TypeFournis typeFournis = new TypeFournis()
            .codeTypeFournis(UPDATED_CODE_TYPE_FOURNIS)
            .libelleTypeFournis(UPDATED_LIBELLE_TYPE_FOURNIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeFournis;
    }

    @BeforeEach
    public void initTest() {
        typeFournis = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeFournis() throws Exception {
        int databaseSizeBeforeCreate = typeFournisRepository.findAll().size();

        // Create the TypeFournis
        restTypeFournisMockMvc.perform(post("/api/type-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFournis)))
            .andExpect(status().isCreated());

        // Validate the TypeFournis in the database
        List<TypeFournis> typeFournisList = typeFournisRepository.findAll();
        assertThat(typeFournisList).hasSize(databaseSizeBeforeCreate + 1);
        TypeFournis testTypeFournis = typeFournisList.get(typeFournisList.size() - 1);
        assertThat(testTypeFournis.getCodeTypeFournis()).isEqualTo(DEFAULT_CODE_TYPE_FOURNIS);
        assertThat(testTypeFournis.getLibelleTypeFournis()).isEqualTo(DEFAULT_LIBELLE_TYPE_FOURNIS);
        assertThat(testTypeFournis.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeFournis.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeFournis.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeFournis.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeFournis.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeFournisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeFournisRepository.findAll().size();

        // Create the TypeFournis with an existing ID
        typeFournis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeFournisMockMvc.perform(post("/api/type-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFournis)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFournis in the database
        List<TypeFournis> typeFournisList = typeFournisRepository.findAll();
        assertThat(typeFournisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeFournis() throws Exception {
        // Initialize the database
        typeFournisRepository.saveAndFlush(typeFournis);

        // Get all the typeFournisList
        restTypeFournisMockMvc.perform(get("/api/type-fournis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeFournis.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeFournis").value(hasItem(DEFAULT_CODE_TYPE_FOURNIS)))
            .andExpect(jsonPath("$.[*].libelleTypeFournis").value(hasItem(DEFAULT_LIBELLE_TYPE_FOURNIS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeFournis() throws Exception {
        // Initialize the database
        typeFournisRepository.saveAndFlush(typeFournis);

        // Get the typeFournis
        restTypeFournisMockMvc.perform(get("/api/type-fournis/{id}", typeFournis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeFournis.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeFournis").value(DEFAULT_CODE_TYPE_FOURNIS))
            .andExpect(jsonPath("$.libelleTypeFournis").value(DEFAULT_LIBELLE_TYPE_FOURNIS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeFournis() throws Exception {
        // Get the typeFournis
        restTypeFournisMockMvc.perform(get("/api/type-fournis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeFournis() throws Exception {
        // Initialize the database
        typeFournisService.save(typeFournis);

        int databaseSizeBeforeUpdate = typeFournisRepository.findAll().size();

        // Update the typeFournis
        TypeFournis updatedTypeFournis = typeFournisRepository.findById(typeFournis.getId()).get();
        // Disconnect from session so that the updates on updatedTypeFournis are not directly saved in db
        em.detach(updatedTypeFournis);
        updatedTypeFournis
            .codeTypeFournis(UPDATED_CODE_TYPE_FOURNIS)
            .libelleTypeFournis(UPDATED_LIBELLE_TYPE_FOURNIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeFournisMockMvc.perform(put("/api/type-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeFournis)))
            .andExpect(status().isOk());

        // Validate the TypeFournis in the database
        List<TypeFournis> typeFournisList = typeFournisRepository.findAll();
        assertThat(typeFournisList).hasSize(databaseSizeBeforeUpdate);
        TypeFournis testTypeFournis = typeFournisList.get(typeFournisList.size() - 1);
        assertThat(testTypeFournis.getCodeTypeFournis()).isEqualTo(UPDATED_CODE_TYPE_FOURNIS);
        assertThat(testTypeFournis.getLibelleTypeFournis()).isEqualTo(UPDATED_LIBELLE_TYPE_FOURNIS);
        assertThat(testTypeFournis.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeFournis.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeFournis.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeFournis.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeFournis.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeFournis() throws Exception {
        int databaseSizeBeforeUpdate = typeFournisRepository.findAll().size();

        // Create the TypeFournis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeFournisMockMvc.perform(put("/api/type-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFournis)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFournis in the database
        List<TypeFournis> typeFournisList = typeFournisRepository.findAll();
        assertThat(typeFournisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeFournis() throws Exception {
        // Initialize the database
        typeFournisService.save(typeFournis);

        int databaseSizeBeforeDelete = typeFournisRepository.findAll().size();

        // Delete the typeFournis
        restTypeFournisMockMvc.perform(delete("/api/type-fournis/{id}", typeFournis.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeFournis> typeFournisList = typeFournisRepository.findAll();
        assertThat(typeFournisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
