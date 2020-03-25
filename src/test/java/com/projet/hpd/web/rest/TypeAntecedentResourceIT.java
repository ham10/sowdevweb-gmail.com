package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeAntecedent;
import com.projet.hpd.repository.TypeAntecedentRepository;
import com.projet.hpd.service.TypeAntecedentService;
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
 * Integration tests for the {@link TypeAntecedentResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeAntecedentResourceIT {

    private static final String DEFAULT_CODE_TYPE_ANTECEDENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_ANTECEDENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_ANTECEDENT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_ANTECEDENT = "BBBBBBBBBB";

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
    private TypeAntecedentRepository typeAntecedentRepository;

    @Autowired
    private TypeAntecedentService typeAntecedentService;

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

    private MockMvc restTypeAntecedentMockMvc;

    private TypeAntecedent typeAntecedent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeAntecedentResource typeAntecedentResource = new TypeAntecedentResource(typeAntecedentService);
        this.restTypeAntecedentMockMvc = MockMvcBuilders.standaloneSetup(typeAntecedentResource)
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
    public static TypeAntecedent createEntity(EntityManager em) {
        TypeAntecedent typeAntecedent = new TypeAntecedent()
            .codeTypeAntecedent(DEFAULT_CODE_TYPE_ANTECEDENT)
            .libelleTypeAntecedent(DEFAULT_LIBELLE_TYPE_ANTECEDENT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeAntecedent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAntecedent createUpdatedEntity(EntityManager em) {
        TypeAntecedent typeAntecedent = new TypeAntecedent()
            .codeTypeAntecedent(UPDATED_CODE_TYPE_ANTECEDENT)
            .libelleTypeAntecedent(UPDATED_LIBELLE_TYPE_ANTECEDENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeAntecedent;
    }

    @BeforeEach
    public void initTest() {
        typeAntecedent = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeAntecedent() throws Exception {
        int databaseSizeBeforeCreate = typeAntecedentRepository.findAll().size();

        // Create the TypeAntecedent
        restTypeAntecedentMockMvc.perform(post("/api/type-antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAntecedent)))
            .andExpect(status().isCreated());

        // Validate the TypeAntecedent in the database
        List<TypeAntecedent> typeAntecedentList = typeAntecedentRepository.findAll();
        assertThat(typeAntecedentList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAntecedent testTypeAntecedent = typeAntecedentList.get(typeAntecedentList.size() - 1);
        assertThat(testTypeAntecedent.getCodeTypeAntecedent()).isEqualTo(DEFAULT_CODE_TYPE_ANTECEDENT);
        assertThat(testTypeAntecedent.getLibelleTypeAntecedent()).isEqualTo(DEFAULT_LIBELLE_TYPE_ANTECEDENT);
        assertThat(testTypeAntecedent.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeAntecedent.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeAntecedent.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeAntecedent.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeAntecedent.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeAntecedentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeAntecedentRepository.findAll().size();

        // Create the TypeAntecedent with an existing ID
        typeAntecedent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAntecedentMockMvc.perform(post("/api/type-antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAntecedent)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAntecedent in the database
        List<TypeAntecedent> typeAntecedentList = typeAntecedentRepository.findAll();
        assertThat(typeAntecedentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeAntecedents() throws Exception {
        // Initialize the database
        typeAntecedentRepository.saveAndFlush(typeAntecedent);

        // Get all the typeAntecedentList
        restTypeAntecedentMockMvc.perform(get("/api/type-antecedents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAntecedent.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeAntecedent").value(hasItem(DEFAULT_CODE_TYPE_ANTECEDENT)))
            .andExpect(jsonPath("$.[*].libelleTypeAntecedent").value(hasItem(DEFAULT_LIBELLE_TYPE_ANTECEDENT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeAntecedent() throws Exception {
        // Initialize the database
        typeAntecedentRepository.saveAndFlush(typeAntecedent);

        // Get the typeAntecedent
        restTypeAntecedentMockMvc.perform(get("/api/type-antecedents/{id}", typeAntecedent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAntecedent.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeAntecedent").value(DEFAULT_CODE_TYPE_ANTECEDENT))
            .andExpect(jsonPath("$.libelleTypeAntecedent").value(DEFAULT_LIBELLE_TYPE_ANTECEDENT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeAntecedent() throws Exception {
        // Get the typeAntecedent
        restTypeAntecedentMockMvc.perform(get("/api/type-antecedents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeAntecedent() throws Exception {
        // Initialize the database
        typeAntecedentService.save(typeAntecedent);

        int databaseSizeBeforeUpdate = typeAntecedentRepository.findAll().size();

        // Update the typeAntecedent
        TypeAntecedent updatedTypeAntecedent = typeAntecedentRepository.findById(typeAntecedent.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAntecedent are not directly saved in db
        em.detach(updatedTypeAntecedent);
        updatedTypeAntecedent
            .codeTypeAntecedent(UPDATED_CODE_TYPE_ANTECEDENT)
            .libelleTypeAntecedent(UPDATED_LIBELLE_TYPE_ANTECEDENT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeAntecedentMockMvc.perform(put("/api/type-antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeAntecedent)))
            .andExpect(status().isOk());

        // Validate the TypeAntecedent in the database
        List<TypeAntecedent> typeAntecedentList = typeAntecedentRepository.findAll();
        assertThat(typeAntecedentList).hasSize(databaseSizeBeforeUpdate);
        TypeAntecedent testTypeAntecedent = typeAntecedentList.get(typeAntecedentList.size() - 1);
        assertThat(testTypeAntecedent.getCodeTypeAntecedent()).isEqualTo(UPDATED_CODE_TYPE_ANTECEDENT);
        assertThat(testTypeAntecedent.getLibelleTypeAntecedent()).isEqualTo(UPDATED_LIBELLE_TYPE_ANTECEDENT);
        assertThat(testTypeAntecedent.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeAntecedent.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeAntecedent.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeAntecedent.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeAntecedent.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeAntecedent() throws Exception {
        int databaseSizeBeforeUpdate = typeAntecedentRepository.findAll().size();

        // Create the TypeAntecedent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAntecedentMockMvc.perform(put("/api/type-antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAntecedent)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAntecedent in the database
        List<TypeAntecedent> typeAntecedentList = typeAntecedentRepository.findAll();
        assertThat(typeAntecedentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeAntecedent() throws Exception {
        // Initialize the database
        typeAntecedentService.save(typeAntecedent);

        int databaseSizeBeforeDelete = typeAntecedentRepository.findAll().size();

        // Delete the typeAntecedent
        restTypeAntecedentMockMvc.perform(delete("/api/type-antecedents/{id}", typeAntecedent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAntecedent> typeAntecedentList = typeAntecedentRepository.findAll();
        assertThat(typeAntecedentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
