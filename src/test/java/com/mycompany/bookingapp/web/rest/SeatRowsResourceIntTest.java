package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.repository.SeatRowsRepository;
import com.mycompany.bookingapp.service.SeatRowsService;
import com.mycompany.bookingapp.service.dto.SeatRowsDTO;
import com.mycompany.bookingapp.service.mapper.SeatRowsMapper;
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
 * Test class for the SeatRowsResource REST controller.
 *
 * @see SeatRowsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class SeatRowsResourceIntTest {

    private static final String DEFAULT_ROW_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ROW_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROW_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROW_NAME = "BBBBBBBBBB";

    @Autowired
    private SeatRowsRepository seatRowsRepository;

    @Autowired
    private SeatRowsMapper seatRowsMapper;

    @Autowired
    private SeatRowsService seatRowsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeatRowsMockMvc;

    private SeatRows seatRows;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeatRowsResource seatRowsResource = new SeatRowsResource(seatRowsService);
        this.restSeatRowsMockMvc = MockMvcBuilders.standaloneSetup(seatRowsResource)
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
    public static SeatRows createEntity(EntityManager em) {
        SeatRows seatRows = new SeatRows()
            .rowCode(DEFAULT_ROW_CODE)
            .rowName(DEFAULT_ROW_NAME);
        // Add required entity
        Screening screening = ScreeningResourceIntTest.createEntity(em);
        em.persist(screening);
        em.flush();
        seatRows.setScreening(screening);
        return seatRows;
    }

    @Before
    public void initTest() {
        seatRows = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeatRows() throws Exception {
        int databaseSizeBeforeCreate = seatRowsRepository.findAll().size();

        // Create the SeatRows
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(seatRows);
        restSeatRowsMockMvc.perform(post("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isCreated());

        // Validate the SeatRows in the database
        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeCreate + 1);
        SeatRows testSeatRows = seatRowsList.get(seatRowsList.size() - 1);
        assertThat(testSeatRows.getRowCode()).isEqualTo(DEFAULT_ROW_CODE);
        assertThat(testSeatRows.getRowName()).isEqualTo(DEFAULT_ROW_NAME);
    }

    @Test
    @Transactional
    public void createSeatRowsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatRowsRepository.findAll().size();

        // Create the SeatRows with an existing ID
        seatRows.setId(1L);
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(seatRows);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatRowsMockMvc.perform(post("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRowCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRowsRepository.findAll().size();
        // set the field null
        seatRows.setRowCode(null);

        // Create the SeatRows, which fails.
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(seatRows);

        restSeatRowsMockMvc.perform(post("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isBadRequest());

        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRowNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatRowsRepository.findAll().size();
        // set the field null
        seatRows.setRowName(null);

        // Create the SeatRows, which fails.
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(seatRows);

        restSeatRowsMockMvc.perform(post("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isBadRequest());

        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeatRows() throws Exception {
        // Initialize the database
        seatRowsRepository.saveAndFlush(seatRows);

        // Get all the seatRowsList
        restSeatRowsMockMvc.perform(get("/api/seat-rows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seatRows.getId().intValue())))
            .andExpect(jsonPath("$.[*].rowCode").value(hasItem(DEFAULT_ROW_CODE.toString())))
            .andExpect(jsonPath("$.[*].rowName").value(hasItem(DEFAULT_ROW_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSeatRows() throws Exception {
        // Initialize the database
        seatRowsRepository.saveAndFlush(seatRows);

        // Get the seatRows
        restSeatRowsMockMvc.perform(get("/api/seat-rows/{id}", seatRows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seatRows.getId().intValue()))
            .andExpect(jsonPath("$.rowCode").value(DEFAULT_ROW_CODE.toString()))
            .andExpect(jsonPath("$.rowName").value(DEFAULT_ROW_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeatRows() throws Exception {
        // Get the seatRows
        restSeatRowsMockMvc.perform(get("/api/seat-rows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeatRows() throws Exception {
        // Initialize the database
        seatRowsRepository.saveAndFlush(seatRows);
        int databaseSizeBeforeUpdate = seatRowsRepository.findAll().size();

        // Update the seatRows
        SeatRows updatedSeatRows = seatRowsRepository.findOne(seatRows.getId());
        updatedSeatRows
            .rowCode(UPDATED_ROW_CODE)
            .rowName(UPDATED_ROW_NAME);
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(updatedSeatRows);

        restSeatRowsMockMvc.perform(put("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isOk());

        // Validate the SeatRows in the database
        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeUpdate);
        SeatRows testSeatRows = seatRowsList.get(seatRowsList.size() - 1);
        assertThat(testSeatRows.getRowCode()).isEqualTo(UPDATED_ROW_CODE);
        assertThat(testSeatRows.getRowName()).isEqualTo(UPDATED_ROW_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeatRows() throws Exception {
        int databaseSizeBeforeUpdate = seatRowsRepository.findAll().size();

        // Create the SeatRows
        SeatRowsDTO seatRowsDTO = seatRowsMapper.toDto(seatRows);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeatRowsMockMvc.perform(put("/api/seat-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatRowsDTO)))
            .andExpect(status().isCreated());

        // Validate the SeatRows in the database
        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeatRows() throws Exception {
        // Initialize the database
        seatRowsRepository.saveAndFlush(seatRows);
        int databaseSizeBeforeDelete = seatRowsRepository.findAll().size();

        // Get the seatRows
        restSeatRowsMockMvc.perform(delete("/api/seat-rows/{id}", seatRows.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeatRows> seatRowsList = seatRowsRepository.findAll();
        assertThat(seatRowsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatRows.class);
        SeatRows seatRows1 = new SeatRows();
        seatRows1.setId(1L);
        SeatRows seatRows2 = new SeatRows();
        seatRows2.setId(seatRows1.getId());
        assertThat(seatRows1).isEqualTo(seatRows2);
        seatRows2.setId(2L);
        assertThat(seatRows1).isNotEqualTo(seatRows2);
        seatRows1.setId(null);
        assertThat(seatRows1).isNotEqualTo(seatRows2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatRowsDTO.class);
        SeatRowsDTO seatRowsDTO1 = new SeatRowsDTO();
        seatRowsDTO1.setId(1L);
        SeatRowsDTO seatRowsDTO2 = new SeatRowsDTO();
        assertThat(seatRowsDTO1).isNotEqualTo(seatRowsDTO2);
        seatRowsDTO2.setId(seatRowsDTO1.getId());
        assertThat(seatRowsDTO1).isEqualTo(seatRowsDTO2);
        seatRowsDTO2.setId(2L);
        assertThat(seatRowsDTO1).isNotEqualTo(seatRowsDTO2);
        seatRowsDTO1.setId(null);
        assertThat(seatRowsDTO1).isNotEqualTo(seatRowsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seatRowsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seatRowsMapper.fromId(null)).isNull();
    }
}
