package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeSortie;
import com.projet.hpd.repository.TypeSortieRepository;
import com.projet.hpd.service.TypeSortieService;
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
 * Integration tests for the {@link TypeSortieResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeSortieResourceIT {

    private static final String DEFAULT_CODE_TYPE_SORTIE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_SORTIE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_SORTIE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_SORTIE = "BBBBBBBBBB";

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
    private TypeSortieRepository typeSortieRepository;

    @Autowired
    private TypeSortieService typeSortieService;

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

    private MockMvc restTypeSortieMockMvc;

    private TypeSortie typeSortie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeSortieResource typeSortieResource = new TypeSortieResource(typeSortieService);
        this.restTypeSortieMockMvc = MockMvcBuilders.standaloneSetup(typeSortieResource)
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
    public static TypeSortie createEntity(EntityManager em) {
        TypeSortie typeSortie = new TypeSortie()
            .codeTypeSortie(DEFAULT_CODE_TYPE_SORTIE)
            .libelleTypeSortie(DEFAULT_LIBELLE_TYPE_SORTIE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeSortie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeSortie createUpdatedEntity(EntityManager em) {
        TypeSortie typeSortie = new TypeSortie()
            .codeTypeSortie(UPDATED_CODE_TYPE_SORTIE)
            .libelleTypeSortie(UPDATED_LIBELLE_TYPE_SORTIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeSortie;
    }

    @BeforeEach
    public void initTest() {
        typeSortie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeSortie() throws Exception {
        int databaseSizeBeforeCreate = typeSortieRepository.findAll().size();

        // Create the TypeSortie
        restTypeSortieMockMvc.perform(post("/api/type-sorties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSortie)))
            .andExpect(status().isCreated());

        // Validate the TypeSortie in the database
        List<TypeSortie> typeSortieList = typeSortieRepository.findAll();
        assertThat(typeSortieList).hasSize(databaseSizeBeforeCreate + 1);
        TypeSortie testTypeSortie = typeSortieList.get(typeSortieList.size() - 1);
        assertThat(testTypeSortie.getCodeTypeSortie()).isEqualTo(DEFAULT_CODE_TYPE_SORTIE);
        assertThat(testTypeSortie.getLibelleTypeSortie()).isEqualTo(DEFAULT_LIBELLE_TYPE_SORTIE);
        assertThat(testTypeSortie.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeSortie.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeSortie.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeSortie.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeSortie.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeSortieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeSortieRepository.findAll().size();

        // Create the TypeSortie with an existing ID
        typeSortie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeSortieMockMvc.perform(post("/api/type-sorties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSortie)))
            .andExpect(status().isBadRequest());

        // Validate the TypeSortie in the database
        List<TypeSortie> typeSortieList = typeSortieRepository.findAll();
        assertThat(typeSortieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeSorties() throws Exception {
        // Initialize the database
        typeSortieRepository.saveAndFlush(typeSortie);

        // Get all the typeSortieList
        restTypeSortieMockMvc.perform(get("/api/type-sorties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeSortie.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeSortie").value(hasItem(DEFAULT_CODE_TYPE_SORTIE)))
            .andExpect(jsonPath("$.[*].libelleTypeSortie").value(hasItem(DEFAULT_LIBELLE_TYPE_SORTIE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeSortie() throws Exception {
        // Initialize the database
        typeSortieRepository.saveAndFlush(typeSortie);

        // Get the typeSortie
        restTypeSortieMockMvc.perform(get("/api/type-sorties/{id}", typeSortie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeSortie.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeSortie").value(DEFAULT_CODE_TYPE_SORTIE))
            .andExpect(jsonPath("$.libelleTypeSortie").value(DEFAULT_LIBELLE_TYPE_SORTIE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeSortie() throws Exception {
        // Get the typeSortie
        restTypeSortieMockMvc.perform(get("/api/type-sorties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeSortie() throws Exception {
        // Initialize the database
        typeSortieService.save(typeSortie);

        int databaseSizeBeforeUpdate = typeSortieRepository.findAll().size();

        // Update the typeSortie
        TypeSortie updatedTypeSortie = typeSortieRepository.findById(typeSortie.getId()).get();
        // Disconnect from session so that the updates on updatedTypeSortie are not directly saved in db
        em.detach(updatedTypeSortie);
        updatedTypeSortie
            .codeTypeSortie(UPDATED_CODE_TYPE_SORTIE)
            .libelleTypeSortie(UPDATED_LIBELLE_TYPE_SORTIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeSortieMockMvc.perform(put("/api/type-sorties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeSortie)))
            .andExpect(status().isOk());

        // Validate the TypeSortie in the database
        List<TypeSortie> typeSortieList = typeSortieRepository.findAll();
        assertThat(typeSortieList).hasSize(databaseSizeBeforeUpdate);
        TypeSortie testTypeSortie = typeSortieList.get(typeSortieList.size() - 1);
        assertThat(testTypeSortie.getCodeTypeSortie()).isEqualTo(UPDATED_CODE_TYPE_SORTIE);
        assertThat(testTypeSortie.getLibelleTypeSortie()).isEqualTo(UPDATED_LIBELLE_TYPE_SORTIE);
        assertThat(testTypeSortie.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeSortie.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeSortie.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeSortie.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeSortie.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeSortie() throws Exception {
        int databaseSizeBeforeUpdate = typeSortieRepository.findAll().size();

        // Create the TypeSortie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeSortieMockMvc.perform(put("/api/type-sorties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeSortie)))
            .andExpect(status().isBadRequest());

        // Validate the TypeSortie in the database
        List<TypeSortie> typeSortieList = typeSortieRepository.findAll();
        assertThat(typeSortieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeSortie() throws Exception {
        // Initialize the database
        typeSortieService.save(typeSortie);

        int databaseSizeBeforeDelete = typeSortieRepository.findAll().size();

        // Delete the typeSortie
        restTypeSortieMockMvc.perform(delete("/api/type-sorties/{id}", typeSortie.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeSortie> typeSortieList = typeSortieRepository.findAll();
        assertThat(typeSortieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
