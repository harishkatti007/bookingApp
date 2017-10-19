package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Venues;
import com.mycompany.bookingapp.domain.Regions;
import com.mycompany.bookingapp.repository.VenuesRepository;
import com.mycompany.bookingapp.service.VenuesService;
import com.mycompany.bookingapp.service.dto.VenuesDTO;
import com.mycompany.bookingapp.service.mapper.VenuesMapper;
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
 * Test class for the VenuesResource REST controller.
 *
 * @see VenuesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class VenuesResourceIntTest {

    private static final String DEFAULT_VENUE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENUE_NAME = "BBBBBBBBBB";

    @Autowired
    private VenuesRepository venuesRepository;

    @Autowired
    private VenuesMapper venuesMapper;

    @Autowired
    private VenuesService venuesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVenuesMockMvc;

    private Venues venues;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VenuesResource venuesResource = new VenuesResource(venuesService);
        this.restVenuesMockMvc = MockMvcBuilders.standaloneSetup(venuesResource)
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
    public static Venues createEntity(EntityManager em) {
        Venues venues = new Venues()
            .venueCode(DEFAULT_VENUE_CODE)
            .venueName(DEFAULT_VENUE_NAME);
        // Add required entity
        Regions regions = RegionsResourceIntTest.createEntity(em);
        em.persist(regions);
        em.flush();
        venues.setRegions(regions);
        return venues;
    }

    @Before
    public void initTest() {
        venues = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenues() throws Exception {
        int databaseSizeBeforeCreate = venuesRepository.findAll().size();

        // Create the Venues
        VenuesDTO venuesDTO = venuesMapper.toDto(venues);
        restVenuesMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isCreated());

        // Validate the Venues in the database
        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeCreate + 1);
        Venues testVenues = venuesList.get(venuesList.size() - 1);
        assertThat(testVenues.getVenueCode()).isEqualTo(DEFAULT_VENUE_CODE);
        assertThat(testVenues.getVenueName()).isEqualTo(DEFAULT_VENUE_NAME);
    }

    @Test
    @Transactional
    public void createVenuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = venuesRepository.findAll().size();

        // Create the Venues with an existing ID
        venues.setId(1L);
        VenuesDTO venuesDTO = venuesMapper.toDto(venues);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenuesMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVenueCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = venuesRepository.findAll().size();
        // set the field null
        venues.setVenueCode(null);

        // Create the Venues, which fails.
        VenuesDTO venuesDTO = venuesMapper.toDto(venues);

        restVenuesMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isBadRequest());

        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVenueNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = venuesRepository.findAll().size();
        // set the field null
        venues.setVenueName(null);

        // Create the Venues, which fails.
        VenuesDTO venuesDTO = venuesMapper.toDto(venues);

        restVenuesMockMvc.perform(post("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isBadRequest());

        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVenues() throws Exception {
        // Initialize the database
        venuesRepository.saveAndFlush(venues);

        // Get all the venuesList
        restVenuesMockMvc.perform(get("/api/venues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venues.getId().intValue())))
            .andExpect(jsonPath("$.[*].venueCode").value(hasItem(DEFAULT_VENUE_CODE.toString())))
            .andExpect(jsonPath("$.[*].venueName").value(hasItem(DEFAULT_VENUE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getVenues() throws Exception {
        // Initialize the database
        venuesRepository.saveAndFlush(venues);

        // Get the venues
        restVenuesMockMvc.perform(get("/api/venues/{id}", venues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venues.getId().intValue()))
            .andExpect(jsonPath("$.venueCode").value(DEFAULT_VENUE_CODE.toString()))
            .andExpect(jsonPath("$.venueName").value(DEFAULT_VENUE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVenues() throws Exception {
        // Get the venues
        restVenuesMockMvc.perform(get("/api/venues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenues() throws Exception {
        // Initialize the database
        venuesRepository.saveAndFlush(venues);
        int databaseSizeBeforeUpdate = venuesRepository.findAll().size();

        // Update the venues
        Venues updatedVenues = venuesRepository.findOne(venues.getId());
        updatedVenues
            .venueCode(UPDATED_VENUE_CODE)
            .venueName(UPDATED_VENUE_NAME);
        VenuesDTO venuesDTO = venuesMapper.toDto(updatedVenues);

        restVenuesMockMvc.perform(put("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isOk());

        // Validate the Venues in the database
        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeUpdate);
        Venues testVenues = venuesList.get(venuesList.size() - 1);
        assertThat(testVenues.getVenueCode()).isEqualTo(UPDATED_VENUE_CODE);
        assertThat(testVenues.getVenueName()).isEqualTo(UPDATED_VENUE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVenues() throws Exception {
        int databaseSizeBeforeUpdate = venuesRepository.findAll().size();

        // Create the Venues
        VenuesDTO venuesDTO = venuesMapper.toDto(venues);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVenuesMockMvc.perform(put("/api/venues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venuesDTO)))
            .andExpect(status().isCreated());

        // Validate the Venues in the database
        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVenues() throws Exception {
        // Initialize the database
        venuesRepository.saveAndFlush(venues);
        int databaseSizeBeforeDelete = venuesRepository.findAll().size();

        // Get the venues
        restVenuesMockMvc.perform(delete("/api/venues/{id}", venues.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Venues> venuesList = venuesRepository.findAll();
        assertThat(venuesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venues.class);
        Venues venues1 = new Venues();
        venues1.setId(1L);
        Venues venues2 = new Venues();
        venues2.setId(venues1.getId());
        assertThat(venues1).isEqualTo(venues2);
        venues2.setId(2L);
        assertThat(venues1).isNotEqualTo(venues2);
        venues1.setId(null);
        assertThat(venues1).isNotEqualTo(venues2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VenuesDTO.class);
        VenuesDTO venuesDTO1 = new VenuesDTO();
        venuesDTO1.setId(1L);
        VenuesDTO venuesDTO2 = new VenuesDTO();
        assertThat(venuesDTO1).isNotEqualTo(venuesDTO2);
        venuesDTO2.setId(venuesDTO1.getId());
        assertThat(venuesDTO1).isEqualTo(venuesDTO2);
        venuesDTO2.setId(2L);
        assertThat(venuesDTO1).isNotEqualTo(venuesDTO2);
        venuesDTO1.setId(null);
        assertThat(venuesDTO1).isNotEqualTo(venuesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(venuesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(venuesMapper.fromId(null)).isNull();
    }
}
