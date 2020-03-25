package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeMvtStock;
import com.projet.hpd.repository.TypeMvtStockRepository;
import com.projet.hpd.service.TypeMvtStockService;
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
 * Integration tests for the {@link TypeMvtStockResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeMvtStockResourceIT {

    private static final String DEFAULT_CODE_TYPE_MVT_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_MVT_STOCK = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_MVT_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_MVT_STOCK = "BBBBBBBBBB";

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
    private TypeMvtStockRepository typeMvtStockRepository;

    @Autowired
    private TypeMvtStockService typeMvtStockService;

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

    private MockMvc restTypeMvtStockMockMvc;

    private TypeMvtStock typeMvtStock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeMvtStockResource typeMvtStockResource = new TypeMvtStockResource(typeMvtStockService);
        this.restTypeMvtStockMockMvc = MockMvcBuilders.standaloneSetup(typeMvtStockResource)
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
    public static TypeMvtStock createEntity(EntityManager em) {
        TypeMvtStock typeMvtStock = new TypeMvtStock()
            .codeTypeMvtStock(DEFAULT_CODE_TYPE_MVT_STOCK)
            .libelleTypeMvtStock(DEFAULT_LIBELLE_TYPE_MVT_STOCK)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeMvtStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeMvtStock createUpdatedEntity(EntityManager em) {
        TypeMvtStock typeMvtStock = new TypeMvtStock()
            .codeTypeMvtStock(UPDATED_CODE_TYPE_MVT_STOCK)
            .libelleTypeMvtStock(UPDATED_LIBELLE_TYPE_MVT_STOCK)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeMvtStock;
    }

    @BeforeEach
    public void initTest() {
        typeMvtStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeMvtStock() throws Exception {
        int databaseSizeBeforeCreate = typeMvtStockRepository.findAll().size();

        // Create the TypeMvtStock
        restTypeMvtStockMockMvc.perform(post("/api/type-mvt-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMvtStock)))
            .andExpect(status().isCreated());

        // Validate the TypeMvtStock in the database
        List<TypeMvtStock> typeMvtStockList = typeMvtStockRepository.findAll();
        assertThat(typeMvtStockList).hasSize(databaseSizeBeforeCreate + 1);
        TypeMvtStock testTypeMvtStock = typeMvtStockList.get(typeMvtStockList.size() - 1);
        assertThat(testTypeMvtStock.getCodeTypeMvtStock()).isEqualTo(DEFAULT_CODE_TYPE_MVT_STOCK);
        assertThat(testTypeMvtStock.getLibelleTypeMvtStock()).isEqualTo(DEFAULT_LIBELLE_TYPE_MVT_STOCK);
        assertThat(testTypeMvtStock.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeMvtStock.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeMvtStock.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeMvtStock.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeMvtStock.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeMvtStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeMvtStockRepository.findAll().size();

        // Create the TypeMvtStock with an existing ID
        typeMvtStock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMvtStockMockMvc.perform(post("/api/type-mvt-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMvtStock)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMvtStock in the database
        List<TypeMvtStock> typeMvtStockList = typeMvtStockRepository.findAll();
        assertThat(typeMvtStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeMvtStocks() throws Exception {
        // Initialize the database
        typeMvtStockRepository.saveAndFlush(typeMvtStock);

        // Get all the typeMvtStockList
        restTypeMvtStockMockMvc.perform(get("/api/type-mvt-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMvtStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeMvtStock").value(hasItem(DEFAULT_CODE_TYPE_MVT_STOCK)))
            .andExpect(jsonPath("$.[*].libelleTypeMvtStock").value(hasItem(DEFAULT_LIBELLE_TYPE_MVT_STOCK)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeMvtStock() throws Exception {
        // Initialize the database
        typeMvtStockRepository.saveAndFlush(typeMvtStock);

        // Get the typeMvtStock
        restTypeMvtStockMockMvc.perform(get("/api/type-mvt-stocks/{id}", typeMvtStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeMvtStock.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeMvtStock").value(DEFAULT_CODE_TYPE_MVT_STOCK))
            .andExpect(jsonPath("$.libelleTypeMvtStock").value(DEFAULT_LIBELLE_TYPE_MVT_STOCK))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeMvtStock() throws Exception {
        // Get the typeMvtStock
        restTypeMvtStockMockMvc.perform(get("/api/type-mvt-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeMvtStock() throws Exception {
        // Initialize the database
        typeMvtStockService.save(typeMvtStock);

        int databaseSizeBeforeUpdate = typeMvtStockRepository.findAll().size();

        // Update the typeMvtStock
        TypeMvtStock updatedTypeMvtStock = typeMvtStockRepository.findById(typeMvtStock.getId()).get();
        // Disconnect from session so that the updates on updatedTypeMvtStock are not directly saved in db
        em.detach(updatedTypeMvtStock);
        updatedTypeMvtStock
            .codeTypeMvtStock(UPDATED_CODE_TYPE_MVT_STOCK)
            .libelleTypeMvtStock(UPDATED_LIBELLE_TYPE_MVT_STOCK)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeMvtStockMockMvc.perform(put("/api/type-mvt-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeMvtStock)))
            .andExpect(status().isOk());

        // Validate the TypeMvtStock in the database
        List<TypeMvtStock> typeMvtStockList = typeMvtStockRepository.findAll();
        assertThat(typeMvtStockList).hasSize(databaseSizeBeforeUpdate);
        TypeMvtStock testTypeMvtStock = typeMvtStockList.get(typeMvtStockList.size() - 1);
        assertThat(testTypeMvtStock.getCodeTypeMvtStock()).isEqualTo(UPDATED_CODE_TYPE_MVT_STOCK);
        assertThat(testTypeMvtStock.getLibelleTypeMvtStock()).isEqualTo(UPDATED_LIBELLE_TYPE_MVT_STOCK);
        assertThat(testTypeMvtStock.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeMvtStock.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeMvtStock.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeMvtStock.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeMvtStock.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeMvtStock() throws Exception {
        int databaseSizeBeforeUpdate = typeMvtStockRepository.findAll().size();

        // Create the TypeMvtStock

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeMvtStockMockMvc.perform(put("/api/type-mvt-stocks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMvtStock)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMvtStock in the database
        List<TypeMvtStock> typeMvtStockList = typeMvtStockRepository.findAll();
        assertThat(typeMvtStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeMvtStock() throws Exception {
        // Initialize the database
        typeMvtStockService.save(typeMvtStock);

        int databaseSizeBeforeDelete = typeMvtStockRepository.findAll().size();

        // Delete the typeMvtStock
        restTypeMvtStockMockMvc.perform(delete("/api/type-mvt-stocks/{id}", typeMvtStock.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeMvtStock> typeMvtStockList = typeMvtStockRepository.findAll();
        assertThat(typeMvtStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
