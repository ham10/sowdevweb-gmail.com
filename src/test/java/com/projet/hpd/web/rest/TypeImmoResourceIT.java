package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeImmo;
import com.projet.hpd.repository.TypeImmoRepository;
import com.projet.hpd.service.TypeImmoService;
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
 * Integration tests for the {@link TypeImmoResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeImmoResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MODE_CALCUL = "AAAAAAAAAA";
    private static final String UPDATED_MODE_CALCUL = "BBBBBBBBBB";

    private static final Double DEFAULT_TAUX = 1D;
    private static final Double UPDATED_TAUX = 2D;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private TypeImmoRepository typeImmoRepository;

    @Autowired
    private TypeImmoService typeImmoService;

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

    private MockMvc restTypeImmoMockMvc;

    private TypeImmo typeImmo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeImmoResource typeImmoResource = new TypeImmoResource(typeImmoService);
        this.restTypeImmoMockMvc = MockMvcBuilders.standaloneSetup(typeImmoResource)
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
    public static TypeImmo createEntity(EntityManager em) {
        TypeImmo typeImmo = new TypeImmo()
            .code(DEFAULT_CODE)
            .modeCalcul(DEFAULT_MODE_CALCUL)
            .taux(DEFAULT_TAUX)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeImmo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeImmo createUpdatedEntity(EntityManager em) {
        TypeImmo typeImmo = new TypeImmo()
            .code(UPDATED_CODE)
            .modeCalcul(UPDATED_MODE_CALCUL)
            .taux(UPDATED_TAUX)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeImmo;
    }

    @BeforeEach
    public void initTest() {
        typeImmo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeImmo() throws Exception {
        int databaseSizeBeforeCreate = typeImmoRepository.findAll().size();

        // Create the TypeImmo
        restTypeImmoMockMvc.perform(post("/api/type-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeImmo)))
            .andExpect(status().isCreated());

        // Validate the TypeImmo in the database
        List<TypeImmo> typeImmoList = typeImmoRepository.findAll();
        assertThat(typeImmoList).hasSize(databaseSizeBeforeCreate + 1);
        TypeImmo testTypeImmo = typeImmoList.get(typeImmoList.size() - 1);
        assertThat(testTypeImmo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeImmo.getModeCalcul()).isEqualTo(DEFAULT_MODE_CALCUL);
        assertThat(testTypeImmo.getTaux()).isEqualTo(DEFAULT_TAUX);
        assertThat(testTypeImmo.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeImmo.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeImmo.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testTypeImmo.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeImmo.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeImmo.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeImmoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeImmoRepository.findAll().size();

        // Create the TypeImmo with an existing ID
        typeImmo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeImmoMockMvc.perform(post("/api/type-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeImmo)))
            .andExpect(status().isBadRequest());

        // Validate the TypeImmo in the database
        List<TypeImmo> typeImmoList = typeImmoRepository.findAll();
        assertThat(typeImmoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeImmos() throws Exception {
        // Initialize the database
        typeImmoRepository.saveAndFlush(typeImmo);

        // Get all the typeImmoList
        restTypeImmoMockMvc.perform(get("/api/type-immos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeImmo.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].modeCalcul").value(hasItem(DEFAULT_MODE_CALCUL)))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeImmo() throws Exception {
        // Initialize the database
        typeImmoRepository.saveAndFlush(typeImmo);

        // Get the typeImmo
        restTypeImmoMockMvc.perform(get("/api/type-immos/{id}", typeImmo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeImmo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.modeCalcul").value(DEFAULT_MODE_CALCUL))
            .andExpect(jsonPath("$.taux").value(DEFAULT_TAUX.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeImmo() throws Exception {
        // Get the typeImmo
        restTypeImmoMockMvc.perform(get("/api/type-immos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeImmo() throws Exception {
        // Initialize the database
        typeImmoService.save(typeImmo);

        int databaseSizeBeforeUpdate = typeImmoRepository.findAll().size();

        // Update the typeImmo
        TypeImmo updatedTypeImmo = typeImmoRepository.findById(typeImmo.getId()).get();
        // Disconnect from session so that the updates on updatedTypeImmo are not directly saved in db
        em.detach(updatedTypeImmo);
        updatedTypeImmo
            .code(UPDATED_CODE)
            .modeCalcul(UPDATED_MODE_CALCUL)
            .taux(UPDATED_TAUX)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeImmoMockMvc.perform(put("/api/type-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeImmo)))
            .andExpect(status().isOk());

        // Validate the TypeImmo in the database
        List<TypeImmo> typeImmoList = typeImmoRepository.findAll();
        assertThat(typeImmoList).hasSize(databaseSizeBeforeUpdate);
        TypeImmo testTypeImmo = typeImmoList.get(typeImmoList.size() - 1);
        assertThat(testTypeImmo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeImmo.getModeCalcul()).isEqualTo(UPDATED_MODE_CALCUL);
        assertThat(testTypeImmo.getTaux()).isEqualTo(UPDATED_TAUX);
        assertThat(testTypeImmo.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeImmo.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeImmo.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testTypeImmo.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeImmo.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeImmo.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeImmo() throws Exception {
        int databaseSizeBeforeUpdate = typeImmoRepository.findAll().size();

        // Create the TypeImmo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeImmoMockMvc.perform(put("/api/type-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeImmo)))
            .andExpect(status().isBadRequest());

        // Validate the TypeImmo in the database
        List<TypeImmo> typeImmoList = typeImmoRepository.findAll();
        assertThat(typeImmoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeImmo() throws Exception {
        // Initialize the database
        typeImmoService.save(typeImmo);

        int databaseSizeBeforeDelete = typeImmoRepository.findAll().size();

        // Delete the typeImmo
        restTypeImmoMockMvc.perform(delete("/api/type-immos/{id}", typeImmo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeImmo> typeImmoList = typeImmoRepository.findAll();
        assertThat(typeImmoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
