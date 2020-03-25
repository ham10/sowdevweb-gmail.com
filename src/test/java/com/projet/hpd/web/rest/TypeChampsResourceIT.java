package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeChamps;
import com.projet.hpd.repository.TypeChampsRepository;
import com.projet.hpd.service.TypeChampsService;
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
 * Integration tests for the {@link TypeChampsResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeChampsResourceIT {

    private static final String DEFAULT_CODE_TYPE_CHAMPS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_CHAMPS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_CHAMPS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_CHAMPS = "BBBBBBBBBB";

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
    private TypeChampsRepository typeChampsRepository;

    @Autowired
    private TypeChampsService typeChampsService;

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

    private MockMvc restTypeChampsMockMvc;

    private TypeChamps typeChamps;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeChampsResource typeChampsResource = new TypeChampsResource(typeChampsService);
        this.restTypeChampsMockMvc = MockMvcBuilders.standaloneSetup(typeChampsResource)
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
    public static TypeChamps createEntity(EntityManager em) {
        TypeChamps typeChamps = new TypeChamps()
            .codeTypeChamps(DEFAULT_CODE_TYPE_CHAMPS)
            .libelleTypeChamps(DEFAULT_LIBELLE_TYPE_CHAMPS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeChamps;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeChamps createUpdatedEntity(EntityManager em) {
        TypeChamps typeChamps = new TypeChamps()
            .codeTypeChamps(UPDATED_CODE_TYPE_CHAMPS)
            .libelleTypeChamps(UPDATED_LIBELLE_TYPE_CHAMPS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeChamps;
    }

    @BeforeEach
    public void initTest() {
        typeChamps = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeChamps() throws Exception {
        int databaseSizeBeforeCreate = typeChampsRepository.findAll().size();

        // Create the TypeChamps
        restTypeChampsMockMvc.perform(post("/api/type-champs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChamps)))
            .andExpect(status().isCreated());

        // Validate the TypeChamps in the database
        List<TypeChamps> typeChampsList = typeChampsRepository.findAll();
        assertThat(typeChampsList).hasSize(databaseSizeBeforeCreate + 1);
        TypeChamps testTypeChamps = typeChampsList.get(typeChampsList.size() - 1);
        assertThat(testTypeChamps.getCodeTypeChamps()).isEqualTo(DEFAULT_CODE_TYPE_CHAMPS);
        assertThat(testTypeChamps.getLibelleTypeChamps()).isEqualTo(DEFAULT_LIBELLE_TYPE_CHAMPS);
        assertThat(testTypeChamps.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeChamps.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeChamps.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeChamps.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeChamps.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeChampsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeChampsRepository.findAll().size();

        // Create the TypeChamps with an existing ID
        typeChamps.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeChampsMockMvc.perform(post("/api/type-champs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChamps)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChamps in the database
        List<TypeChamps> typeChampsList = typeChampsRepository.findAll();
        assertThat(typeChampsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeChamps() throws Exception {
        // Initialize the database
        typeChampsRepository.saveAndFlush(typeChamps);

        // Get all the typeChampsList
        restTypeChampsMockMvc.perform(get("/api/type-champs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeChamps.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeChamps").value(hasItem(DEFAULT_CODE_TYPE_CHAMPS)))
            .andExpect(jsonPath("$.[*].libelleTypeChamps").value(hasItem(DEFAULT_LIBELLE_TYPE_CHAMPS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeChamps() throws Exception {
        // Initialize the database
        typeChampsRepository.saveAndFlush(typeChamps);

        // Get the typeChamps
        restTypeChampsMockMvc.perform(get("/api/type-champs/{id}", typeChamps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeChamps.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeChamps").value(DEFAULT_CODE_TYPE_CHAMPS))
            .andExpect(jsonPath("$.libelleTypeChamps").value(DEFAULT_LIBELLE_TYPE_CHAMPS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeChamps() throws Exception {
        // Get the typeChamps
        restTypeChampsMockMvc.perform(get("/api/type-champs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeChamps() throws Exception {
        // Initialize the database
        typeChampsService.save(typeChamps);

        int databaseSizeBeforeUpdate = typeChampsRepository.findAll().size();

        // Update the typeChamps
        TypeChamps updatedTypeChamps = typeChampsRepository.findById(typeChamps.getId()).get();
        // Disconnect from session so that the updates on updatedTypeChamps are not directly saved in db
        em.detach(updatedTypeChamps);
        updatedTypeChamps
            .codeTypeChamps(UPDATED_CODE_TYPE_CHAMPS)
            .libelleTypeChamps(UPDATED_LIBELLE_TYPE_CHAMPS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeChampsMockMvc.perform(put("/api/type-champs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeChamps)))
            .andExpect(status().isOk());

        // Validate the TypeChamps in the database
        List<TypeChamps> typeChampsList = typeChampsRepository.findAll();
        assertThat(typeChampsList).hasSize(databaseSizeBeforeUpdate);
        TypeChamps testTypeChamps = typeChampsList.get(typeChampsList.size() - 1);
        assertThat(testTypeChamps.getCodeTypeChamps()).isEqualTo(UPDATED_CODE_TYPE_CHAMPS);
        assertThat(testTypeChamps.getLibelleTypeChamps()).isEqualTo(UPDATED_LIBELLE_TYPE_CHAMPS);
        assertThat(testTypeChamps.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeChamps.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeChamps.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeChamps.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeChamps.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeChamps() throws Exception {
        int databaseSizeBeforeUpdate = typeChampsRepository.findAll().size();

        // Create the TypeChamps

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeChampsMockMvc.perform(put("/api/type-champs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeChamps)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChamps in the database
        List<TypeChamps> typeChampsList = typeChampsRepository.findAll();
        assertThat(typeChampsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeChamps() throws Exception {
        // Initialize the database
        typeChampsService.save(typeChamps);

        int databaseSizeBeforeDelete = typeChampsRepository.findAll().size();

        // Delete the typeChamps
        restTypeChampsMockMvc.perform(delete("/api/type-champs/{id}", typeChamps.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeChamps> typeChampsList = typeChampsRepository.findAll();
        assertThat(typeChampsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
