package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Screens;
import com.mycompany.bookingapp.domain.Venues;
import com.mycompany.bookingapp.repository.ScreensRepository;
import com.mycompany.bookingapp.service.ScreensService;
import com.mycompany.bookingapp.service.dto.ScreensDTO;
import com.mycompany.bookingapp.service.mapper.ScreensMapper;
import com.mycompany.bookingapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScreensResource REST controller.
 *
 * @see ScreensResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class ScreensResourceIntTest {

    private static final String DEFAULT_SCREEN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCREEN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCREEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCREEN_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    @Autowired
    private ScreensRepository screensRepository;

    @Autowired
    private ScreensMapper screensMapper;

    @Autowired
    private ScreensService screensService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScreensMockMvc;

    private Screens screens;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScreensResource screensResource = new ScreensResource(screensService);
        this.restScreensMockMvc = MockMvcBuilders.standaloneSetup(screensResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Screens createEntity(EntityManager em) {
        Screens screens = new Screens()
            .screenCode(DEFAULT_SCREEN_CODE)
            .screenName(DEFAULT_SCREEN_NAME)
            .capacity(DEFAULT_CAPACITY);
        // Add required entity
        Venues venues = VenuesResourceIntTest.createEntity(em);
        em.persist(venues);
        em.flush();
        screens.setVenues(venues);
        return screens;
    }

    @Before
    public void initTest() {
        screens = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreens() throws Exception {
        int databaseSizeBeforeCreate = screensRepository.findAll().size();

        // Create the Screens
        ScreensDTO screensDTO = screensMapper.toDto(screens);
        restScreensMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isCreated());

        // Validate the Screens in the database
        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeCreate + 1);
        Screens testScreens = screensList.get(screensList.size() - 1);
        assertThat(testScreens.getScreenCode()).isEqualTo(DEFAULT_SCREEN_CODE);
        assertThat(testScreens.getScreenName()).isEqualTo(DEFAULT_SCREEN_NAME);
        assertThat(testScreens.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createScreensWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screensRepository.findAll().size();

        // Create the Screens with an existing ID
        screens.setId(1L);
        ScreensDTO screensDTO = screensMapper.toDto(screens);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreensMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScreenCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = screensRepository.findAll().size();
        // set the field null
        screens.setScreenCode(null);

        // Create the Screens, which fails.
        ScreensDTO screensDTO = screensMapper.toDto(screens);

        restScreensMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isBadRequest());

        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScreenNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = screensRepository.findAll().size();
        // set the field null
        screens.setScreenName(null);

        // Create the Screens, which fails.
        ScreensDTO screensDTO = screensMapper.toDto(screens);

        restScreensMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isBadRequest());

        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = screensRepository.findAll().size();
        // set the field null
        screens.setCapacity(null);

        // Create the Screens, which fails.
        ScreensDTO screensDTO = screensMapper.toDto(screens);

        restScreensMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isBadRequest());

        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScreens() throws Exception {
        // Initialize the database
        screensRepository.saveAndFlush(screens);

        // Get all the screensList
        restScreensMockMvc.perform(get("/api/screens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screens.getId().intValue())))
            .andExpect(jsonPath("$.[*].screenCode").value(hasItem(DEFAULT_SCREEN_CODE.toString())))
            .andExpect(jsonPath("$.[*].screenName").value(hasItem(DEFAULT_SCREEN_NAME.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
    }

    @Test
    @Transactional
    public void getScreens() throws Exception {
        // Initialize the database
        screensRepository.saveAndFlush(screens);

        // Get the screens
        restScreensMockMvc.perform(get("/api/screens/{id}", screens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(screens.getId().intValue()))
            .andExpect(jsonPath("$.screenCode").value(DEFAULT_SCREEN_CODE.toString()))
            .andExpect(jsonPath("$.screenName").value(DEFAULT_SCREEN_NAME.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingScreens() throws Exception {
        // Get the screens
        restScreensMockMvc.perform(get("/api/screens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreens() throws Exception {
        // Initialize the database
        screensRepository.saveAndFlush(screens);
        int databaseSizeBeforeUpdate = screensRepository.findAll().size();

        // Update the screens
        Screens updatedScreens = screensRepository.findOne(screens.getId());
        updatedScreens
            .screenCode(UPDATED_SCREEN_CODE)
            .screenName(UPDATED_SCREEN_NAME)
            .capacity(UPDATED_CAPACITY);
        ScreensDTO screensDTO = screensMapper.toDto(updatedScreens);

        restScreensMockMvc.perform(put("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isOk());

        // Validate the Screens in the database
        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeUpdate);
        Screens testScreens = screensList.get(screensList.size() - 1);
        assertThat(testScreens.getScreenCode()).isEqualTo(UPDATED_SCREEN_CODE);
        assertThat(testScreens.getScreenName()).isEqualTo(UPDATED_SCREEN_NAME);
        assertThat(testScreens.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void updateNonExistingScreens() throws Exception {
        int databaseSizeBeforeUpdate = screensRepository.findAll().size();

        // Create the Screens
        ScreensDTO screensDTO = screensMapper.toDto(screens);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScreensMockMvc.perform(put("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screensDTO)))
            .andExpect(status().isCreated());

        // Validate the Screens in the database
        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScreens() throws Exception {
        // Initialize the database
        screensRepository.saveAndFlush(screens);
        int databaseSizeBeforeDelete = screensRepository.findAll().size();

        // Get the screens
        restScreensMockMvc.perform(delete("/api/screens/{id}", screens.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Screens> screensList = screensRepository.findAll();
        assertThat(screensList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screens.class);
        Screens screens1 = new Screens();
        screens1.setId(1L);
        Screens screens2 = new Screens();
        screens2.setId(screens1.getId());
        assertThat(screens1).isEqualTo(screens2);
        screens2.setId(2L);
        assertThat(screens1).isNotEqualTo(screens2);
        screens1.setId(null);
        assertThat(screens1).isNotEqualTo(screens2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreensDTO.class);
        ScreensDTO screensDTO1 = new ScreensDTO();
        screensDTO1.setId(1L);
        ScreensDTO screensDTO2 = new ScreensDTO();
        assertThat(screensDTO1).isNotEqualTo(screensDTO2);
        screensDTO2.setId(screensDTO1.getId());
        assertThat(screensDTO1).isEqualTo(screensDTO2);
        screensDTO2.setId(2L);
        assertThat(screensDTO1).isNotEqualTo(screensDTO2);
        screensDTO1.setId(null);
        assertThat(screensDTO1).isNotEqualTo(screensDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(screensMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(screensMapper.fromId(null)).isNull();
    }
}
