package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeFact;
import com.projet.hpd.repository.TypeFactRepository;
import com.projet.hpd.service.TypeFactService;
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
 * Integration tests for the {@link TypeFactResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeFactResourceIT {

    private static final String DEFAULT_CODE_TYPE_FACT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_FACT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_FACT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_FACT = "BBBBBBBBBB";

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
    private TypeFactRepository typeFactRepository;

    @Autowired
    private TypeFactService typeFactService;

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

    private MockMvc restTypeFactMockMvc;

    private TypeFact typeFact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeFactResource typeFactResource = new TypeFactResource(typeFactService);
        this.restTypeFactMockMvc = MockMvcBuilders.standaloneSetup(typeFactResource)
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
    public static TypeFact createEntity(EntityManager em) {
        TypeFact typeFact = new TypeFact()
            .codeTypeFact(DEFAULT_CODE_TYPE_FACT)
            .libelleTypeFact(DEFAULT_LIBELLE_TYPE_FACT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeFact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeFact createUpdatedEntity(EntityManager em) {
        TypeFact typeFact = new TypeFact()
            .codeTypeFact(UPDATED_CODE_TYPE_FACT)
            .libelleTypeFact(UPDATED_LIBELLE_TYPE_FACT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeFact;
    }

    @BeforeEach
    public void initTest() {
        typeFact = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeFact() throws Exception {
        int databaseSizeBeforeCreate = typeFactRepository.findAll().size();

        // Create the TypeFact
        restTypeFactMockMvc.perform(post("/api/type-facts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFact)))
            .andExpect(status().isCreated());

        // Validate the TypeFact in the database
        List<TypeFact> typeFactList = typeFactRepository.findAll();
        assertThat(typeFactList).hasSize(databaseSizeBeforeCreate + 1);
        TypeFact testTypeFact = typeFactList.get(typeFactList.size() - 1);
        assertThat(testTypeFact.getCodeTypeFact()).isEqualTo(DEFAULT_CODE_TYPE_FACT);
        assertThat(testTypeFact.getLibelleTypeFact()).isEqualTo(DEFAULT_LIBELLE_TYPE_FACT);
        assertThat(testTypeFact.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeFact.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeFact.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeFact.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeFact.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeFactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeFactRepository.findAll().size();

        // Create the TypeFact with an existing ID
        typeFact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeFactMockMvc.perform(post("/api/type-facts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFact)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFact in the database
        List<TypeFact> typeFactList = typeFactRepository.findAll();
        assertThat(typeFactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeFacts() throws Exception {
        // Initialize the database
        typeFactRepository.saveAndFlush(typeFact);

        // Get all the typeFactList
        restTypeFactMockMvc.perform(get("/api/type-facts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeFact.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeFact").value(hasItem(DEFAULT_CODE_TYPE_FACT)))
            .andExpect(jsonPath("$.[*].libelleTypeFact").value(hasItem(DEFAULT_LIBELLE_TYPE_FACT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeFact() throws Exception {
        // Initialize the database
        typeFactRepository.saveAndFlush(typeFact);

        // Get the typeFact
        restTypeFactMockMvc.perform(get("/api/type-facts/{id}", typeFact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeFact.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeFact").value(DEFAULT_CODE_TYPE_FACT))
            .andExpect(jsonPath("$.libelleTypeFact").value(DEFAULT_LIBELLE_TYPE_FACT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeFact() throws Exception {
        // Get the typeFact
        restTypeFactMockMvc.perform(get("/api/type-facts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeFact() throws Exception {
        // Initialize the database
        typeFactService.save(typeFact);

        int databaseSizeBeforeUpdate = typeFactRepository.findAll().size();

        // Update the typeFact
        TypeFact updatedTypeFact = typeFactRepository.findById(typeFact.getId()).get();
        // Disconnect from session so that the updates on updatedTypeFact are not directly saved in db
        em.detach(updatedTypeFact);
        updatedTypeFact
            .codeTypeFact(UPDATED_CODE_TYPE_FACT)
            .libelleTypeFact(UPDATED_LIBELLE_TYPE_FACT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeFactMockMvc.perform(put("/api/type-facts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeFact)))
            .andExpect(status().isOk());

        // Validate the TypeFact in the database
        List<TypeFact> typeFactList = typeFactRepository.findAll();
        assertThat(typeFactList).hasSize(databaseSizeBeforeUpdate);
        TypeFact testTypeFact = typeFactList.get(typeFactList.size() - 1);
        assertThat(testTypeFact.getCodeTypeFact()).isEqualTo(UPDATED_CODE_TYPE_FACT);
        assertThat(testTypeFact.getLibelleTypeFact()).isEqualTo(UPDATED_LIBELLE_TYPE_FACT);
        assertThat(testTypeFact.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeFact.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeFact.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeFact.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeFact.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeFact() throws Exception {
        int databaseSizeBeforeUpdate = typeFactRepository.findAll().size();

        // Create the TypeFact

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeFactMockMvc.perform(put("/api/type-facts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeFact)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFact in the database
        List<TypeFact> typeFactList = typeFactRepository.findAll();
        assertThat(typeFactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeFact() throws Exception {
        // Initialize the database
        typeFactService.save(typeFact);

        int databaseSizeBeforeDelete = typeFactRepository.findAll().size();

        // Delete the typeFact
        restTypeFactMockMvc.perform(delete("/api/type-facts/{id}", typeFact.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeFact> typeFactList = typeFactRepository.findAll();
        assertThat(typeFactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
