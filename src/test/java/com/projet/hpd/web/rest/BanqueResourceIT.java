package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Banque;
import com.projet.hpd.repository.BanqueRepository;
import com.projet.hpd.service.BanqueService;
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
 * Integration tests for the {@link BanqueResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class BanqueResourceIT {

    private static final String DEFAULT_CODE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_RIB_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_RIB_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_BANQUE = "BBBBBBBBBB";

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
    private BanqueRepository banqueRepository;

    @Autowired
    private BanqueService banqueService;

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

    private MockMvc restBanqueMockMvc;

    private Banque banque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanqueResource banqueResource = new BanqueResource(banqueService);
        this.restBanqueMockMvc = MockMvcBuilders.standaloneSetup(banqueResource)
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
    public static Banque createEntity(EntityManager em) {
        Banque banque = new Banque()
            .codeBanque(DEFAULT_CODE_BANQUE)
            .ribBanque(DEFAULT_RIB_BANQUE)
            .libelleBanque(DEFAULT_LIBELLE_BANQUE)
            .descriptionBanque(DEFAULT_DESCRIPTION_BANQUE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return banque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banque createUpdatedEntity(EntityManager em) {
        Banque banque = new Banque()
            .codeBanque(UPDATED_CODE_BANQUE)
            .ribBanque(UPDATED_RIB_BANQUE)
            .libelleBanque(UPDATED_LIBELLE_BANQUE)
            .descriptionBanque(UPDATED_DESCRIPTION_BANQUE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return banque;
    }

    @BeforeEach
    public void initTest() {
        banque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanque() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate + 1);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getCodeBanque()).isEqualTo(DEFAULT_CODE_BANQUE);
        assertThat(testBanque.getRibBanque()).isEqualTo(DEFAULT_RIB_BANQUE);
        assertThat(testBanque.getLibelleBanque()).isEqualTo(DEFAULT_LIBELLE_BANQUE);
        assertThat(testBanque.getDescriptionBanque()).isEqualTo(DEFAULT_DESCRIPTION_BANQUE);
        assertThat(testBanque.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBanque.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testBanque.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testBanque.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testBanque.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createBanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque with an existing ID
        banque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBanques() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeBanque").value(hasItem(DEFAULT_CODE_BANQUE)))
            .andExpect(jsonPath("$.[*].ribBanque").value(hasItem(DEFAULT_RIB_BANQUE)))
            .andExpect(jsonPath("$.[*].libelleBanque").value(hasItem(DEFAULT_LIBELLE_BANQUE)))
            .andExpect(jsonPath("$.[*].descriptionBanque").value(hasItem(DEFAULT_DESCRIPTION_BANQUE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banque.getId().intValue()))
            .andExpect(jsonPath("$.codeBanque").value(DEFAULT_CODE_BANQUE))
            .andExpect(jsonPath("$.ribBanque").value(DEFAULT_RIB_BANQUE))
            .andExpect(jsonPath("$.libelleBanque").value(DEFAULT_LIBELLE_BANQUE))
            .andExpect(jsonPath("$.descriptionBanque").value(DEFAULT_DESCRIPTION_BANQUE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanque() throws Exception {
        // Initialize the database
        banqueService.save(banque);

        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Update the banque
        Banque updatedBanque = banqueRepository.findById(banque.getId()).get();
        // Disconnect from session so that the updates on updatedBanque are not directly saved in db
        em.detach(updatedBanque);
        updatedBanque
            .codeBanque(UPDATED_CODE_BANQUE)
            .ribBanque(UPDATED_RIB_BANQUE)
            .libelleBanque(UPDATED_LIBELLE_BANQUE)
            .descriptionBanque(UPDATED_DESCRIPTION_BANQUE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanque)))
            .andExpect(status().isOk());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getCodeBanque()).isEqualTo(UPDATED_CODE_BANQUE);
        assertThat(testBanque.getRibBanque()).isEqualTo(UPDATED_RIB_BANQUE);
        assertThat(testBanque.getLibelleBanque()).isEqualTo(UPDATED_LIBELLE_BANQUE);
        assertThat(testBanque.getDescriptionBanque()).isEqualTo(UPDATED_DESCRIPTION_BANQUE);
        assertThat(testBanque.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBanque.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testBanque.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testBanque.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testBanque.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBanque() throws Exception {
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Create the Banque

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBanque() throws Exception {
        // Initialize the database
        banqueService.save(banque);

        int databaseSizeBeforeDelete = banqueRepository.findAll().size();

        // Delete the banque
        restBanqueMockMvc.perform(delete("/api/banques/{id}", banque.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
