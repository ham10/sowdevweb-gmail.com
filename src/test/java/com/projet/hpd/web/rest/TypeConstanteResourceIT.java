package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeConstante;
import com.projet.hpd.repository.TypeConstanteRepository;
import com.projet.hpd.service.TypeConstanteService;
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
 * Integration tests for the {@link TypeConstanteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeConstanteResourceIT {

    private static final String DEFAULT_CODE_TYPE_CONSTANTE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_CONSTANTE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_CONSTANTE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_CONSTANTE = "BBBBBBBBBB";

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
    private TypeConstanteRepository typeConstanteRepository;

    @Autowired
    private TypeConstanteService typeConstanteService;

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

    private MockMvc restTypeConstanteMockMvc;

    private TypeConstante typeConstante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeConstanteResource typeConstanteResource = new TypeConstanteResource(typeConstanteService);
        this.restTypeConstanteMockMvc = MockMvcBuilders.standaloneSetup(typeConstanteResource)
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
    public static TypeConstante createEntity(EntityManager em) {
        TypeConstante typeConstante = new TypeConstante()
            .codeTypeConstante(DEFAULT_CODE_TYPE_CONSTANTE)
            .libelleTypeConstante(DEFAULT_LIBELLE_TYPE_CONSTANTE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeConstante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeConstante createUpdatedEntity(EntityManager em) {
        TypeConstante typeConstante = new TypeConstante()
            .codeTypeConstante(UPDATED_CODE_TYPE_CONSTANTE)
            .libelleTypeConstante(UPDATED_LIBELLE_TYPE_CONSTANTE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeConstante;
    }

    @BeforeEach
    public void initTest() {
        typeConstante = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeConstante() throws Exception {
        int databaseSizeBeforeCreate = typeConstanteRepository.findAll().size();

        // Create the TypeConstante
        restTypeConstanteMockMvc.perform(post("/api/type-constantes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeConstante)))
            .andExpect(status().isCreated());

        // Validate the TypeConstante in the database
        List<TypeConstante> typeConstanteList = typeConstanteRepository.findAll();
        assertThat(typeConstanteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeConstante testTypeConstante = typeConstanteList.get(typeConstanteList.size() - 1);
        assertThat(testTypeConstante.getCodeTypeConstante()).isEqualTo(DEFAULT_CODE_TYPE_CONSTANTE);
        assertThat(testTypeConstante.getLibelleTypeConstante()).isEqualTo(DEFAULT_LIBELLE_TYPE_CONSTANTE);
        assertThat(testTypeConstante.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeConstante.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeConstante.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeConstante.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeConstante.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeConstanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeConstanteRepository.findAll().size();

        // Create the TypeConstante with an existing ID
        typeConstante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeConstanteMockMvc.perform(post("/api/type-constantes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeConstante)))
            .andExpect(status().isBadRequest());

        // Validate the TypeConstante in the database
        List<TypeConstante> typeConstanteList = typeConstanteRepository.findAll();
        assertThat(typeConstanteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeConstantes() throws Exception {
        // Initialize the database
        typeConstanteRepository.saveAndFlush(typeConstante);

        // Get all the typeConstanteList
        restTypeConstanteMockMvc.perform(get("/api/type-constantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeConstante.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeConstante").value(hasItem(DEFAULT_CODE_TYPE_CONSTANTE)))
            .andExpect(jsonPath("$.[*].libelleTypeConstante").value(hasItem(DEFAULT_LIBELLE_TYPE_CONSTANTE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeConstante() throws Exception {
        // Initialize the database
        typeConstanteRepository.saveAndFlush(typeConstante);

        // Get the typeConstante
        restTypeConstanteMockMvc.perform(get("/api/type-constantes/{id}", typeConstante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeConstante.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeConstante").value(DEFAULT_CODE_TYPE_CONSTANTE))
            .andExpect(jsonPath("$.libelleTypeConstante").value(DEFAULT_LIBELLE_TYPE_CONSTANTE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeConstante() throws Exception {
        // Get the typeConstante
        restTypeConstanteMockMvc.perform(get("/api/type-constantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeConstante() throws Exception {
        // Initialize the database
        typeConstanteService.save(typeConstante);

        int databaseSizeBeforeUpdate = typeConstanteRepository.findAll().size();

        // Update the typeConstante
        TypeConstante updatedTypeConstante = typeConstanteRepository.findById(typeConstante.getId()).get();
        // Disconnect from session so that the updates on updatedTypeConstante are not directly saved in db
        em.detach(updatedTypeConstante);
        updatedTypeConstante
            .codeTypeConstante(UPDATED_CODE_TYPE_CONSTANTE)
            .libelleTypeConstante(UPDATED_LIBELLE_TYPE_CONSTANTE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeConstanteMockMvc.perform(put("/api/type-constantes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeConstante)))
            .andExpect(status().isOk());

        // Validate the TypeConstante in the database
        List<TypeConstante> typeConstanteList = typeConstanteRepository.findAll();
        assertThat(typeConstanteList).hasSize(databaseSizeBeforeUpdate);
        TypeConstante testTypeConstante = typeConstanteList.get(typeConstanteList.size() - 1);
        assertThat(testTypeConstante.getCodeTypeConstante()).isEqualTo(UPDATED_CODE_TYPE_CONSTANTE);
        assertThat(testTypeConstante.getLibelleTypeConstante()).isEqualTo(UPDATED_LIBELLE_TYPE_CONSTANTE);
        assertThat(testTypeConstante.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeConstante.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeConstante.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeConstante.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeConstante.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeConstante() throws Exception {
        int databaseSizeBeforeUpdate = typeConstanteRepository.findAll().size();

        // Create the TypeConstante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeConstanteMockMvc.perform(put("/api/type-constantes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeConstante)))
            .andExpect(status().isBadRequest());

        // Validate the TypeConstante in the database
        List<TypeConstante> typeConstanteList = typeConstanteRepository.findAll();
        assertThat(typeConstanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeConstante() throws Exception {
        // Initialize the database
        typeConstanteService.save(typeConstante);

        int databaseSizeBeforeDelete = typeConstanteRepository.findAll().size();

        // Delete the typeConstante
        restTypeConstanteMockMvc.perform(delete("/api/type-constantes/{id}", typeConstante.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeConstante> typeConstanteList = typeConstanteRepository.findAll();
        assertThat(typeConstanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
