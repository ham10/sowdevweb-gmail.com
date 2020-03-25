package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeDoc;
import com.projet.hpd.repository.TypeDocRepository;
import com.projet.hpd.service.TypeDocService;
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
 * Integration tests for the {@link TypeDocResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeDocResourceIT {

    private static final String DEFAULT_CODE_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_DOC = "BBBBBBBBBB";

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
    private TypeDocRepository typeDocRepository;

    @Autowired
    private TypeDocService typeDocService;

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

    private MockMvc restTypeDocMockMvc;

    private TypeDoc typeDoc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeDocResource typeDocResource = new TypeDocResource(typeDocService);
        this.restTypeDocMockMvc = MockMvcBuilders.standaloneSetup(typeDocResource)
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
    public static TypeDoc createEntity(EntityManager em) {
        TypeDoc typeDoc = new TypeDoc()
            .codeTypeDoc(DEFAULT_CODE_TYPE_DOC)
            .libelleTypeDoc(DEFAULT_LIBELLE_TYPE_DOC)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeDoc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeDoc createUpdatedEntity(EntityManager em) {
        TypeDoc typeDoc = new TypeDoc()
            .codeTypeDoc(UPDATED_CODE_TYPE_DOC)
            .libelleTypeDoc(UPDATED_LIBELLE_TYPE_DOC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeDoc;
    }

    @BeforeEach
    public void initTest() {
        typeDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDoc() throws Exception {
        int databaseSizeBeforeCreate = typeDocRepository.findAll().size();

        // Create the TypeDoc
        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDoc)))
            .andExpect(status().isCreated());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDoc testTypeDoc = typeDocList.get(typeDocList.size() - 1);
        assertThat(testTypeDoc.getCodeTypeDoc()).isEqualTo(DEFAULT_CODE_TYPE_DOC);
        assertThat(testTypeDoc.getLibelleTypeDoc()).isEqualTo(DEFAULT_LIBELLE_TYPE_DOC);
        assertThat(testTypeDoc.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeDoc.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeDoc.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeDoc.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeDoc.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDocRepository.findAll().size();

        // Create the TypeDoc with an existing ID
        typeDoc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDocMockMvc.perform(post("/api/type-docs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDoc)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeDocs() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        // Get all the typeDocList
        restTypeDocMockMvc.perform(get("/api/type-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeDoc").value(hasItem(DEFAULT_CODE_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].libelleTypeDoc").value(hasItem(DEFAULT_LIBELLE_TYPE_DOC)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeDoc() throws Exception {
        // Initialize the database
        typeDocRepository.saveAndFlush(typeDoc);

        // Get the typeDoc
        restTypeDocMockMvc.perform(get("/api/type-docs/{id}", typeDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeDoc.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeDoc").value(DEFAULT_CODE_TYPE_DOC))
            .andExpect(jsonPath("$.libelleTypeDoc").value(DEFAULT_LIBELLE_TYPE_DOC))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeDoc() throws Exception {
        // Get the typeDoc
        restTypeDocMockMvc.perform(get("/api/type-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDoc() throws Exception {
        // Initialize the database
        typeDocService.save(typeDoc);

        int databaseSizeBeforeUpdate = typeDocRepository.findAll().size();

        // Update the typeDoc
        TypeDoc updatedTypeDoc = typeDocRepository.findById(typeDoc.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDoc are not directly saved in db
        em.detach(updatedTypeDoc);
        updatedTypeDoc
            .codeTypeDoc(UPDATED_CODE_TYPE_DOC)
            .libelleTypeDoc(UPDATED_LIBELLE_TYPE_DOC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeDocMockMvc.perform(put("/api/type-docs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeDoc)))
            .andExpect(status().isOk());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeUpdate);
        TypeDoc testTypeDoc = typeDocList.get(typeDocList.size() - 1);
        assertThat(testTypeDoc.getCodeTypeDoc()).isEqualTo(UPDATED_CODE_TYPE_DOC);
        assertThat(testTypeDoc.getLibelleTypeDoc()).isEqualTo(UPDATED_LIBELLE_TYPE_DOC);
        assertThat(testTypeDoc.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeDoc.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeDoc.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeDoc.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeDoc.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDoc() throws Exception {
        int databaseSizeBeforeUpdate = typeDocRepository.findAll().size();

        // Create the TypeDoc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDocMockMvc.perform(put("/api/type-docs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeDoc)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDoc in the database
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeDoc() throws Exception {
        // Initialize the database
        typeDocService.save(typeDoc);

        int databaseSizeBeforeDelete = typeDocRepository.findAll().size();

        // Delete the typeDoc
        restTypeDocMockMvc.perform(delete("/api/type-docs/{id}", typeDoc.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeDoc> typeDocList = typeDocRepository.findAll();
        assertThat(typeDocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
