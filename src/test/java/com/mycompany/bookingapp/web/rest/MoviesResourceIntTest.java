package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.BookingApp;

import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.domain.MovieDimensions;
import com.mycompany.bookingapp.repository.MoviesRepository;
import com.mycompany.bookingapp.service.MoviesService;
import com.mycompany.bookingapp.service.dto.MoviesDTO;
import com.mycompany.bookingapp.service.mapper.MoviesMapper;
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
 * Test class for the MoviesResource REST controller.
 *
 * @see MoviesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApp.class)
public class MoviesResourceIntTest {

    private static final String DEFAULT_MOVIE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MOVIE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MOVIE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOVIE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private MoviesMapper moviesMapper;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoviesMockMvc;

    private Movies movies;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MoviesResource moviesResource = new MoviesResource(moviesService);
        this.restMoviesMockMvc = MockMvcBuilders.standaloneSetup(moviesResource)
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
    public static Movies createEntity(EntityManager em) {
        Movies movies = new Movies()
            .movieCode(DEFAULT_MOVIE_CODE)
            .movieName(DEFAULT_MOVIE_NAME)
            .category(DEFAULT_CATEGORY)
            .language(DEFAULT_LANGUAGE);
        // Add required entity
        MovieDimensions movieDimensions = MovieDimensionsResourceIntTest.createEntity(em);
        em.persist(movieDimensions);
        em.flush();
        movies.setMovieDimensions(movieDimensions);
        return movies;
    }

    @Before
    public void initTest() {
        movies = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovies() throws Exception {
        int databaseSizeBeforeCreate = moviesRepository.findAll().size();

        // Create the Movies
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);
        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isCreated());

        // Validate the Movies in the database
        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeCreate + 1);
        Movies testMovies = moviesList.get(moviesList.size() - 1);
        assertThat(testMovies.getMovieCode()).isEqualTo(DEFAULT_MOVIE_CODE);
        assertThat(testMovies.getMovieName()).isEqualTo(DEFAULT_MOVIE_NAME);
        assertThat(testMovies.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMovies.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createMoviesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moviesRepository.findAll().size();

        // Create the Movies with an existing ID
        movies.setId(1L);
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMovieCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moviesRepository.findAll().size();
        // set the field null
        movies.setMovieCode(null);

        // Create the Movies, which fails.
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isBadRequest());

        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMovieNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = moviesRepository.findAll().size();
        // set the field null
        movies.setMovieName(null);

        // Create the Movies, which fails.
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isBadRequest());

        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = moviesRepository.findAll().size();
        // set the field null
        movies.setCategory(null);

        // Create the Movies, which fails.
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isBadRequest());

        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = moviesRepository.findAll().size();
        // set the field null
        movies.setLanguage(null);

        // Create the Movies, which fails.
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        restMoviesMockMvc.perform(post("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isBadRequest());

        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovies() throws Exception {
        // Initialize the database
        moviesRepository.saveAndFlush(movies);

        // Get all the moviesList
        restMoviesMockMvc.perform(get("/api/movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movies.getId().intValue())))
            .andExpect(jsonPath("$.[*].movieCode").value(hasItem(DEFAULT_MOVIE_CODE.toString())))
            .andExpect(jsonPath("$.[*].movieName").value(hasItem(DEFAULT_MOVIE_NAME.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getMovies() throws Exception {
        // Initialize the database
        moviesRepository.saveAndFlush(movies);

        // Get the movies
        restMoviesMockMvc.perform(get("/api/movies/{id}", movies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movies.getId().intValue()))
            .andExpect(jsonPath("$.movieCode").value(DEFAULT_MOVIE_CODE.toString()))
            .andExpect(jsonPath("$.movieName").value(DEFAULT_MOVIE_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovies() throws Exception {
        // Get the movies
        restMoviesMockMvc.perform(get("/api/movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovies() throws Exception {
        // Initialize the database
        moviesRepository.saveAndFlush(movies);
        int databaseSizeBeforeUpdate = moviesRepository.findAll().size();

        // Update the movies
        Movies updatedMovies = moviesRepository.findOne(movies.getId());
        updatedMovies
            .movieCode(UPDATED_MOVIE_CODE)
            .movieName(UPDATED_MOVIE_NAME)
            .category(UPDATED_CATEGORY)
            .language(UPDATED_LANGUAGE);
        MoviesDTO moviesDTO = moviesMapper.toDto(updatedMovies);

        restMoviesMockMvc.perform(put("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isOk());

        // Validate the Movies in the database
        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeUpdate);
        Movies testMovies = moviesList.get(moviesList.size() - 1);
        assertThat(testMovies.getMovieCode()).isEqualTo(UPDATED_MOVIE_CODE);
        assertThat(testMovies.getMovieName()).isEqualTo(UPDATED_MOVIE_NAME);
        assertThat(testMovies.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMovies.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovies() throws Exception {
        int databaseSizeBeforeUpdate = moviesRepository.findAll().size();

        // Create the Movies
        MoviesDTO moviesDTO = moviesMapper.toDto(movies);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMoviesMockMvc.perform(put("/api/movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moviesDTO)))
            .andExpect(status().isCreated());

        // Validate the Movies in the database
        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovies() throws Exception {
        // Initialize the database
        moviesRepository.saveAndFlush(movies);
        int databaseSizeBeforeDelete = moviesRepository.findAll().size();

        // Get the movies
        restMoviesMockMvc.perform(delete("/api/movies/{id}", movies.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Movies> moviesList = moviesRepository.findAll();
        assertThat(moviesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movies.class);
        Movies movies1 = new Movies();
        movies1.setId(1L);
        Movies movies2 = new Movies();
        movies2.setId(movies1.getId());
        assertThat(movies1).isEqualTo(movies2);
        movies2.setId(2L);
        assertThat(movies1).isNotEqualTo(movies2);
        movies1.setId(null);
        assertThat(movies1).isNotEqualTo(movies2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoviesDTO.class);
        MoviesDTO moviesDTO1 = new MoviesDTO();
        moviesDTO1.setId(1L);
        MoviesDTO moviesDTO2 = new MoviesDTO();
        assertThat(moviesDTO1).isNotEqualTo(moviesDTO2);
        moviesDTO2.setId(moviesDTO1.getId());
        assertThat(moviesDTO1).isEqualTo(moviesDTO2);
        moviesDTO2.setId(2L);
        assertThat(moviesDTO1).isNotEqualTo(moviesDTO2);
        moviesDTO1.setId(null);
        assertThat(moviesDTO1).isNotEqualTo(moviesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moviesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moviesMapper.fromId(null)).isNull();
    }
}
