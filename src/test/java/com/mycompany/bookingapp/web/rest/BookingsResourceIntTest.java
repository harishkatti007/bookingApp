package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Bookings;
import com.mycompany.bookingapp.domain.UserAccount;
import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.repository.BookingsRepository;
import com.mycompany.bookingapp.service.BookingsService;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.mapper.BookingsMapper;
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

import com.mycompany.bookingapp.domain.enumeration.BookingStatus;
/**
 * Test class for the BookingsResource REST controller.
 *
 * @see BookingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class BookingsResourceIntTest {

    private static final ZonedDateTime DEFAULT_BOOKING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BOOKING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final BookingStatus DEFAULT_BOOKING_STATUS = BookingStatus.CONFIRMED;
    private static final BookingStatus UPDATED_BOOKING_STATUS = BookingStatus.PENDING;

    private static final String DEFAULT_SEAT_IDS = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_IDS = "BBBBBBBBBB";

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private BookingsMapper bookingsMapper;

    @Autowired
    private BookingsService bookingsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookingsMockMvc;

    private Bookings bookings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingsResource bookingsResource = new BookingsResource(bookingsService);
        this.restBookingsMockMvc = MockMvcBuilders.standaloneSetup(bookingsResource)
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
    public static Bookings createEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .bookingDate(DEFAULT_BOOKING_DATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .bookingStatus(DEFAULT_BOOKING_STATUS)
            .seatIds(DEFAULT_SEAT_IDS);
        // Add required entity
        UserAccount userAccount = UserAccountResourceIntTest.createEntity(em);
        em.persist(userAccount);
        em.flush();
        bookings.setUserAccount(userAccount);
        // Add required entity
        Movies movies = MoviesResourceIntTest.createEntity(em);
        em.persist(movies);
        em.flush();
        bookings.setMovies(movies);
        // Add required entity
        Screening screening = ScreeningResourceIntTest.createEntity(em);
        em.persist(screening);
        em.flush();
        bookings.setScreening(screening);
        return bookings;
    }

    @Before
    public void initTest() {
        bookings = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookings() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate + 1);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getBookingDate()).isEqualTo(DEFAULT_BOOKING_DATE);
        assertThat(testBookings.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBookings.getBookingStatus()).isEqualTo(DEFAULT_BOOKING_STATUS);
        assertThat(testBookings.getSeatIds()).isEqualTo(DEFAULT_SEAT_IDS);
    }

    @Test
    @Transactional
    public void createBookingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings with an existing ID
        bookings.setId(1L);
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBookingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setBookingDate(null);

        // Create the Bookings, which fails.
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setTotalAmount(null);

        // Create the Bookings, which fails.
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setBookingStatus(null);

        // Create the Bookings, which fails.
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeatIdsIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setSeatIds(null);

        // Create the Bookings, which fails.
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList
        restBookingsMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(sameInstant(DEFAULT_BOOKING_DATE))))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].bookingStatus").value(hasItem(DEFAULT_BOOKING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].seatIds").value(hasItem(DEFAULT_SEAT_IDS.toString())));
    }

    @Test
    @Transactional
    public void getBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookings.getId().intValue()))
            .andExpect(jsonPath("$.bookingDate").value(sameInstant(DEFAULT_BOOKING_DATE)))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bookingStatus").value(DEFAULT_BOOKING_STATUS.toString()))
            .andExpect(jsonPath("$.seatIds").value(DEFAULT_SEAT_IDS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookings() throws Exception {
        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings
        Bookings updatedBookings = bookingsRepository.findOne(bookings.getId());
        updatedBookings
            .bookingDate(UPDATED_BOOKING_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .bookingStatus(UPDATED_BOOKING_STATUS)
            .seatIds(UPDATED_SEAT_IDS);
        BookingsDTO bookingsDTO = bookingsMapper.toDto(updatedBookings);

        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getBookingDate()).isEqualTo(UPDATED_BOOKING_DATE);
        assertThat(testBookings.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBookings.getBookingStatus()).isEqualTo(UPDATED_BOOKING_STATUS);
        assertThat(testBookings.getSeatIds()).isEqualTo(UPDATED_SEAT_IDS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Create the Bookings
        BookingsDTO bookingsDTO = bookingsMapper.toDto(bookings);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);
        int databaseSizeBeforeDelete = bookingsRepository.findAll().size();

        // Get the bookings
        restBookingsMockMvc.perform(delete("/api/bookings/{id}", bookings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookings.class);
        Bookings bookings1 = new Bookings();
        bookings1.setId(1L);
        Bookings bookings2 = new Bookings();
        bookings2.setId(bookings1.getId());
        assertThat(bookings1).isEqualTo(bookings2);
        bookings2.setId(2L);
        assertThat(bookings1).isNotEqualTo(bookings2);
        bookings1.setId(null);
        assertThat(bookings1).isNotEqualTo(bookings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingsDTO.class);
        BookingsDTO bookingsDTO1 = new BookingsDTO();
        bookingsDTO1.setId(1L);
        BookingsDTO bookingsDTO2 = new BookingsDTO();
        assertThat(bookingsDTO1).isNotEqualTo(bookingsDTO2);
        bookingsDTO2.setId(bookingsDTO1.getId());
        assertThat(bookingsDTO1).isEqualTo(bookingsDTO2);
        bookingsDTO2.setId(2L);
        assertThat(bookingsDTO1).isNotEqualTo(bookingsDTO2);
        bookingsDTO1.setId(null);
        assertThat(bookingsDTO1).isNotEqualTo(bookingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookingsMapper.fromId(null)).isNull();
    }
}
