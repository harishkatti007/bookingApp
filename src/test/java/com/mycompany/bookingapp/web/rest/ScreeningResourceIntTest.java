package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.domain.Screens;
import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.repository.ScreeningRepository;
import com.mycompany.bookingapp.service.ScreeningService;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.mapper.ScreeningMapper;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.bookingapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScreeningResource REST controller.
 *
 * @see ScreeningResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class ScreeningResourceIntTest {

    private static final LocalDate DEFAULT_SCREENING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCREENING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_SCREENING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCREENING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private ScreeningMapper screeningMapper;

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScreeningMockMvc;

    private Screening screening;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScreeningResource screeningResource = new ScreeningResource(screeningService);
        this.restScreeningMockMvc = MockMvcBuilders.standaloneSetup(screeningResource)
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
    public static Screening createEntity(EntityManager em) {
        Screening screening = new Screening()
            .screeningDate(DEFAULT_SCREENING_DATE)
            .screeningTime(DEFAULT_SCREENING_TIME);
        // Add required entity
        Screens screens = ScreensResourceIntTest.createEntity(em);
        em.persist(screens);
        em.flush();
        screening.setScreens(screens);
        // Add required entity
        Movies movies = MoviesResourceIntTest.createEntity(em);
        em.persist(movies);
        em.flush();
        screening.setMovies(movies);
        return screening;
    }

    @Before
    public void initTest() {
        screening = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreening() throws Exception {
        int databaseSizeBeforeCreate = screeningRepository.findAll().size();

        // Create the Screening
        ScreeningDTO screeningDTO = screeningMapper.toDto(screening);
        restScreeningMockMvc.perform(post("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isCreated());

        // Validate the Screening in the database
        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeCreate + 1);
        Screening testScreening = screeningList.get(screeningList.size() - 1);
        assertThat(testScreening.getScreeningDate()).isEqualTo(DEFAULT_SCREENING_DATE);
        assertThat(testScreening.getScreeningTime()).isEqualTo(DEFAULT_SCREENING_TIME);
    }

    @Test
    @Transactional
    public void createScreeningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screeningRepository.findAll().size();

        // Create the Screening with an existing ID
        screening.setId(1L);
        ScreeningDTO screeningDTO = screeningMapper.toDto(screening);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreeningMockMvc.perform(post("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScreeningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = screeningRepository.findAll().size();
        // set the field null
        screening.setScreeningDate(null);

        // Create the Screening, which fails.
        ScreeningDTO screeningDTO = screeningMapper.toDto(screening);

        restScreeningMockMvc.perform(post("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isBadRequest());

        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScreeningTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = screeningRepository.findAll().size();
        // set the field null
        screening.setScreeningTime(null);

        // Create the Screening, which fails.
        ScreeningDTO screeningDTO = screeningMapper.toDto(screening);

        restScreeningMockMvc.perform(post("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isBadRequest());

        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScreenings() throws Exception {
        // Initialize the database
        screeningRepository.saveAndFlush(screening);

        // Get all the screeningList
        restScreeningMockMvc.perform(get("/api/screenings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screening.getId().intValue())))
            .andExpect(jsonPath("$.[*].screeningDate").value(hasItem(DEFAULT_SCREENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].screeningTime").value(hasItem(sameInstant(DEFAULT_SCREENING_TIME))));
    }

    @Test
    @Transactional
    public void getScreening() throws Exception {
        // Initialize the database
        screeningRepository.saveAndFlush(screening);

        // Get the screening
        restScreeningMockMvc.perform(get("/api/screenings/{id}", screening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(screening.getId().intValue()))
            .andExpect(jsonPath("$.screeningDate").value(DEFAULT_SCREENING_DATE.toString()))
            .andExpect(jsonPath("$.screeningTime").value(sameInstant(DEFAULT_SCREENING_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingScreening() throws Exception {
        // Get the screening
        restScreeningMockMvc.perform(get("/api/screenings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreening() throws Exception {
        // Initialize the database
        screeningRepository.saveAndFlush(screening);
        int databaseSizeBeforeUpdate = screeningRepository.findAll().size();

        // Update the screening
        Screening updatedScreening = screeningRepository.findOne(screening.getId());
        updatedScreening
            .screeningDate(UPDATED_SCREENING_DATE)
            .screeningTime(UPDATED_SCREENING_TIME);
        ScreeningDTO screeningDTO = screeningMapper.toDto(updatedScreening);

        restScreeningMockMvc.perform(put("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isOk());

        // Validate the Screening in the database
        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeUpdate);
        Screening testScreening = screeningList.get(screeningList.size() - 1);
        assertThat(testScreening.getScreeningDate()).isEqualTo(UPDATED_SCREENING_DATE);
        assertThat(testScreening.getScreeningTime()).isEqualTo(UPDATED_SCREENING_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingScreening() throws Exception {
        int databaseSizeBeforeUpdate = screeningRepository.findAll().size();

        // Create the Screening
        ScreeningDTO screeningDTO = screeningMapper.toDto(screening);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScreeningMockMvc.perform(put("/api/screenings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screeningDTO)))
            .andExpect(status().isCreated());

        // Validate the Screening in the database
        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScreening() throws Exception {
        // Initialize the database
        screeningRepository.saveAndFlush(screening);
        int databaseSizeBeforeDelete = screeningRepository.findAll().size();

        // Get the screening
        restScreeningMockMvc.perform(delete("/api/screenings/{id}", screening.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Screening> screeningList = screeningRepository.findAll();
        assertThat(screeningList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screening.class);
        Screening screening1 = new Screening();
        screening1.setId(1L);
        Screening screening2 = new Screening();
        screening2.setId(screening1.getId());
        assertThat(screening1).isEqualTo(screening2);
        screening2.setId(2L);
        assertThat(screening1).isNotEqualTo(screening2);
        screening1.setId(null);
        assertThat(screening1).isNotEqualTo(screening2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreeningDTO.class);
        ScreeningDTO screeningDTO1 = new ScreeningDTO();
        screeningDTO1.setId(1L);
        ScreeningDTO screeningDTO2 = new ScreeningDTO();
        assertThat(screeningDTO1).isNotEqualTo(screeningDTO2);
        screeningDTO2.setId(screeningDTO1.getId());
        assertThat(screeningDTO1).isEqualTo(screeningDTO2);
        screeningDTO2.setId(2L);
        assertThat(screeningDTO1).isNotEqualTo(screeningDTO2);
        screeningDTO1.setId(null);
        assertThat(screeningDTO1).isNotEqualTo(screeningDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(screeningMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(screeningMapper.fromId(null)).isNull();
    }
}
