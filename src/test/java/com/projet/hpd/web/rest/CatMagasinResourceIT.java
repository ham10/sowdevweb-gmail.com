package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.CatMagasin;
import com.projet.hpd.repository.CatMagasinRepository;
import com.projet.hpd.service.CatMagasinService;
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
 * Integration tests for the {@link CatMagasinResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CatMagasinResourceIT {

    private static final String DEFAULT_CODE_CAT_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CAT_MAGASIN = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CAT_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CAT_MAGASIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_CAT_MAGASIN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_CAT_MAGASIN = "BBBBBBBBBB";

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
    private CatMagasinRepository catMagasinRepository;

    @Autowired
    private CatMagasinService catMagasinService;

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

    private MockMvc restCatMagasinMockMvc;

    private CatMagasin catMagasin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatMagasinResource catMagasinResource = new CatMagasinResource(catMagasinService);
        this.restCatMagasinMockMvc = MockMvcBuilders.standaloneSetup(catMagasinResource)
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
    public static CatMagasin createEntity(EntityManager em) {
        CatMagasin catMagasin = new CatMagasin()
            .codeCatMagasin(DEFAULT_CODE_CAT_MAGASIN)
            .libelleCatMagasin(DEFAULT_LIBELLE_CAT_MAGASIN)
            .descriptionCatMagasin(DEFAULT_DESCRIPTION_CAT_MAGASIN)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return catMagasin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatMagasin createUpdatedEntity(EntityManager em) {
        CatMagasin catMagasin = new CatMagasin()
            .codeCatMagasin(UPDATED_CODE_CAT_MAGASIN)
            .libelleCatMagasin(UPDATED_LIBELLE_CAT_MAGASIN)
            .descriptionCatMagasin(UPDATED_DESCRIPTION_CAT_MAGASIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return catMagasin;
    }

    @BeforeEach
    public void initTest() {
        catMagasin = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatMagasin() throws Exception {
        int databaseSizeBeforeCreate = catMagasinRepository.findAll().size();

        // Create the CatMagasin
        restCatMagasinMockMvc.perform(post("/api/cat-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMagasin)))
            .andExpect(status().isCreated());

        // Validate the CatMagasin in the database
        List<CatMagasin> catMagasinList = catMagasinRepository.findAll();
        assertThat(catMagasinList).hasSize(databaseSizeBeforeCreate + 1);
        CatMagasin testCatMagasin = catMagasinList.get(catMagasinList.size() - 1);
        assertThat(testCatMagasin.getCodeCatMagasin()).isEqualTo(DEFAULT_CODE_CAT_MAGASIN);
        assertThat(testCatMagasin.getLibelleCatMagasin()).isEqualTo(DEFAULT_LIBELLE_CAT_MAGASIN);
        assertThat(testCatMagasin.getDescriptionCatMagasin()).isEqualTo(DEFAULT_DESCRIPTION_CAT_MAGASIN);
        assertThat(testCatMagasin.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCatMagasin.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCatMagasin.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCatMagasin.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCatMagasin.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCatMagasinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catMagasinRepository.findAll().size();

        // Create the CatMagasin with an existing ID
        catMagasin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatMagasinMockMvc.perform(post("/api/cat-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMagasin)))
            .andExpect(status().isBadRequest());

        // Validate the CatMagasin in the database
        List<CatMagasin> catMagasinList = catMagasinRepository.findAll();
        assertThat(catMagasinList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatMagasins() throws Exception {
        // Initialize the database
        catMagasinRepository.saveAndFlush(catMagasin);

        // Get all the catMagasinList
        restCatMagasinMockMvc.perform(get("/api/cat-magasins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catMagasin.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCatMagasin").value(hasItem(DEFAULT_CODE_CAT_MAGASIN)))
            .andExpect(jsonPath("$.[*].libelleCatMagasin").value(hasItem(DEFAULT_LIBELLE_CAT_MAGASIN)))
            .andExpect(jsonPath("$.[*].descriptionCatMagasin").value(hasItem(DEFAULT_DESCRIPTION_CAT_MAGASIN)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCatMagasin() throws Exception {
        // Initialize the database
        catMagasinRepository.saveAndFlush(catMagasin);

        // Get the catMagasin
        restCatMagasinMockMvc.perform(get("/api/cat-magasins/{id}", catMagasin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catMagasin.getId().intValue()))
            .andExpect(jsonPath("$.codeCatMagasin").value(DEFAULT_CODE_CAT_MAGASIN))
            .andExpect(jsonPath("$.libelleCatMagasin").value(DEFAULT_LIBELLE_CAT_MAGASIN))
            .andExpect(jsonPath("$.descriptionCatMagasin").value(DEFAULT_DESCRIPTION_CAT_MAGASIN))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCatMagasin() throws Exception {
        // Get the catMagasin
        restCatMagasinMockMvc.perform(get("/api/cat-magasins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatMagasin() throws Exception {
        // Initialize the database
        catMagasinService.save(catMagasin);

        int databaseSizeBeforeUpdate = catMagasinRepository.findAll().size();

        // Update the catMagasin
        CatMagasin updatedCatMagasin = catMagasinRepository.findById(catMagasin.getId()).get();
        // Disconnect from session so that the updates on updatedCatMagasin are not directly saved in db
        em.detach(updatedCatMagasin);
        updatedCatMagasin
            .codeCatMagasin(UPDATED_CODE_CAT_MAGASIN)
            .libelleCatMagasin(UPDATED_LIBELLE_CAT_MAGASIN)
            .descriptionCatMagasin(UPDATED_DESCRIPTION_CAT_MAGASIN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCatMagasinMockMvc.perform(put("/api/cat-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatMagasin)))
            .andExpect(status().isOk());

        // Validate the CatMagasin in the database
        List<CatMagasin> catMagasinList = catMagasinRepository.findAll();
        assertThat(catMagasinList).hasSize(databaseSizeBeforeUpdate);
        CatMagasin testCatMagasin = catMagasinList.get(catMagasinList.size() - 1);
        assertThat(testCatMagasin.getCodeCatMagasin()).isEqualTo(UPDATED_CODE_CAT_MAGASIN);
        assertThat(testCatMagasin.getLibelleCatMagasin()).isEqualTo(UPDATED_LIBELLE_CAT_MAGASIN);
        assertThat(testCatMagasin.getDescriptionCatMagasin()).isEqualTo(UPDATED_DESCRIPTION_CAT_MAGASIN);
        assertThat(testCatMagasin.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCatMagasin.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCatMagasin.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCatMagasin.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCatMagasin.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCatMagasin() throws Exception {
        int databaseSizeBeforeUpdate = catMagasinRepository.findAll().size();

        // Create the CatMagasin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMagasinMockMvc.perform(put("/api/cat-magasins")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catMagasin)))
            .andExpect(status().isBadRequest());

        // Validate the CatMagasin in the database
        List<CatMagasin> catMagasinList = catMagasinRepository.findAll();
        assertThat(catMagasinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatMagasin() throws Exception {
        // Initialize the database
        catMagasinService.save(catMagasin);

        int databaseSizeBeforeDelete = catMagasinRepository.findAll().size();

        // Delete the catMagasin
        restCatMagasinMockMvc.perform(delete("/api/cat-magasins/{id}", catMagasin.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatMagasin> catMagasinList = catMagasinRepository.findAll();
        assertThat(catMagasinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
