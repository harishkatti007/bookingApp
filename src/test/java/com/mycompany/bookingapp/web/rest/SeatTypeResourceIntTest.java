package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.SeatType;
import com.mycompany.bookingapp.repository.SeatTypeRepository;
import com.mycompany.bookingapp.service.SeatTypeService;
import com.mycompany.bookingapp.service.dto.SeatTypeDTO;
import com.mycompany.bookingapp.service.mapper.SeatTypeMapper;
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
 * Test class for the SeatTypeResource REST controller.
 *
 * @see SeatTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class SeatTypeResourceIntTest {

    private static final String DEFAULT_SEAT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private SeatTypeMapper seatTypeMapper;

    @Autowired
    private SeatTypeService seatTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeatTypeMockMvc;

    private SeatType seatType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeatTypeResource seatTypeResource = new SeatTypeResource(seatTypeService);
        this.restSeatTypeMockMvc = MockMvcBuilders.standaloneSetup(seatTypeResource)
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
    public static SeatType createEntity(EntityManager em) {
        SeatType seatType = new SeatType()
            .seatTypeCode(DEFAULT_SEAT_TYPE_CODE)
            .seatTypeName(DEFAULT_SEAT_TYPE_NAME);
        return seatType;
    }

    @Before
    public void initTest() {
        seatType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeatType() throws Exception {
        int databaseSizeBeforeCreate = seatTypeRepository.findAll().size();

        // Create the SeatType
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(seatType);
        restSeatTypeMockMvc.perform(post("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SeatType in the database
        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SeatType testSeatType = seatTypeList.get(seatTypeList.size() - 1);
        assertThat(testSeatType.getSeatTypeCode()).isEqualTo(DEFAULT_SEAT_TYPE_CODE);
        assertThat(testSeatType.getSeatTypeName()).isEqualTo(DEFAULT_SEAT_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createSeatTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatTypeRepository.findAll().size();

        // Create the SeatType with an existing ID
        seatType.setId(1L);
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(seatType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatTypeMockMvc.perform(post("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeatTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatTypeRepository.findAll().size();
        // set the field null
        seatType.setSeatTypeCode(null);

        // Create the SeatType, which fails.
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(seatType);

        restSeatTypeMockMvc.perform(post("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeatTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = seatTypeRepository.findAll().size();
        // set the field null
        seatType.setSeatTypeName(null);

        // Create the SeatType, which fails.
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(seatType);

        restSeatTypeMockMvc.perform(post("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeatTypes() throws Exception {
        // Initialize the database
        seatTypeRepository.saveAndFlush(seatType);

        // Get all the seatTypeList
        restSeatTypeMockMvc.perform(get("/api/seat-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seatType.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatTypeCode").value(hasItem(DEFAULT_SEAT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].seatTypeName").value(hasItem(DEFAULT_SEAT_TYPE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSeatType() throws Exception {
        // Initialize the database
        seatTypeRepository.saveAndFlush(seatType);

        // Get the seatType
        restSeatTypeMockMvc.perform(get("/api/seat-types/{id}", seatType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seatType.getId().intValue()))
            .andExpect(jsonPath("$.seatTypeCode").value(DEFAULT_SEAT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.seatTypeName").value(DEFAULT_SEAT_TYPE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeatType() throws Exception {
        // Get the seatType
        restSeatTypeMockMvc.perform(get("/api/seat-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeatType() throws Exception {
        // Initialize the database
        seatTypeRepository.saveAndFlush(seatType);
        int databaseSizeBeforeUpdate = seatTypeRepository.findAll().size();

        // Update the seatType
        SeatType updatedSeatType = seatTypeRepository.findOne(seatType.getId());
        updatedSeatType
            .seatTypeCode(UPDATED_SEAT_TYPE_CODE)
            .seatTypeName(UPDATED_SEAT_TYPE_NAME);
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(updatedSeatType);

        restSeatTypeMockMvc.perform(put("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SeatType in the database
        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeUpdate);
        SeatType testSeatType = seatTypeList.get(seatTypeList.size() - 1);
        assertThat(testSeatType.getSeatTypeCode()).isEqualTo(UPDATED_SEAT_TYPE_CODE);
        assertThat(testSeatType.getSeatTypeName()).isEqualTo(UPDATED_SEAT_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeatType() throws Exception {
        int databaseSizeBeforeUpdate = seatTypeRepository.findAll().size();

        // Create the SeatType
        SeatTypeDTO seatTypeDTO = seatTypeMapper.toDto(seatType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeatTypeMockMvc.perform(put("/api/seat-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SeatType in the database
        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeatType() throws Exception {
        // Initialize the database
        seatTypeRepository.saveAndFlush(seatType);
        int databaseSizeBeforeDelete = seatTypeRepository.findAll().size();

        // Get the seatType
        restSeatTypeMockMvc.perform(delete("/api/seat-types/{id}", seatType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeatType> seatTypeList = seatTypeRepository.findAll();
        assertThat(seatTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatType.class);
        SeatType seatType1 = new SeatType();
        seatType1.setId(1L);
        SeatType seatType2 = new SeatType();
        seatType2.setId(seatType1.getId());
        assertThat(seatType1).isEqualTo(seatType2);
        seatType2.setId(2L);
        assertThat(seatType1).isNotEqualTo(seatType2);
        seatType1.setId(null);
        assertThat(seatType1).isNotEqualTo(seatType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatTypeDTO.class);
        SeatTypeDTO seatTypeDTO1 = new SeatTypeDTO();
        seatTypeDTO1.setId(1L);
        SeatTypeDTO seatTypeDTO2 = new SeatTypeDTO();
        assertThat(seatTypeDTO1).isNotEqualTo(seatTypeDTO2);
        seatTypeDTO2.setId(seatTypeDTO1.getId());
        assertThat(seatTypeDTO1).isEqualTo(seatTypeDTO2);
        seatTypeDTO2.setId(2L);
        assertThat(seatTypeDTO1).isNotEqualTo(seatTypeDTO2);
        seatTypeDTO1.setId(null);
        assertThat(seatTypeDTO1).isNotEqualTo(seatTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seatTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seatTypeMapper.fromId(null)).isNull();
    }
}
