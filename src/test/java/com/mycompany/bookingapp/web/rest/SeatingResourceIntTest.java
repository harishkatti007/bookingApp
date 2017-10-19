package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.domain.SeatType;
import com.mycompany.bookingapp.repository.SeatingRepository;
import com.mycompany.bookingapp.service.SeatingService;
import com.mycompany.bookingapp.service.dto.SeatingDTO;
import com.mycompany.bookingapp.service.mapper.SeatingMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeatingResource REST controller.
 *
 * @see SeatingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class SeatingResourceIntTest {

    private static final String DEFAULT_SEAT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SEAT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SEAT_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_BOOKED = false;
    private static final Boolean UPDATED_BOOKED = true;

    @Autowired
    private SeatingRepository seatingRepository;

    @Autowired
    private SeatingMapper seatingMapper;

    @Autowired
    private SeatingService seatingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeatingMockMvc;

    private Seating seating;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeatingResource seatingResource = new SeatingResource(seatingService);
        this.restSeatingMockMvc = MockMvcBuilders.standaloneSetup(seatingResource)
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
    public static Seating createEntity(EntityManager em) {
        Seating seating = new Seating()
            .seatCode(DEFAULT_SEAT_CODE)
            .seatName(DEFAULT_SEAT_NAME)
            .seatPrice(DEFAULT_SEAT_PRICE)
            .booked(DEFAULT_BOOKED);
        // Add required entity
        SeatRows seatRows = SeatRowsResourceIntTest.createEntity(em);
        em.persist(seatRows);
        em.flush();
        seating.setSeatRows(seatRows);
        // Add required entity
        SeatType seatType = SeatTypeResourceIntTest.createEntity(em);
        em.persist(seatType);
        em.flush();
        seating.setSeatType(seatType);
        return seating;
    }

    @Before
    public void initTest() {
        seating = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeating() throws Exception {
        int databaseSizeBeforeCreate = seatingRepository.findAll().size();

        // Create the Seating
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);
        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isCreated());

        // Validate the Seating in the database
        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeCreate + 1);
        Seating testSeating = seatingList.get(seatingList.size() - 1);
        assertThat(testSeating.getSeatCode()).isEqualTo(DEFAULT_SEAT_CODE);
        assertThat(testSeating.getSeatName()).isEqualTo(DEFAULT_SEAT_NAME);
        assertThat(testSeating.getSeatPrice()).isEqualTo(DEFAULT_SEAT_PRICE);
        assertThat(testSeating.isBooked()).isEqualTo(DEFAULT_BOOKED);
    }

    @Test
    @Transactional
    public void createSeatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatingRepository.findAll().size();

        // Create the Seating with an existing ID
        seating.setId(1L);
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeatCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatingRepository.findAll().size();
        // set the field null
        seating.setSeatCode(null);

        // Create the Seating, which fails.
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isBadRequest());

        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeatNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatingRepository.findAll().size();
        // set the field null
        seating.setSeatName(null);

        // Create the Seating, which fails.
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isBadRequest());

        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeatPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatingRepository.findAll().size();
        // set the field null
        seating.setSeatPrice(null);

        // Create the Seating, which fails.
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isBadRequest());

        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookedIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatingRepository.findAll().size();
        // set the field null
        seating.setBooked(null);

        // Create the Seating, which fails.
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        restSeatingMockMvc.perform(post("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isBadRequest());

        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeatings() throws Exception {
        // Initialize the database
        seatingRepository.saveAndFlush(seating);

        // Get all the seatingList
        restSeatingMockMvc.perform(get("/api/seatings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seating.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatCode").value(hasItem(DEFAULT_SEAT_CODE.toString())))
            .andExpect(jsonPath("$.[*].seatName").value(hasItem(DEFAULT_SEAT_NAME.toString())))
            .andExpect(jsonPath("$.[*].seatPrice").value(hasItem(DEFAULT_SEAT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].booked").value(hasItem(DEFAULT_BOOKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSeating() throws Exception {
        // Initialize the database
        seatingRepository.saveAndFlush(seating);

        // Get the seating
        restSeatingMockMvc.perform(get("/api/seatings/{id}", seating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seating.getId().intValue()))
            .andExpect(jsonPath("$.seatCode").value(DEFAULT_SEAT_CODE.toString()))
            .andExpect(jsonPath("$.seatName").value(DEFAULT_SEAT_NAME.toString()))
            .andExpect(jsonPath("$.seatPrice").value(DEFAULT_SEAT_PRICE.intValue()))
            .andExpect(jsonPath("$.booked").value(DEFAULT_BOOKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeating() throws Exception {
        // Get the seating
        restSeatingMockMvc.perform(get("/api/seatings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeating() throws Exception {
        // Initialize the database
        seatingRepository.saveAndFlush(seating);
        int databaseSizeBeforeUpdate = seatingRepository.findAll().size();

        // Update the seating
        Seating updatedSeating = seatingRepository.findOne(seating.getId());
        updatedSeating
            .seatCode(UPDATED_SEAT_CODE)
            .seatName(UPDATED_SEAT_NAME)
            .seatPrice(UPDATED_SEAT_PRICE)
            .booked(UPDATED_BOOKED);
        SeatingDTO seatingDTO = seatingMapper.toDto(updatedSeating);

        restSeatingMockMvc.perform(put("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isOk());

        // Validate the Seating in the database
        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeUpdate);
        Seating testSeating = seatingList.get(seatingList.size() - 1);
        assertThat(testSeating.getSeatCode()).isEqualTo(UPDATED_SEAT_CODE);
        assertThat(testSeating.getSeatName()).isEqualTo(UPDATED_SEAT_NAME);
        assertThat(testSeating.getSeatPrice()).isEqualTo(UPDATED_SEAT_PRICE);
        assertThat(testSeating.isBooked()).isEqualTo(UPDATED_BOOKED);
    }

    @Test
    @Transactional
    public void updateNonExistingSeating() throws Exception {
        int databaseSizeBeforeUpdate = seatingRepository.findAll().size();

        // Create the Seating
        SeatingDTO seatingDTO = seatingMapper.toDto(seating);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeatingMockMvc.perform(put("/api/seatings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatingDTO)))
            .andExpect(status().isCreated());

        // Validate the Seating in the database
        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeating() throws Exception {
        // Initialize the database
        seatingRepository.saveAndFlush(seating);
        int databaseSizeBeforeDelete = seatingRepository.findAll().size();

        // Get the seating
        restSeatingMockMvc.perform(delete("/api/seatings/{id}", seating.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seating> seatingList = seatingRepository.findAll();
        assertThat(seatingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seating.class);
        Seating seating1 = new Seating();
        seating1.setId(1L);
        Seating seating2 = new Seating();
        seating2.setId(seating1.getId());
        assertThat(seating1).isEqualTo(seating2);
        seating2.setId(2L);
        assertThat(seating1).isNotEqualTo(seating2);
        seating1.setId(null);
        assertThat(seating1).isNotEqualTo(seating2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatingDTO.class);
        SeatingDTO seatingDTO1 = new SeatingDTO();
        seatingDTO1.setId(1L);
        SeatingDTO seatingDTO2 = new SeatingDTO();
        assertThat(seatingDTO1).isNotEqualTo(seatingDTO2);
        seatingDTO2.setId(seatingDTO1.getId());
        assertThat(seatingDTO1).isEqualTo(seatingDTO2);
        seatingDTO2.setId(2L);
        assertThat(seatingDTO1).isNotEqualTo(seatingDTO2);
        seatingDTO1.setId(null);
        assertThat(seatingDTO1).isNotEqualTo(seatingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seatingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seatingMapper.fromId(null)).isNull();
    }
}
