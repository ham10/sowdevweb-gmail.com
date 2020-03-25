package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.MachAutorise;
import com.projet.hpd.repository.MachAutoriseRepository;
import com.projet.hpd.service.MachAutoriseService;
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
 * Integration tests for the {@link MachAutoriseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class MachAutoriseResourceIT {

    private static final String DEFAULT_NUMERO_MACH_AUTORISE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_MACH_AUTORISE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_MAC_MACH_AUTORISE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_MAC_MACH_AUTORISE = "BBBBBBBBBB";

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
    private MachAutoriseRepository machAutoriseRepository;

    @Autowired
    private MachAutoriseService machAutoriseService;

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

    private MockMvc restMachAutoriseMockMvc;

    private MachAutorise machAutorise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MachAutoriseResource machAutoriseResource = new MachAutoriseResource(machAutoriseService);
        this.restMachAutoriseMockMvc = MockMvcBuilders.standaloneSetup(machAutoriseResource)
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
    public static MachAutorise createEntity(EntityManager em) {
        MachAutorise machAutorise = new MachAutorise()
            .numeroMachAutorise(DEFAULT_NUMERO_MACH_AUTORISE)
            .adresseMacMachAutorise(DEFAULT_ADRESSE_MAC_MACH_AUTORISE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return machAutorise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachAutorise createUpdatedEntity(EntityManager em) {
        MachAutorise machAutorise = new MachAutorise()
            .numeroMachAutorise(UPDATED_NUMERO_MACH_AUTORISE)
            .adresseMacMachAutorise(UPDATED_ADRESSE_MAC_MACH_AUTORISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return machAutorise;
    }

    @BeforeEach
    public void initTest() {
        machAutorise = createEntity(em);
    }

    @Test
    @Transactional
    public void createMachAutorise() throws Exception {
        int databaseSizeBeforeCreate = machAutoriseRepository.findAll().size();

        // Create the MachAutorise
        restMachAutoriseMockMvc.perform(post("/api/mach-autorises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(machAutorise)))
            .andExpect(status().isCreated());

        // Validate the MachAutorise in the database
        List<MachAutorise> machAutoriseList = machAutoriseRepository.findAll();
        assertThat(machAutoriseList).hasSize(databaseSizeBeforeCreate + 1);
        MachAutorise testMachAutorise = machAutoriseList.get(machAutoriseList.size() - 1);
        assertThat(testMachAutorise.getNumeroMachAutorise()).isEqualTo(DEFAULT_NUMERO_MACH_AUTORISE);
        assertThat(testMachAutorise.getAdresseMacMachAutorise()).isEqualTo(DEFAULT_ADRESSE_MAC_MACH_AUTORISE);
        assertThat(testMachAutorise.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testMachAutorise.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testMachAutorise.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testMachAutorise.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testMachAutorise.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createMachAutoriseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = machAutoriseRepository.findAll().size();

        // Create the MachAutorise with an existing ID
        machAutorise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachAutoriseMockMvc.perform(post("/api/mach-autorises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(machAutorise)))
            .andExpect(status().isBadRequest());

        // Validate the MachAutorise in the database
        List<MachAutorise> machAutoriseList = machAutoriseRepository.findAll();
        assertThat(machAutoriseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMachAutorises() throws Exception {
        // Initialize the database
        machAutoriseRepository.saveAndFlush(machAutorise);

        // Get all the machAutoriseList
        restMachAutoriseMockMvc.perform(get("/api/mach-autorises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machAutorise.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroMachAutorise").value(hasItem(DEFAULT_NUMERO_MACH_AUTORISE)))
            .andExpect(jsonPath("$.[*].adresseMacMachAutorise").value(hasItem(DEFAULT_ADRESSE_MAC_MACH_AUTORISE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getMachAutorise() throws Exception {
        // Initialize the database
        machAutoriseRepository.saveAndFlush(machAutorise);

        // Get the machAutorise
        restMachAutoriseMockMvc.perform(get("/api/mach-autorises/{id}", machAutorise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machAutorise.getId().intValue()))
            .andExpect(jsonPath("$.numeroMachAutorise").value(DEFAULT_NUMERO_MACH_AUTORISE))
            .andExpect(jsonPath("$.adresseMacMachAutorise").value(DEFAULT_ADRESSE_MAC_MACH_AUTORISE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMachAutorise() throws Exception {
        // Get the machAutorise
        restMachAutoriseMockMvc.perform(get("/api/mach-autorises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMachAutorise() throws Exception {
        // Initialize the database
        machAutoriseService.save(machAutorise);

        int databaseSizeBeforeUpdate = machAutoriseRepository.findAll().size();

        // Update the machAutorise
        MachAutorise updatedMachAutorise = machAutoriseRepository.findById(machAutorise.getId()).get();
        // Disconnect from session so that the updates on updatedMachAutorise are not directly saved in db
        em.detach(updatedMachAutorise);
        updatedMachAutorise
            .numeroMachAutorise(UPDATED_NUMERO_MACH_AUTORISE)
            .adresseMacMachAutorise(UPDATED_ADRESSE_MAC_MACH_AUTORISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restMachAutoriseMockMvc.perform(put("/api/mach-autorises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMachAutorise)))
            .andExpect(status().isOk());

        // Validate the MachAutorise in the database
        List<MachAutorise> machAutoriseList = machAutoriseRepository.findAll();
        assertThat(machAutoriseList).hasSize(databaseSizeBeforeUpdate);
        MachAutorise testMachAutorise = machAutoriseList.get(machAutoriseList.size() - 1);
        assertThat(testMachAutorise.getNumeroMachAutorise()).isEqualTo(UPDATED_NUMERO_MACH_AUTORISE);
        assertThat(testMachAutorise.getAdresseMacMachAutorise()).isEqualTo(UPDATED_ADRESSE_MAC_MACH_AUTORISE);
        assertThat(testMachAutorise.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMachAutorise.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testMachAutorise.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testMachAutorise.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testMachAutorise.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMachAutorise() throws Exception {
        int databaseSizeBeforeUpdate = machAutoriseRepository.findAll().size();

        // Create the MachAutorise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachAutoriseMockMvc.perform(put("/api/mach-autorises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(machAutorise)))
            .andExpect(status().isBadRequest());

        // Validate the MachAutorise in the database
        List<MachAutorise> machAutoriseList = machAutoriseRepository.findAll();
        assertThat(machAutoriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMachAutorise() throws Exception {
        // Initialize the database
        machAutoriseService.save(machAutorise);

        int databaseSizeBeforeDelete = machAutoriseRepository.findAll().size();

        // Delete the machAutorise
        restMachAutoriseMockMvc.perform(delete("/api/mach-autorises/{id}", machAutorise.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MachAutorise> machAutoriseList = machAutoriseRepository.findAll();
        assertThat(machAutoriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
