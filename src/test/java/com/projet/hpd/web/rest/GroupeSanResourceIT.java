package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.GroupeSan;
import com.projet.hpd.repository.GroupeSanRepository;
import com.projet.hpd.service.GroupeSanService;
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
 * Integration tests for the {@link GroupeSanResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class GroupeSanResourceIT {

    private static final Integer DEFAULT_CODE_GROUPE_SAN = 1;
    private static final Integer UPDATED_CODE_GROUPE_SAN = 2;

    private static final String DEFAULT_LIBELLE_GROUPE_SAN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_GROUPE_SAN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_GROUPE_SAN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_GROUPE_SAN = "BBBBBBBBBB";

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
    private GroupeSanRepository groupeSanRepository;

    @Autowired
    private GroupeSanService groupeSanService;

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

    private MockMvc restGroupeSanMockMvc;

    private GroupeSan groupeSan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupeSanResource groupeSanResource = new GroupeSanResource(groupeSanService);
        this.restGroupeSanMockMvc = MockMvcBuilders.standaloneSetup(groupeSanResource)
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
    public static GroupeSan createEntity(EntityManager em) {
        GroupeSan groupeSan = new GroupeSan()
            .codeGroupeSan(DEFAULT_CODE_GROUPE_SAN)
            .libelleGroupeSan(DEFAULT_LIBELLE_GROUPE_SAN)
            .descriptionGroupeSan(DEFAULT_DESCRIPTION_GROUPE_SAN)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return groupeSan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupeSan createUpdatedEntity(EntityManager em) {
        GroupeSan groupeSan = new GroupeSan()
            .codeGroupeSan(UPDATED_CODE_GROUPE_SAN)
            .libelleGroupeSan(UPDATED_LIBELLE_GROUPE_SAN)
            .descriptionGroupeSan(UPDATED_DESCRIPTION_GROUPE_SAN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return groupeSan;
    }

    @BeforeEach
    public void initTest() {
        groupeSan = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupeSan() throws Exception {
        int databaseSizeBeforeCreate = groupeSanRepository.findAll().size();

        // Create the GroupeSan
        restGroupeSanMockMvc.perform(post("/api/groupe-sans")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeSan)))
            .andExpect(status().isCreated());

        // Validate the GroupeSan in the database
        List<GroupeSan> groupeSanList = groupeSanRepository.findAll();
        assertThat(groupeSanList).hasSize(databaseSizeBeforeCreate + 1);
        GroupeSan testGroupeSan = groupeSanList.get(groupeSanList.size() - 1);
        assertThat(testGroupeSan.getCodeGroupeSan()).isEqualTo(DEFAULT_CODE_GROUPE_SAN);
        assertThat(testGroupeSan.getLibelleGroupeSan()).isEqualTo(DEFAULT_LIBELLE_GROUPE_SAN);
        assertThat(testGroupeSan.getDescriptionGroupeSan()).isEqualTo(DEFAULT_DESCRIPTION_GROUPE_SAN);
        assertThat(testGroupeSan.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testGroupeSan.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testGroupeSan.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testGroupeSan.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testGroupeSan.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createGroupeSanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeSanRepository.findAll().size();

        // Create the GroupeSan with an existing ID
        groupeSan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeSanMockMvc.perform(post("/api/groupe-sans")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeSan)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeSan in the database
        List<GroupeSan> groupeSanList = groupeSanRepository.findAll();
        assertThat(groupeSanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupeSans() throws Exception {
        // Initialize the database
        groupeSanRepository.saveAndFlush(groupeSan);

        // Get all the groupeSanList
        restGroupeSanMockMvc.perform(get("/api/groupe-sans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeSan.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeGroupeSan").value(hasItem(DEFAULT_CODE_GROUPE_SAN)))
            .andExpect(jsonPath("$.[*].libelleGroupeSan").value(hasItem(DEFAULT_LIBELLE_GROUPE_SAN)))
            .andExpect(jsonPath("$.[*].descriptionGroupeSan").value(hasItem(DEFAULT_DESCRIPTION_GROUPE_SAN)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getGroupeSan() throws Exception {
        // Initialize the database
        groupeSanRepository.saveAndFlush(groupeSan);

        // Get the groupeSan
        restGroupeSanMockMvc.perform(get("/api/groupe-sans/{id}", groupeSan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupeSan.getId().intValue()))
            .andExpect(jsonPath("$.codeGroupeSan").value(DEFAULT_CODE_GROUPE_SAN))
            .andExpect(jsonPath("$.libelleGroupeSan").value(DEFAULT_LIBELLE_GROUPE_SAN))
            .andExpect(jsonPath("$.descriptionGroupeSan").value(DEFAULT_DESCRIPTION_GROUPE_SAN))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupeSan() throws Exception {
        // Get the groupeSan
        restGroupeSanMockMvc.perform(get("/api/groupe-sans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupeSan() throws Exception {
        // Initialize the database
        groupeSanService.save(groupeSan);

        int databaseSizeBeforeUpdate = groupeSanRepository.findAll().size();

        // Update the groupeSan
        GroupeSan updatedGroupeSan = groupeSanRepository.findById(groupeSan.getId()).get();
        // Disconnect from session so that the updates on updatedGroupeSan are not directly saved in db
        em.detach(updatedGroupeSan);
        updatedGroupeSan
            .codeGroupeSan(UPDATED_CODE_GROUPE_SAN)
            .libelleGroupeSan(UPDATED_LIBELLE_GROUPE_SAN)
            .descriptionGroupeSan(UPDATED_DESCRIPTION_GROUPE_SAN)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restGroupeSanMockMvc.perform(put("/api/groupe-sans")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupeSan)))
            .andExpect(status().isOk());

        // Validate the GroupeSan in the database
        List<GroupeSan> groupeSanList = groupeSanRepository.findAll();
        assertThat(groupeSanList).hasSize(databaseSizeBeforeUpdate);
        GroupeSan testGroupeSan = groupeSanList.get(groupeSanList.size() - 1);
        assertThat(testGroupeSan.getCodeGroupeSan()).isEqualTo(UPDATED_CODE_GROUPE_SAN);
        assertThat(testGroupeSan.getLibelleGroupeSan()).isEqualTo(UPDATED_LIBELLE_GROUPE_SAN);
        assertThat(testGroupeSan.getDescriptionGroupeSan()).isEqualTo(UPDATED_DESCRIPTION_GROUPE_SAN);
        assertThat(testGroupeSan.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testGroupeSan.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testGroupeSan.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testGroupeSan.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testGroupeSan.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupeSan() throws Exception {
        int databaseSizeBeforeUpdate = groupeSanRepository.findAll().size();

        // Create the GroupeSan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeSanMockMvc.perform(put("/api/groupe-sans")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeSan)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeSan in the database
        List<GroupeSan> groupeSanList = groupeSanRepository.findAll();
        assertThat(groupeSanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupeSan() throws Exception {
        // Initialize the database
        groupeSanService.save(groupeSan);

        int databaseSizeBeforeDelete = groupeSanRepository.findAll().size();

        // Delete the groupeSan
        restGroupeSanMockMvc.perform(delete("/api/groupe-sans/{id}", groupeSan.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupeSan> groupeSanList = groupeSanRepository.findAll();
        assertThat(groupeSanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
