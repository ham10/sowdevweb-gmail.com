package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ClasseProd;
import com.projet.hpd.repository.ClasseProdRepository;
import com.projet.hpd.service.ClasseProdService;
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
 * Integration tests for the {@link ClasseProdResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ClasseProdResourceIT {

    private static final String DEFAULT_CODE_CLASSE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CLASSE_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CLASSE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CLASSE_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_CLASSE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_CLASSE_PROD = "BBBBBBBBBB";

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
    private ClasseProdRepository classeProdRepository;

    @Autowired
    private ClasseProdService classeProdService;

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

    private MockMvc restClasseProdMockMvc;

    private ClasseProd classeProd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClasseProdResource classeProdResource = new ClasseProdResource(classeProdService);
        this.restClasseProdMockMvc = MockMvcBuilders.standaloneSetup(classeProdResource)
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
    public static ClasseProd createEntity(EntityManager em) {
        ClasseProd classeProd = new ClasseProd()
            .codeClasseProd(DEFAULT_CODE_CLASSE_PROD)
            .libelleClasseProd(DEFAULT_LIBELLE_CLASSE_PROD)
            .descriptionClasseProd(DEFAULT_DESCRIPTION_CLASSE_PROD)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return classeProd;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseProd createUpdatedEntity(EntityManager em) {
        ClasseProd classeProd = new ClasseProd()
            .codeClasseProd(UPDATED_CODE_CLASSE_PROD)
            .libelleClasseProd(UPDATED_LIBELLE_CLASSE_PROD)
            .descriptionClasseProd(UPDATED_DESCRIPTION_CLASSE_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return classeProd;
    }

    @BeforeEach
    public void initTest() {
        classeProd = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasseProd() throws Exception {
        int databaseSizeBeforeCreate = classeProdRepository.findAll().size();

        // Create the ClasseProd
        restClasseProdMockMvc.perform(post("/api/classe-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeProd)))
            .andExpect(status().isCreated());

        // Validate the ClasseProd in the database
        List<ClasseProd> classeProdList = classeProdRepository.findAll();
        assertThat(classeProdList).hasSize(databaseSizeBeforeCreate + 1);
        ClasseProd testClasseProd = classeProdList.get(classeProdList.size() - 1);
        assertThat(testClasseProd.getCodeClasseProd()).isEqualTo(DEFAULT_CODE_CLASSE_PROD);
        assertThat(testClasseProd.getLibelleClasseProd()).isEqualTo(DEFAULT_LIBELLE_CLASSE_PROD);
        assertThat(testClasseProd.getDescriptionClasseProd()).isEqualTo(DEFAULT_DESCRIPTION_CLASSE_PROD);
        assertThat(testClasseProd.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testClasseProd.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testClasseProd.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testClasseProd.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testClasseProd.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createClasseProdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeProdRepository.findAll().size();

        // Create the ClasseProd with an existing ID
        classeProd.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseProdMockMvc.perform(post("/api/classe-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeProd)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseProd in the database
        List<ClasseProd> classeProdList = classeProdRepository.findAll();
        assertThat(classeProdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClasseProds() throws Exception {
        // Initialize the database
        classeProdRepository.saveAndFlush(classeProd);

        // Get all the classeProdList
        restClasseProdMockMvc.perform(get("/api/classe-prods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classeProd.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeClasseProd").value(hasItem(DEFAULT_CODE_CLASSE_PROD)))
            .andExpect(jsonPath("$.[*].libelleClasseProd").value(hasItem(DEFAULT_LIBELLE_CLASSE_PROD)))
            .andExpect(jsonPath("$.[*].descriptionClasseProd").value(hasItem(DEFAULT_DESCRIPTION_CLASSE_PROD)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getClasseProd() throws Exception {
        // Initialize the database
        classeProdRepository.saveAndFlush(classeProd);

        // Get the classeProd
        restClasseProdMockMvc.perform(get("/api/classe-prods/{id}", classeProd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classeProd.getId().intValue()))
            .andExpect(jsonPath("$.codeClasseProd").value(DEFAULT_CODE_CLASSE_PROD))
            .andExpect(jsonPath("$.libelleClasseProd").value(DEFAULT_LIBELLE_CLASSE_PROD))
            .andExpect(jsonPath("$.descriptionClasseProd").value(DEFAULT_DESCRIPTION_CLASSE_PROD))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClasseProd() throws Exception {
        // Get the classeProd
        restClasseProdMockMvc.perform(get("/api/classe-prods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasseProd() throws Exception {
        // Initialize the database
        classeProdService.save(classeProd);

        int databaseSizeBeforeUpdate = classeProdRepository.findAll().size();

        // Update the classeProd
        ClasseProd updatedClasseProd = classeProdRepository.findById(classeProd.getId()).get();
        // Disconnect from session so that the updates on updatedClasseProd are not directly saved in db
        em.detach(updatedClasseProd);
        updatedClasseProd
            .codeClasseProd(UPDATED_CODE_CLASSE_PROD)
            .libelleClasseProd(UPDATED_LIBELLE_CLASSE_PROD)
            .descriptionClasseProd(UPDATED_DESCRIPTION_CLASSE_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restClasseProdMockMvc.perform(put("/api/classe-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasseProd)))
            .andExpect(status().isOk());

        // Validate the ClasseProd in the database
        List<ClasseProd> classeProdList = classeProdRepository.findAll();
        assertThat(classeProdList).hasSize(databaseSizeBeforeUpdate);
        ClasseProd testClasseProd = classeProdList.get(classeProdList.size() - 1);
        assertThat(testClasseProd.getCodeClasseProd()).isEqualTo(UPDATED_CODE_CLASSE_PROD);
        assertThat(testClasseProd.getLibelleClasseProd()).isEqualTo(UPDATED_LIBELLE_CLASSE_PROD);
        assertThat(testClasseProd.getDescriptionClasseProd()).isEqualTo(UPDATED_DESCRIPTION_CLASSE_PROD);
        assertThat(testClasseProd.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testClasseProd.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testClasseProd.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testClasseProd.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testClasseProd.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingClasseProd() throws Exception {
        int databaseSizeBeforeUpdate = classeProdRepository.findAll().size();

        // Create the ClasseProd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseProdMockMvc.perform(put("/api/classe-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeProd)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseProd in the database
        List<ClasseProd> classeProdList = classeProdRepository.findAll();
        assertThat(classeProdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasseProd() throws Exception {
        // Initialize the database
        classeProdService.save(classeProd);

        int databaseSizeBeforeDelete = classeProdRepository.findAll().size();

        // Delete the classeProd
        restClasseProdMockMvc.perform(delete("/api/classe-prods/{id}", classeProd.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClasseProd> classeProdList = classeProdRepository.findAll();
        assertThat(classeProdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
