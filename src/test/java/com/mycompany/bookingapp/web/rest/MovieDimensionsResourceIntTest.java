package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.MovieDimensions;
import com.mycompany.bookingapp.repository.MovieDimensionsRepository;
import com.mycompany.bookingapp.service.MovieDimensionsService;
import com.mycompany.bookingapp.service.dto.MovieDimensionsDTO;
import com.mycompany.bookingapp.service.mapper.MovieDimensionsMapper;
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
 * Test class for the MovieDimensionsResource REST controller.
 *
 * @see MovieDimensionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class MovieDimensionsResourceIntTest {

    private static final String DEFAULT_DIMENSION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION_NAME = "BBBBBBBBBB";

    @Autowired
    private MovieDimensionsRepository movieDimensionsRepository;

    @Autowired
    private MovieDimensionsMapper movieDimensionsMapper;

    @Autowired
    private MovieDimensionsService movieDimensionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovieDimensionsMockMvc;

    private MovieDimensions movieDimensions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MovieDimensionsResource movieDimensionsResource = new MovieDimensionsResource(movieDimensionsService);
        this.restMovieDimensionsMockMvc = MockMvcBuilders.standaloneSetup(movieDimensionsResource)
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
    public static MovieDimensions createEntity(EntityManager em) {
        MovieDimensions movieDimensions = new MovieDimensions()
            .dimensionCode(DEFAULT_DIMENSION_CODE)
            .dimensionName(DEFAULT_DIMENSION_NAME);
        return movieDimensions;
    }

    @Before
    public void initTest() {
        movieDimensions = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieDimensions() throws Exception {
        int databaseSizeBeforeCreate = movieDimensionsRepository.findAll().size();

        // Create the MovieDimensions
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(movieDimensions);
        restMovieDimensionsMockMvc.perform(post("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieDimensions in the database
        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeCreate + 1);
        MovieDimensions testMovieDimensions = movieDimensionsList.get(movieDimensionsList.size() - 1);
        assertThat(testMovieDimensions.getDimensionCode()).isEqualTo(DEFAULT_DIMENSION_CODE);
        assertThat(testMovieDimensions.getDimensionName()).isEqualTo(DEFAULT_DIMENSION_NAME);
    }

    @Test
    @Transactional
    public void createMovieDimensionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieDimensionsRepository.findAll().size();

        // Create the MovieDimensions with an existing ID
        movieDimensions.setId(1L);
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(movieDimensions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieDimensionsMockMvc.perform(post("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDimensionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieDimensionsRepository.findAll().size();
        // set the field null
        movieDimensions.setDimensionCode(null);

        // Create the MovieDimensions, which fails.
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(movieDimensions);

        restMovieDimensionsMockMvc.perform(post("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimensionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movieDimensionsRepository.findAll().size();
        // set the field null
        movieDimensions.setDimensionName(null);

        // Create the MovieDimensions, which fails.
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(movieDimensions);

        restMovieDimensionsMockMvc.perform(post("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isBadRequest());

        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovieDimensions() throws Exception {
        // Initialize the database
        movieDimensionsRepository.saveAndFlush(movieDimensions);

        // Get all the movieDimensionsList
        restMovieDimensionsMockMvc.perform(get("/api/movie-dimensions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieDimensions.getId().intValue())))
            .andExpect(jsonPath("$.[*].dimensionCode").value(hasItem(DEFAULT_DIMENSION_CODE.toString())))
            .andExpect(jsonPath("$.[*].dimensionName").value(hasItem(DEFAULT_DIMENSION_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMovieDimensions() throws Exception {
        // Initialize the database
        movieDimensionsRepository.saveAndFlush(movieDimensions);

        // Get the movieDimensions
        restMovieDimensionsMockMvc.perform(get("/api/movie-dimensions/{id}", movieDimensions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieDimensions.getId().intValue()))
            .andExpect(jsonPath("$.dimensionCode").value(DEFAULT_DIMENSION_CODE.toString()))
            .andExpect(jsonPath("$.dimensionName").value(DEFAULT_DIMENSION_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovieDimensions() throws Exception {
        // Get the movieDimensions
        restMovieDimensionsMockMvc.perform(get("/api/movie-dimensions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieDimensions() throws Exception {
        // Initialize the database
        movieDimensionsRepository.saveAndFlush(movieDimensions);
        int databaseSizeBeforeUpdate = movieDimensionsRepository.findAll().size();

        // Update the movieDimensions
        MovieDimensions updatedMovieDimensions = movieDimensionsRepository.findOne(movieDimensions.getId());
        updatedMovieDimensions
            .dimensionCode(UPDATED_DIMENSION_CODE)
            .dimensionName(UPDATED_DIMENSION_NAME);
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(updatedMovieDimensions);

        restMovieDimensionsMockMvc.perform(put("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isOk());

        // Validate the MovieDimensions in the database
        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeUpdate);
        MovieDimensions testMovieDimensions = movieDimensionsList.get(movieDimensionsList.size() - 1);
        assertThat(testMovieDimensions.getDimensionCode()).isEqualTo(UPDATED_DIMENSION_CODE);
        assertThat(testMovieDimensions.getDimensionName()).isEqualTo(UPDATED_DIMENSION_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieDimensions() throws Exception {
        int databaseSizeBeforeUpdate = movieDimensionsRepository.findAll().size();

        // Create the MovieDimensions
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsMapper.toDto(movieDimensions);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieDimensionsMockMvc.perform(put("/api/movie-dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieDimensionsDTO)))
            .andExpect(status().isCreated());

        // Validate the MovieDimensions in the database
        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieDimensions() throws Exception {
        // Initialize the database
        movieDimensionsRepository.saveAndFlush(movieDimensions);
        int databaseSizeBeforeDelete = movieDimensionsRepository.findAll().size();

        // Get the movieDimensions
        restMovieDimensionsMockMvc.perform(delete("/api/movie-dimensions/{id}", movieDimensions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieDimensions> movieDimensionsList = movieDimensionsRepository.findAll();
        assertThat(movieDimensionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieDimensions.class);
        MovieDimensions movieDimensions1 = new MovieDimensions();
        movieDimensions1.setId(1L);
        MovieDimensions movieDimensions2 = new MovieDimensions();
        movieDimensions2.setId(movieDimensions1.getId());
        assertThat(movieDimensions1).isEqualTo(movieDimensions2);
        movieDimensions2.setId(2L);
        assertThat(movieDimensions1).isNotEqualTo(movieDimensions2);
        movieDimensions1.setId(null);
        assertThat(movieDimensions1).isNotEqualTo(movieDimensions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieDimensionsDTO.class);
        MovieDimensionsDTO movieDimensionsDTO1 = new MovieDimensionsDTO();
        movieDimensionsDTO1.setId(1L);
        MovieDimensionsDTO movieDimensionsDTO2 = new MovieDimensionsDTO();
        assertThat(movieDimensionsDTO1).isNotEqualTo(movieDimensionsDTO2);
        movieDimensionsDTO2.setId(movieDimensionsDTO1.getId());
        assertThat(movieDimensionsDTO1).isEqualTo(movieDimensionsDTO2);
        movieDimensionsDTO2.setId(2L);
        assertThat(movieDimensionsDTO1).isNotEqualTo(movieDimensionsDTO2);
        movieDimensionsDTO1.setId(null);
        assertThat(movieDimensionsDTO1).isNotEqualTo(movieDimensionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movieDimensionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movieDimensionsMapper.fromId(null)).isNull();
    }
}
