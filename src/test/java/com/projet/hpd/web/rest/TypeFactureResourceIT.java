package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeFacture;
import com.projet.hpd.repository.TypeFactureRepository;
import com.projet.hpd.service.TypeFactureService;
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
 * Integration tests for the {@link TypeFactureResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeFactureResourceIT {

    private static final String DEFAULT_CODE_TYPE_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_FACTURE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_FACTURE = "BBBBBBBBBB";

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
    private TypeFactureRepository typeFactureRepository;

    @Autowired
    private TypeFactureService typeFactureService;

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

    private MockMvc restTypeFactureMockMvc;

    private TypeFacture typeFacture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeFactureResource typeFactureResource = new TypeFactureResource(typeFactureService);
        this.restTypeFactureMockMvc = MockMvcBuilders.standaloneSetup(typeFactureResource)
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
    public static TypeFacture createEntity(EntityManager em) {
        TypeFacture typeFacture = new TypeFacture()
            .codeTypeFacture(DEFAULT_CODE_TYPE_FACTURE)
            .libelleTypeFacture(DEFAULT_LIBELLE_TYPE_FACTURE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeFacture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeFacture createUpdatedEntity(EntityManager em) {
        TypeFacture typeFacture = new TypeFacture()
            .codeTypeFacture(UPDATED_CODE_TYPE_FACTURE)
            .libelleTypeFacture(UPDATED_LIBELLE_TYPE_FACTURE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeFacture;
    }

    @BeforeEach
    public void initTest() {
        typeFacture = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeFacture() throws Exception {
        int databaseSizeBeforeCreate = typeFactureRepository.findAll().size();

        // Create the TypeFacture
        restTypeFactureMockMvc.perform(post("/api/type-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFacture)))
            .andExpect(status().isCreated());

        // Validate the TypeFacture in the database
        List<TypeFacture> typeFactureList = typeFactureRepository.findAll();
        assertThat(typeFactureList).hasSize(databaseSizeBeforeCreate + 1);
        TypeFacture testTypeFacture = typeFactureList.get(typeFactureList.size() - 1);
        assertThat(testTypeFacture.getCodeTypeFacture()).isEqualTo(DEFAULT_CODE_TYPE_FACTURE);
        assertThat(testTypeFacture.getLibelleTypeFacture()).isEqualTo(DEFAULT_LIBELLE_TYPE_FACTURE);
        assertThat(testTypeFacture.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeFacture.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeFacture.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeFacture.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeFacture.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeFactureRepository.findAll().size();

        // Create the TypeFacture with an existing ID
        typeFacture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeFactureMockMvc.perform(post("/api/type-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFacture)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFacture in the database
        List<TypeFacture> typeFactureList = typeFactureRepository.findAll();
        assertThat(typeFactureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeFactures() throws Exception {
        // Initialize the database
        typeFactureRepository.saveAndFlush(typeFacture);

        // Get all the typeFactureList
        restTypeFactureMockMvc.perform(get("/api/type-factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeFacture.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeFacture").value(hasItem(DEFAULT_CODE_TYPE_FACTURE)))
            .andExpect(jsonPath("$.[*].libelleTypeFacture").value(hasItem(DEFAULT_LIBELLE_TYPE_FACTURE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeFacture() throws Exception {
        // Initialize the database
        typeFactureRepository.saveAndFlush(typeFacture);

        // Get the typeFacture
        restTypeFactureMockMvc.perform(get("/api/type-factures/{id}", typeFacture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeFacture.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeFacture").value(DEFAULT_CODE_TYPE_FACTURE))
            .andExpect(jsonPath("$.libelleTypeFacture").value(DEFAULT_LIBELLE_TYPE_FACTURE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeFacture() throws Exception {
        // Get the typeFacture
        restTypeFactureMockMvc.perform(get("/api/type-factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeFacture() throws Exception {
        // Initialize the database
        typeFactureService.save(typeFacture);

        int databaseSizeBeforeUpdate = typeFactureRepository.findAll().size();

        // Update the typeFacture
        TypeFacture updatedTypeFacture = typeFactureRepository.findById(typeFacture.getId()).get();
        // Disconnect from session so that the updates on updatedTypeFacture are not directly saved in db
        em.detach(updatedTypeFacture);
        updatedTypeFacture
            .codeTypeFacture(UPDATED_CODE_TYPE_FACTURE)
            .libelleTypeFacture(UPDATED_LIBELLE_TYPE_FACTURE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeFactureMockMvc.perform(put("/api/type-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeFacture)))
            .andExpect(status().isOk());

        // Validate the TypeFacture in the database
        List<TypeFacture> typeFactureList = typeFactureRepository.findAll();
        assertThat(typeFactureList).hasSize(databaseSizeBeforeUpdate);
        TypeFacture testTypeFacture = typeFactureList.get(typeFactureList.size() - 1);
        assertThat(testTypeFacture.getCodeTypeFacture()).isEqualTo(UPDATED_CODE_TYPE_FACTURE);
        assertThat(testTypeFacture.getLibelleTypeFacture()).isEqualTo(UPDATED_LIBELLE_TYPE_FACTURE);
        assertThat(testTypeFacture.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeFacture.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeFacture.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeFacture.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeFacture.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeFacture() throws Exception {
        int databaseSizeBeforeUpdate = typeFactureRepository.findAll().size();

        // Create the TypeFacture

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeFactureMockMvc.perform(put("/api/type-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFacture)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFacture in the database
        List<TypeFacture> typeFactureList = typeFactureRepository.findAll();
        assertThat(typeFactureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeFacture() throws Exception {
        // Initialize the database
        typeFactureService.save(typeFacture);

        int databaseSizeBeforeDelete = typeFactureRepository.findAll().size();

        // Delete the typeFacture
        restTypeFactureMockMvc.perform(delete("/api/type-factures/{id}", typeFacture.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeFacture> typeFactureList = typeFactureRepository.findAll();
        assertThat(typeFactureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
