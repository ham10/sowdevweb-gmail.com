package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeUnite;
import com.projet.hpd.repository.TypeUniteRepository;
import com.projet.hpd.service.TypeUniteService;
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
 * Integration tests for the {@link TypeUniteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeUniteResourceIT {

    private static final String DEFAULT_CODE_TYPE_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_UNITE = "BBBBBBBBBB";

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
    private TypeUniteRepository typeUniteRepository;

    @Autowired
    private TypeUniteService typeUniteService;

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

    private MockMvc restTypeUniteMockMvc;

    private TypeUnite typeUnite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeUniteResource typeUniteResource = new TypeUniteResource(typeUniteService);
        this.restTypeUniteMockMvc = MockMvcBuilders.standaloneSetup(typeUniteResource)
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
    public static TypeUnite createEntity(EntityManager em) {
        TypeUnite typeUnite = new TypeUnite()
            .codeTypeUnite(DEFAULT_CODE_TYPE_UNITE)
            .libelleTypeUnite(DEFAULT_LIBELLE_TYPE_UNITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeUnite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeUnite createUpdatedEntity(EntityManager em) {
        TypeUnite typeUnite = new TypeUnite()
            .codeTypeUnite(UPDATED_CODE_TYPE_UNITE)
            .libelleTypeUnite(UPDATED_LIBELLE_TYPE_UNITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeUnite;
    }

    @BeforeEach
    public void initTest() {
        typeUnite = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeUnite() throws Exception {
        int databaseSizeBeforeCreate = typeUniteRepository.findAll().size();

        // Create the TypeUnite
        restTypeUniteMockMvc.perform(post("/api/type-unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUnite)))
            .andExpect(status().isCreated());

        // Validate the TypeUnite in the database
        List<TypeUnite> typeUniteList = typeUniteRepository.findAll();
        assertThat(typeUniteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeUnite testTypeUnite = typeUniteList.get(typeUniteList.size() - 1);
        assertThat(testTypeUnite.getCodeTypeUnite()).isEqualTo(DEFAULT_CODE_TYPE_UNITE);
        assertThat(testTypeUnite.getLibelleTypeUnite()).isEqualTo(DEFAULT_LIBELLE_TYPE_UNITE);
        assertThat(testTypeUnite.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeUnite.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeUnite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeUnite.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeUnite.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeUniteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeUniteRepository.findAll().size();

        // Create the TypeUnite with an existing ID
        typeUnite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeUniteMockMvc.perform(post("/api/type-unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUnite)))
            .andExpect(status().isBadRequest());

        // Validate the TypeUnite in the database
        List<TypeUnite> typeUniteList = typeUniteRepository.findAll();
        assertThat(typeUniteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeUnites() throws Exception {
        // Initialize the database
        typeUniteRepository.saveAndFlush(typeUnite);

        // Get all the typeUniteList
        restTypeUniteMockMvc.perform(get("/api/type-unites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeUnite").value(hasItem(DEFAULT_CODE_TYPE_UNITE)))
            .andExpect(jsonPath("$.[*].libelleTypeUnite").value(hasItem(DEFAULT_LIBELLE_TYPE_UNITE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeUnite() throws Exception {
        // Initialize the database
        typeUniteRepository.saveAndFlush(typeUnite);

        // Get the typeUnite
        restTypeUniteMockMvc.perform(get("/api/type-unites/{id}", typeUnite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeUnite.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeUnite").value(DEFAULT_CODE_TYPE_UNITE))
            .andExpect(jsonPath("$.libelleTypeUnite").value(DEFAULT_LIBELLE_TYPE_UNITE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeUnite() throws Exception {
        // Get the typeUnite
        restTypeUniteMockMvc.perform(get("/api/type-unites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeUnite() throws Exception {
        // Initialize the database
        typeUniteService.save(typeUnite);

        int databaseSizeBeforeUpdate = typeUniteRepository.findAll().size();

        // Update the typeUnite
        TypeUnite updatedTypeUnite = typeUniteRepository.findById(typeUnite.getId()).get();
        // Disconnect from session so that the updates on updatedTypeUnite are not directly saved in db
        em.detach(updatedTypeUnite);
        updatedTypeUnite
            .codeTypeUnite(UPDATED_CODE_TYPE_UNITE)
            .libelleTypeUnite(UPDATED_LIBELLE_TYPE_UNITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeUniteMockMvc.perform(put("/api/type-unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeUnite)))
            .andExpect(status().isOk());

        // Validate the TypeUnite in the database
        List<TypeUnite> typeUniteList = typeUniteRepository.findAll();
        assertThat(typeUniteList).hasSize(databaseSizeBeforeUpdate);
        TypeUnite testTypeUnite = typeUniteList.get(typeUniteList.size() - 1);
        assertThat(testTypeUnite.getCodeTypeUnite()).isEqualTo(UPDATED_CODE_TYPE_UNITE);
        assertThat(testTypeUnite.getLibelleTypeUnite()).isEqualTo(UPDATED_LIBELLE_TYPE_UNITE);
        assertThat(testTypeUnite.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeUnite.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeUnite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeUnite.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeUnite.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeUnite() throws Exception {
        int databaseSizeBeforeUpdate = typeUniteRepository.findAll().size();

        // Create the TypeUnite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeUniteMockMvc.perform(put("/api/type-unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeUnite)))
            .andExpect(status().isBadRequest());

        // Validate the TypeUnite in the database
        List<TypeUnite> typeUniteList = typeUniteRepository.findAll();
        assertThat(typeUniteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeUnite() throws Exception {
        // Initialize the database
        typeUniteService.save(typeUnite);

        int databaseSizeBeforeDelete = typeUniteRepository.findAll().size();

        // Delete the typeUnite
        restTypeUniteMockMvc.perform(delete("/api/type-unites/{id}", typeUnite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeUnite> typeUniteList = typeUniteRepository.findAll();
        assertThat(typeUniteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
