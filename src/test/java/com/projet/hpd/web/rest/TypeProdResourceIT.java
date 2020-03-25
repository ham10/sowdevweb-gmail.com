package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeProd;
import com.projet.hpd.repository.TypeProdRepository;
import com.projet.hpd.service.TypeProdService;
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
 * Integration tests for the {@link TypeProdResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeProdResourceIT {

    private static final String DEFAULT_CODE_TYPE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PROD = "BBBBBBBBBB";

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
    private TypeProdRepository typeProdRepository;

    @Autowired
    private TypeProdService typeProdService;

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

    private MockMvc restTypeProdMockMvc;

    private TypeProd typeProd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeProdResource typeProdResource = new TypeProdResource(typeProdService);
        this.restTypeProdMockMvc = MockMvcBuilders.standaloneSetup(typeProdResource)
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
    public static TypeProd createEntity(EntityManager em) {
        TypeProd typeProd = new TypeProd()
            .codeTypeProd(DEFAULT_CODE_TYPE_PROD)
            .libelleTypeProd(DEFAULT_LIBELLE_TYPE_PROD)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeProd;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeProd createUpdatedEntity(EntityManager em) {
        TypeProd typeProd = new TypeProd()
            .codeTypeProd(UPDATED_CODE_TYPE_PROD)
            .libelleTypeProd(UPDATED_LIBELLE_TYPE_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeProd;
    }

    @BeforeEach
    public void initTest() {
        typeProd = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeProd() throws Exception {
        int databaseSizeBeforeCreate = typeProdRepository.findAll().size();

        // Create the TypeProd
        restTypeProdMockMvc.perform(post("/api/type-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeProd)))
            .andExpect(status().isCreated());

        // Validate the TypeProd in the database
        List<TypeProd> typeProdList = typeProdRepository.findAll();
        assertThat(typeProdList).hasSize(databaseSizeBeforeCreate + 1);
        TypeProd testTypeProd = typeProdList.get(typeProdList.size() - 1);
        assertThat(testTypeProd.getCodeTypeProd()).isEqualTo(DEFAULT_CODE_TYPE_PROD);
        assertThat(testTypeProd.getLibelleTypeProd()).isEqualTo(DEFAULT_LIBELLE_TYPE_PROD);
        assertThat(testTypeProd.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeProd.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeProd.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeProd.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeProd.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeProdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeProdRepository.findAll().size();

        // Create the TypeProd with an existing ID
        typeProd.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeProdMockMvc.perform(post("/api/type-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeProd)))
            .andExpect(status().isBadRequest());

        // Validate the TypeProd in the database
        List<TypeProd> typeProdList = typeProdRepository.findAll();
        assertThat(typeProdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeProds() throws Exception {
        // Initialize the database
        typeProdRepository.saveAndFlush(typeProd);

        // Get all the typeProdList
        restTypeProdMockMvc.perform(get("/api/type-prods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeProd.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeProd").value(hasItem(DEFAULT_CODE_TYPE_PROD)))
            .andExpect(jsonPath("$.[*].libelleTypeProd").value(hasItem(DEFAULT_LIBELLE_TYPE_PROD)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeProd() throws Exception {
        // Initialize the database
        typeProdRepository.saveAndFlush(typeProd);

        // Get the typeProd
        restTypeProdMockMvc.perform(get("/api/type-prods/{id}", typeProd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeProd.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeProd").value(DEFAULT_CODE_TYPE_PROD))
            .andExpect(jsonPath("$.libelleTypeProd").value(DEFAULT_LIBELLE_TYPE_PROD))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeProd() throws Exception {
        // Get the typeProd
        restTypeProdMockMvc.perform(get("/api/type-prods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeProd() throws Exception {
        // Initialize the database
        typeProdService.save(typeProd);

        int databaseSizeBeforeUpdate = typeProdRepository.findAll().size();

        // Update the typeProd
        TypeProd updatedTypeProd = typeProdRepository.findById(typeProd.getId()).get();
        // Disconnect from session so that the updates on updatedTypeProd are not directly saved in db
        em.detach(updatedTypeProd);
        updatedTypeProd
            .codeTypeProd(UPDATED_CODE_TYPE_PROD)
            .libelleTypeProd(UPDATED_LIBELLE_TYPE_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeProdMockMvc.perform(put("/api/type-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeProd)))
            .andExpect(status().isOk());

        // Validate the TypeProd in the database
        List<TypeProd> typeProdList = typeProdRepository.findAll();
        assertThat(typeProdList).hasSize(databaseSizeBeforeUpdate);
        TypeProd testTypeProd = typeProdList.get(typeProdList.size() - 1);
        assertThat(testTypeProd.getCodeTypeProd()).isEqualTo(UPDATED_CODE_TYPE_PROD);
        assertThat(testTypeProd.getLibelleTypeProd()).isEqualTo(UPDATED_LIBELLE_TYPE_PROD);
        assertThat(testTypeProd.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeProd.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeProd.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeProd.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeProd.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeProd() throws Exception {
        int databaseSizeBeforeUpdate = typeProdRepository.findAll().size();

        // Create the TypeProd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeProdMockMvc.perform(put("/api/type-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeProd)))
            .andExpect(status().isBadRequest());

        // Validate the TypeProd in the database
        List<TypeProd> typeProdList = typeProdRepository.findAll();
        assertThat(typeProdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeProd() throws Exception {
        // Initialize the database
        typeProdService.save(typeProd);

        int databaseSizeBeforeDelete = typeProdRepository.findAll().size();

        // Delete the typeProd
        restTypeProdMockMvc.perform(delete("/api/type-prods/{id}", typeProd.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeProd> typeProdList = typeProdRepository.findAll();
        assertThat(typeProdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
