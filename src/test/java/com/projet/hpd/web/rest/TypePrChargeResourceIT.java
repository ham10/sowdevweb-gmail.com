package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePrCharge;
import com.projet.hpd.repository.TypePrChargeRepository;
import com.projet.hpd.service.TypePrChargeService;
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
 * Integration tests for the {@link TypePrChargeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePrChargeResourceIT {

    private static final String DEFAULT_CODE_TYPE_PR_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PR_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PR_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PR_CHARGE = "BBBBBBBBBB";

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
    private TypePrChargeRepository typePrChargeRepository;

    @Autowired
    private TypePrChargeService typePrChargeService;

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

    private MockMvc restTypePrChargeMockMvc;

    private TypePrCharge typePrCharge;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePrChargeResource typePrChargeResource = new TypePrChargeResource(typePrChargeService);
        this.restTypePrChargeMockMvc = MockMvcBuilders.standaloneSetup(typePrChargeResource)
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
    public static TypePrCharge createEntity(EntityManager em) {
        TypePrCharge typePrCharge = new TypePrCharge()
            .codeTypePrCharge(DEFAULT_CODE_TYPE_PR_CHARGE)
            .libelleTypePrCharge(DEFAULT_LIBELLE_TYPE_PR_CHARGE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePrCharge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePrCharge createUpdatedEntity(EntityManager em) {
        TypePrCharge typePrCharge = new TypePrCharge()
            .codeTypePrCharge(UPDATED_CODE_TYPE_PR_CHARGE)
            .libelleTypePrCharge(UPDATED_LIBELLE_TYPE_PR_CHARGE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePrCharge;
    }

    @BeforeEach
    public void initTest() {
        typePrCharge = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePrCharge() throws Exception {
        int databaseSizeBeforeCreate = typePrChargeRepository.findAll().size();

        // Create the TypePrCharge
        restTypePrChargeMockMvc.perform(post("/api/type-pr-charges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePrCharge)))
            .andExpect(status().isCreated());

        // Validate the TypePrCharge in the database
        List<TypePrCharge> typePrChargeList = typePrChargeRepository.findAll();
        assertThat(typePrChargeList).hasSize(databaseSizeBeforeCreate + 1);
        TypePrCharge testTypePrCharge = typePrChargeList.get(typePrChargeList.size() - 1);
        assertThat(testTypePrCharge.getCodeTypePrCharge()).isEqualTo(DEFAULT_CODE_TYPE_PR_CHARGE);
        assertThat(testTypePrCharge.getLibelleTypePrCharge()).isEqualTo(DEFAULT_LIBELLE_TYPE_PR_CHARGE);
        assertThat(testTypePrCharge.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePrCharge.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePrCharge.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePrCharge.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePrCharge.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePrChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePrChargeRepository.findAll().size();

        // Create the TypePrCharge with an existing ID
        typePrCharge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePrChargeMockMvc.perform(post("/api/type-pr-charges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePrCharge)))
            .andExpect(status().isBadRequest());

        // Validate the TypePrCharge in the database
        List<TypePrCharge> typePrChargeList = typePrChargeRepository.findAll();
        assertThat(typePrChargeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePrCharges() throws Exception {
        // Initialize the database
        typePrChargeRepository.saveAndFlush(typePrCharge);

        // Get all the typePrChargeList
        restTypePrChargeMockMvc.perform(get("/api/type-pr-charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePrCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePrCharge").value(hasItem(DEFAULT_CODE_TYPE_PR_CHARGE)))
            .andExpect(jsonPath("$.[*].libelleTypePrCharge").value(hasItem(DEFAULT_LIBELLE_TYPE_PR_CHARGE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePrCharge() throws Exception {
        // Initialize the database
        typePrChargeRepository.saveAndFlush(typePrCharge);

        // Get the typePrCharge
        restTypePrChargeMockMvc.perform(get("/api/type-pr-charges/{id}", typePrCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePrCharge.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePrCharge").value(DEFAULT_CODE_TYPE_PR_CHARGE))
            .andExpect(jsonPath("$.libelleTypePrCharge").value(DEFAULT_LIBELLE_TYPE_PR_CHARGE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePrCharge() throws Exception {
        // Get the typePrCharge
        restTypePrChargeMockMvc.perform(get("/api/type-pr-charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePrCharge() throws Exception {
        // Initialize the database
        typePrChargeService.save(typePrCharge);

        int databaseSizeBeforeUpdate = typePrChargeRepository.findAll().size();

        // Update the typePrCharge
        TypePrCharge updatedTypePrCharge = typePrChargeRepository.findById(typePrCharge.getId()).get();
        // Disconnect from session so that the updates on updatedTypePrCharge are not directly saved in db
        em.detach(updatedTypePrCharge);
        updatedTypePrCharge
            .codeTypePrCharge(UPDATED_CODE_TYPE_PR_CHARGE)
            .libelleTypePrCharge(UPDATED_LIBELLE_TYPE_PR_CHARGE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePrChargeMockMvc.perform(put("/api/type-pr-charges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePrCharge)))
            .andExpect(status().isOk());

        // Validate the TypePrCharge in the database
        List<TypePrCharge> typePrChargeList = typePrChargeRepository.findAll();
        assertThat(typePrChargeList).hasSize(databaseSizeBeforeUpdate);
        TypePrCharge testTypePrCharge = typePrChargeList.get(typePrChargeList.size() - 1);
        assertThat(testTypePrCharge.getCodeTypePrCharge()).isEqualTo(UPDATED_CODE_TYPE_PR_CHARGE);
        assertThat(testTypePrCharge.getLibelleTypePrCharge()).isEqualTo(UPDATED_LIBELLE_TYPE_PR_CHARGE);
        assertThat(testTypePrCharge.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePrCharge.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePrCharge.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePrCharge.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePrCharge.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePrCharge() throws Exception {
        int databaseSizeBeforeUpdate = typePrChargeRepository.findAll().size();

        // Create the TypePrCharge

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePrChargeMockMvc.perform(put("/api/type-pr-charges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePrCharge)))
            .andExpect(status().isBadRequest());

        // Validate the TypePrCharge in the database
        List<TypePrCharge> typePrChargeList = typePrChargeRepository.findAll();
        assertThat(typePrChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePrCharge() throws Exception {
        // Initialize the database
        typePrChargeService.save(typePrCharge);

        int databaseSizeBeforeDelete = typePrChargeRepository.findAll().size();

        // Delete the typePrCharge
        restTypePrChargeMockMvc.perform(delete("/api/type-pr-charges/{id}", typePrCharge.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePrCharge> typePrChargeList = typePrChargeRepository.findAll();
        assertThat(typePrChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
