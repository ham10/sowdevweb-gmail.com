package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeCond;
import com.projet.hpd.repository.TypeCondRepository;
import com.projet.hpd.service.TypeCondService;
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
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TypeCondResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeCondResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeCondRepository typeCondRepository;

    @Autowired
    private TypeCondService typeCondService;

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

    private MockMvc restTypeCondMockMvc;

    private TypeCond typeCond;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCondResource typeCondResource = new TypeCondResource(typeCondService);
        this.restTypeCondMockMvc = MockMvcBuilders.standaloneSetup(typeCondResource)
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
    public static TypeCond createEntity(EntityManager em) {
        TypeCond typeCond = new TypeCond()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return typeCond;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeCond createUpdatedEntity(EntityManager em) {
        TypeCond typeCond = new TypeCond()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return typeCond;
    }

    @BeforeEach
    public void initTest() {
        typeCond = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCond() throws Exception {
        int databaseSizeBeforeCreate = typeCondRepository.findAll().size();

        // Create the TypeCond
        restTypeCondMockMvc.perform(post("/api/type-conds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCond)))
            .andExpect(status().isCreated());

        // Validate the TypeCond in the database
        List<TypeCond> typeCondList = typeCondRepository.findAll();
        assertThat(typeCondList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCond testTypeCond = typeCondList.get(typeCondList.size() - 1);
        assertThat(testTypeCond.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCond.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeCondWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCondRepository.findAll().size();

        // Create the TypeCond with an existing ID
        typeCond.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCondMockMvc.perform(post("/api/type-conds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCond)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCond in the database
        List<TypeCond> typeCondList = typeCondRepository.findAll();
        assertThat(typeCondList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeConds() throws Exception {
        // Initialize the database
        typeCondRepository.saveAndFlush(typeCond);

        // Get all the typeCondList
        restTypeCondMockMvc.perform(get("/api/type-conds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCond.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getTypeCond() throws Exception {
        // Initialize the database
        typeCondRepository.saveAndFlush(typeCond);

        // Get the typeCond
        restTypeCondMockMvc.perform(get("/api/type-conds/{id}", typeCond.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeCond.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCond() throws Exception {
        // Get the typeCond
        restTypeCondMockMvc.perform(get("/api/type-conds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCond() throws Exception {
        // Initialize the database
        typeCondService.save(typeCond);

        int databaseSizeBeforeUpdate = typeCondRepository.findAll().size();

        // Update the typeCond
        TypeCond updatedTypeCond = typeCondRepository.findById(typeCond.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCond are not directly saved in db
        em.detach(updatedTypeCond);
        updatedTypeCond
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restTypeCondMockMvc.perform(put("/api/type-conds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeCond)))
            .andExpect(status().isOk());

        // Validate the TypeCond in the database
        List<TypeCond> typeCondList = typeCondRepository.findAll();
        assertThat(typeCondList).hasSize(databaseSizeBeforeUpdate);
        TypeCond testTypeCond = typeCondList.get(typeCondList.size() - 1);
        assertThat(testTypeCond.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCond.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCond() throws Exception {
        int databaseSizeBeforeUpdate = typeCondRepository.findAll().size();

        // Create the TypeCond

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCondMockMvc.perform(put("/api/type-conds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeCond)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCond in the database
        List<TypeCond> typeCondList = typeCondRepository.findAll();
        assertThat(typeCondList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCond() throws Exception {
        // Initialize the database
        typeCondService.save(typeCond);

        int databaseSizeBeforeDelete = typeCondRepository.findAll().size();

        // Delete the typeCond
        restTypeCondMockMvc.perform(delete("/api/type-conds/{id}", typeCond.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeCond> typeCondList = typeCondRepository.findAll();
        assertThat(typeCondList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
