package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Lit;
import com.projet.hpd.repository.LitRepository;
import com.projet.hpd.service.LitService;
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
 * Integration tests for the {@link LitResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class LitResourceIT {

    private static final Integer DEFAULT_NUMERO_LIT = 1;
    private static final Integer UPDATED_NUMERO_LIT = 2;

    private static final String DEFAULT_DESCRIPTION_LIT = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_LIT = "BBBBBBBBBB";

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
    private LitRepository litRepository;

    @Autowired
    private LitService litService;

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

    private MockMvc restLitMockMvc;

    private Lit lit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LitResource litResource = new LitResource(litService);
        this.restLitMockMvc = MockMvcBuilders.standaloneSetup(litResource)
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
    public static Lit createEntity(EntityManager em) {
        Lit lit = new Lit()
            .numeroLit(DEFAULT_NUMERO_LIT)
            .descriptionLit(DEFAULT_DESCRIPTION_LIT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return lit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lit createUpdatedEntity(EntityManager em) {
        Lit lit = new Lit()
            .numeroLit(UPDATED_NUMERO_LIT)
            .descriptionLit(UPDATED_DESCRIPTION_LIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return lit;
    }

    @BeforeEach
    public void initTest() {
        lit = createEntity(em);
    }

    @Test
    @Transactional
    public void createLit() throws Exception {
        int databaseSizeBeforeCreate = litRepository.findAll().size();

        // Create the Lit
        restLitMockMvc.perform(post("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isCreated());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeCreate + 1);
        Lit testLit = litList.get(litList.size() - 1);
        assertThat(testLit.getNumeroLit()).isEqualTo(DEFAULT_NUMERO_LIT);
        assertThat(testLit.getDescriptionLit()).isEqualTo(DEFAULT_DESCRIPTION_LIT);
        assertThat(testLit.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testLit.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testLit.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testLit.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testLit.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createLitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = litRepository.findAll().size();

        // Create the Lit with an existing ID
        lit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLitMockMvc.perform(post("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isBadRequest());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLits() throws Exception {
        // Initialize the database
        litRepository.saveAndFlush(lit);

        // Get all the litList
        restLitMockMvc.perform(get("/api/lits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lit.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroLit").value(hasItem(DEFAULT_NUMERO_LIT)))
            .andExpect(jsonPath("$.[*].descriptionLit").value(hasItem(DEFAULT_DESCRIPTION_LIT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getLit() throws Exception {
        // Initialize the database
        litRepository.saveAndFlush(lit);

        // Get the lit
        restLitMockMvc.perform(get("/api/lits/{id}", lit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lit.getId().intValue()))
            .andExpect(jsonPath("$.numeroLit").value(DEFAULT_NUMERO_LIT))
            .andExpect(jsonPath("$.descriptionLit").value(DEFAULT_DESCRIPTION_LIT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLit() throws Exception {
        // Get the lit
        restLitMockMvc.perform(get("/api/lits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLit() throws Exception {
        // Initialize the database
        litService.save(lit);

        int databaseSizeBeforeUpdate = litRepository.findAll().size();

        // Update the lit
        Lit updatedLit = litRepository.findById(lit.getId()).get();
        // Disconnect from session so that the updates on updatedLit are not directly saved in db
        em.detach(updatedLit);
        updatedLit
            .numeroLit(UPDATED_NUMERO_LIT)
            .descriptionLit(UPDATED_DESCRIPTION_LIT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restLitMockMvc.perform(put("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLit)))
            .andExpect(status().isOk());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeUpdate);
        Lit testLit = litList.get(litList.size() - 1);
        assertThat(testLit.getNumeroLit()).isEqualTo(UPDATED_NUMERO_LIT);
        assertThat(testLit.getDescriptionLit()).isEqualTo(UPDATED_DESCRIPTION_LIT);
        assertThat(testLit.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testLit.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testLit.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testLit.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testLit.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLit() throws Exception {
        int databaseSizeBeforeUpdate = litRepository.findAll().size();

        // Create the Lit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLitMockMvc.perform(put("/api/lits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lit)))
            .andExpect(status().isBadRequest());

        // Validate the Lit in the database
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLit() throws Exception {
        // Initialize the database
        litService.save(lit);

        int databaseSizeBeforeDelete = litRepository.findAll().size();

        // Delete the lit
        restLitMockMvc.perform(delete("/api/lits/{id}", lit.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lit> litList = litRepository.findAll();
        assertThat(litList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
