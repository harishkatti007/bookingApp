package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Regions;
import com.mycompany.bookingapp.repository.RegionsRepository;
import com.mycompany.bookingapp.service.RegionsService;
import com.mycompany.bookingapp.service.dto.RegionsDTO;
import com.mycompany.bookingapp.service.mapper.RegionsMapper;
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
 * Test class for the RegionsResource REST controller.
 *
 * @see RegionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class RegionsResourceIntTest {

    private static final String DEFAULT_REGION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REGION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PINCODE = 1L;
    private static final Long UPDATED_PINCODE = 2L;

    @Autowired
    private RegionsRepository regionsRepository;

    @Autowired
    private RegionsMapper regionsMapper;

    @Autowired
    private RegionsService regionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegionsMockMvc;

    private Regions regions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegionsResource regionsResource = new RegionsResource(regionsService);
        this.restRegionsMockMvc = MockMvcBuilders.standaloneSetup(regionsResource)
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
    public static Regions createEntity(EntityManager em) {
        Regions regions = new Regions()
            .regionCode(DEFAULT_REGION_CODE)
            .regionName(DEFAULT_REGION_NAME)
            .pincode(DEFAULT_PINCODE);
        return regions;
    }

    @Before
    public void initTest() {
        regions = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegions() throws Exception {
        int databaseSizeBeforeCreate = regionsRepository.findAll().size();

        // Create the Regions
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);
        restRegionsMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate + 1);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getRegionCode()).isEqualTo(DEFAULT_REGION_CODE);
        assertThat(testRegions.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testRegions.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    public void createRegionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionsRepository.findAll().size();

        // Create the Regions with an existing ID
        regions.setId(1L);
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionsMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRegionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setRegionCode(null);

        // Create the Regions, which fails.
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        restRegionsMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setRegionName(null);

        // Create the Regions, which fails.
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        restRegionsMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionsRepository.findAll().size();
        // set the field null
        regions.setPincode(null);

        // Create the Regions, which fails.
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        restRegionsMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isBadRequest());

        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get all the regionsList
        restRegionsMockMvc.perform(get("/api/regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regions.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionCode").value(hasItem(DEFAULT_REGION_CODE.toString())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.intValue())));
    }

    @Test
    @Transactional
    public void getRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);

        // Get the regions
        restRegionsMockMvc.perform(get("/api/regions/{id}", regions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regions.getId().intValue()))
            .andExpect(jsonPath("$.regionCode").value(DEFAULT_REGION_CODE.toString()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRegions() throws Exception {
        // Get the regions
        restRegionsMockMvc.perform(get("/api/regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Update the regions
        Regions updatedRegions = regionsRepository.findOne(regions.getId());
        updatedRegions
            .regionCode(UPDATED_REGION_CODE)
            .regionName(UPDATED_REGION_NAME)
            .pincode(UPDATED_PINCODE);
        RegionsDTO regionsDTO = regionsMapper.toDto(updatedRegions);

        restRegionsMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isOk());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate);
        Regions testRegions = regionsList.get(regionsList.size() - 1);
        assertThat(testRegions.getRegionCode()).isEqualTo(UPDATED_REGION_CODE);
        assertThat(testRegions.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegions.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegions() throws Exception {
        int databaseSizeBeforeUpdate = regionsRepository.findAll().size();

        // Create the Regions
        RegionsDTO regionsDTO = regionsMapper.toDto(regions);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegionsMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Regions in the database
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegions() throws Exception {
        // Initialize the database
        regionsRepository.saveAndFlush(regions);
        int databaseSizeBeforeDelete = regionsRepository.findAll().size();

        // Get the regions
        restRegionsMockMvc.perform(delete("/api/regions/{id}", regions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Regions> regionsList = regionsRepository.findAll();
        assertThat(regionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regions.class);
        Regions regions1 = new Regions();
        regions1.setId(1L);
        Regions regions2 = new Regions();
        regions2.setId(regions1.getId());
        assertThat(regions1).isEqualTo(regions2);
        regions2.setId(2L);
        assertThat(regions1).isNotEqualTo(regions2);
        regions1.setId(null);
        assertThat(regions1).isNotEqualTo(regions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionsDTO.class);
        RegionsDTO regionsDTO1 = new RegionsDTO();
        regionsDTO1.setId(1L);
        RegionsDTO regionsDTO2 = new RegionsDTO();
        assertThat(regionsDTO1).isNotEqualTo(regionsDTO2);
        regionsDTO2.setId(regionsDTO1.getId());
        assertThat(regionsDTO1).isEqualTo(regionsDTO2);
        regionsDTO2.setId(2L);
        assertThat(regionsDTO1).isNotEqualTo(regionsDTO2);
        regionsDTO1.setId(null);
        assertThat(regionsDTO1).isNotEqualTo(regionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regionsMapper.fromId(null)).isNull();
    }
}
