package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Boxe;
import com.projet.hpd.repository.BoxeRepository;
import com.projet.hpd.service.BoxeService;
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
 * Integration tests for the {@link BoxeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class BoxeResourceIT {

    private static final Integer DEFAULT_NUMERO_BOXE = 1;
    private static final Integer UPDATED_NUMERO_BOXE = 2;

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
    private BoxeRepository boxeRepository;

    @Autowired
    private BoxeService boxeService;

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

    private MockMvc restBoxeMockMvc;

    private Boxe boxe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoxeResource boxeResource = new BoxeResource(boxeService);
        this.restBoxeMockMvc = MockMvcBuilders.standaloneSetup(boxeResource)
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
    public static Boxe createEntity(EntityManager em) {
        Boxe boxe = new Boxe()
            .numeroBoxe(DEFAULT_NUMERO_BOXE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return boxe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boxe createUpdatedEntity(EntityManager em) {
        Boxe boxe = new Boxe()
            .numeroBoxe(UPDATED_NUMERO_BOXE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return boxe;
    }

    @BeforeEach
    public void initTest() {
        boxe = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxe() throws Exception {
        int databaseSizeBeforeCreate = boxeRepository.findAll().size();

        // Create the Boxe
        restBoxeMockMvc.perform(post("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxe)))
            .andExpect(status().isCreated());

        // Validate the Boxe in the database
        List<Boxe> boxeList = boxeRepository.findAll();
        assertThat(boxeList).hasSize(databaseSizeBeforeCreate + 1);
        Boxe testBoxe = boxeList.get(boxeList.size() - 1);
        assertThat(testBoxe.getNumeroBoxe()).isEqualTo(DEFAULT_NUMERO_BOXE);
        assertThat(testBoxe.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBoxe.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testBoxe.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testBoxe.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testBoxe.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createBoxeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxeRepository.findAll().size();

        // Create the Boxe with an existing ID
        boxe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxeMockMvc.perform(post("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxe)))
            .andExpect(status().isBadRequest());

        // Validate the Boxe in the database
        List<Boxe> boxeList = boxeRepository.findAll();
        assertThat(boxeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBoxes() throws Exception {
        // Initialize the database
        boxeRepository.saveAndFlush(boxe);

        // Get all the boxeList
        restBoxeMockMvc.perform(get("/api/boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxe.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroBoxe").value(hasItem(DEFAULT_NUMERO_BOXE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBoxe() throws Exception {
        // Initialize the database
        boxeRepository.saveAndFlush(boxe);

        // Get the boxe
        restBoxeMockMvc.perform(get("/api/boxes/{id}", boxe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boxe.getId().intValue()))
            .andExpect(jsonPath("$.numeroBoxe").value(DEFAULT_NUMERO_BOXE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBoxe() throws Exception {
        // Get the boxe
        restBoxeMockMvc.perform(get("/api/boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxe() throws Exception {
        // Initialize the database
        boxeService.save(boxe);

        int databaseSizeBeforeUpdate = boxeRepository.findAll().size();

        // Update the boxe
        Boxe updatedBoxe = boxeRepository.findById(boxe.getId()).get();
        // Disconnect from session so that the updates on updatedBoxe are not directly saved in db
        em.detach(updatedBoxe);
        updatedBoxe
            .numeroBoxe(UPDATED_NUMERO_BOXE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restBoxeMockMvc.perform(put("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxe)))
            .andExpect(status().isOk());

        // Validate the Boxe in the database
        List<Boxe> boxeList = boxeRepository.findAll();
        assertThat(boxeList).hasSize(databaseSizeBeforeUpdate);
        Boxe testBoxe = boxeList.get(boxeList.size() - 1);
        assertThat(testBoxe.getNumeroBoxe()).isEqualTo(UPDATED_NUMERO_BOXE);
        assertThat(testBoxe.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBoxe.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testBoxe.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testBoxe.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testBoxe.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxe() throws Exception {
        int databaseSizeBeforeUpdate = boxeRepository.findAll().size();

        // Create the Boxe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoxeMockMvc.perform(put("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxe)))
            .andExpect(status().isBadRequest());

        // Validate the Boxe in the database
        List<Boxe> boxeList = boxeRepository.findAll();
        assertThat(boxeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoxe() throws Exception {
        // Initialize the database
        boxeService.save(boxe);

        int databaseSizeBeforeDelete = boxeRepository.findAll().size();

        // Delete the boxe
        restBoxeMockMvc.perform(delete("/api/boxes/{id}", boxe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Boxe> boxeList = boxeRepository.findAll();
        assertThat(boxeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
