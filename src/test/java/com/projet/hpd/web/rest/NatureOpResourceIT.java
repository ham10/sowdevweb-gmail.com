package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.NatureOp;
import com.projet.hpd.repository.NatureOpRepository;
import com.projet.hpd.service.NatureOpService;
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
 * Integration tests for the {@link NatureOpResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class NatureOpResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

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
    private NatureOpRepository natureOpRepository;

    @Autowired
    private NatureOpService natureOpService;

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

    private MockMvc restNatureOpMockMvc;

    private NatureOp natureOp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NatureOpResource natureOpResource = new NatureOpResource(natureOpService);
        this.restNatureOpMockMvc = MockMvcBuilders.standaloneSetup(natureOpResource)
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
    public static NatureOp createEntity(EntityManager em) {
        NatureOp natureOp = new NatureOp()
            .numero(DEFAULT_NUMERO)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return natureOp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureOp createUpdatedEntity(EntityManager em) {
        NatureOp natureOp = new NatureOp()
            .numero(UPDATED_NUMERO)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return natureOp;
    }

    @BeforeEach
    public void initTest() {
        natureOp = createEntity(em);
    }

    @Test
    @Transactional
    public void createNatureOp() throws Exception {
        int databaseSizeBeforeCreate = natureOpRepository.findAll().size();

        // Create the NatureOp
        restNatureOpMockMvc.perform(post("/api/nature-ops")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureOp)))
            .andExpect(status().isCreated());

        // Validate the NatureOp in the database
        List<NatureOp> natureOpList = natureOpRepository.findAll();
        assertThat(natureOpList).hasSize(databaseSizeBeforeCreate + 1);
        NatureOp testNatureOp = natureOpList.get(natureOpList.size() - 1);
        assertThat(testNatureOp.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testNatureOp.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNatureOp.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testNatureOp.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testNatureOp.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testNatureOp.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testNatureOp.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testNatureOp.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createNatureOpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = natureOpRepository.findAll().size();

        // Create the NatureOp with an existing ID
        natureOp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureOpMockMvc.perform(post("/api/nature-ops")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureOp)))
            .andExpect(status().isBadRequest());

        // Validate the NatureOp in the database
        List<NatureOp> natureOpList = natureOpRepository.findAll();
        assertThat(natureOpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNatureOps() throws Exception {
        // Initialize the database
        natureOpRepository.saveAndFlush(natureOp);

        // Get all the natureOpList
        restNatureOpMockMvc.perform(get("/api/nature-ops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureOp.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getNatureOp() throws Exception {
        // Initialize the database
        natureOpRepository.saveAndFlush(natureOp);

        // Get the natureOp
        restNatureOpMockMvc.perform(get("/api/nature-ops/{id}", natureOp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureOp.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNatureOp() throws Exception {
        // Get the natureOp
        restNatureOpMockMvc.perform(get("/api/nature-ops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNatureOp() throws Exception {
        // Initialize the database
        natureOpService.save(natureOp);

        int databaseSizeBeforeUpdate = natureOpRepository.findAll().size();

        // Update the natureOp
        NatureOp updatedNatureOp = natureOpRepository.findById(natureOp.getId()).get();
        // Disconnect from session so that the updates on updatedNatureOp are not directly saved in db
        em.detach(updatedNatureOp);
        updatedNatureOp
            .numero(UPDATED_NUMERO)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restNatureOpMockMvc.perform(put("/api/nature-ops")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNatureOp)))
            .andExpect(status().isOk());

        // Validate the NatureOp in the database
        List<NatureOp> natureOpList = natureOpRepository.findAll();
        assertThat(natureOpList).hasSize(databaseSizeBeforeUpdate);
        NatureOp testNatureOp = natureOpList.get(natureOpList.size() - 1);
        assertThat(testNatureOp.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testNatureOp.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNatureOp.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testNatureOp.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testNatureOp.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testNatureOp.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testNatureOp.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testNatureOp.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingNatureOp() throws Exception {
        int databaseSizeBeforeUpdate = natureOpRepository.findAll().size();

        // Create the NatureOp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureOpMockMvc.perform(put("/api/nature-ops")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureOp)))
            .andExpect(status().isBadRequest());

        // Validate the NatureOp in the database
        List<NatureOp> natureOpList = natureOpRepository.findAll();
        assertThat(natureOpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNatureOp() throws Exception {
        // Initialize the database
        natureOpService.save(natureOp);

        int databaseSizeBeforeDelete = natureOpRepository.findAll().size();

        // Delete the natureOp
        restNatureOpMockMvc.perform(delete("/api/nature-ops/{id}", natureOp.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureOp> natureOpList = natureOpRepository.findAll();
        assertThat(natureOpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
