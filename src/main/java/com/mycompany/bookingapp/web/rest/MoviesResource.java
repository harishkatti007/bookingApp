package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.MoviesService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.MoviesDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Movies.
 */
@RestController
@RequestMapping("/api")
public class MoviesResource {

    private final Logger log = LoggerFactory.getLogger(MoviesResource.class);

    private static final String ENTITY_NAME = "movies";

    private final MoviesService moviesService;

    public MoviesResource(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    /**
     * POST  /movies : Create a new movies.
     *
     * @param moviesDTO the moviesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moviesDTO, or with status 400 (Bad Request) if the movies has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movies")
    @Timed
    public ResponseEntity<MoviesDTO> createMovies(@Valid @RequestBody MoviesDTO moviesDTO) throws URISyntaxException {
        log.debug("REST request to save Movies : {}", moviesDTO);
        if (moviesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new movies cannot already have an ID")).body(null);
        }
        MoviesDTO result = moviesService.save(moviesDTO);
        return ResponseEntity.created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movies : Updates an existing movies.
     *
     * @param moviesDTO the moviesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moviesDTO,
     * or with status 400 (Bad Request) if the moviesDTO is not valid,
     * or with status 500 (Internal Server Error) if the moviesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movies")
    @Timed
    public ResponseEntity<MoviesDTO> updateMovies(@Valid @RequestBody MoviesDTO moviesDTO) throws URISyntaxException {
        log.debug("REST request to update Movies : {}", moviesDTO);
        if (moviesDTO.getId() == null) {
            return createMovies(moviesDTO);
        }
        MoviesDTO result = moviesService.save(moviesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moviesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movies : get all the movies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movies in body
     */
    @GetMapping("/movies")
    @Timed
    public ResponseEntity<List<MoviesDTO>> getAllMovies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Movies");
        Page<MoviesDTO> page = moviesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /movies/:id : get the "id" movies.
     *
     * @param id the id of the moviesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moviesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movies/{id}")
    @Timed
    public ResponseEntity<MoviesDTO> getMovies(@PathVariable Long id) {
        log.debug("REST request to get Movies : {}", id);
        MoviesDTO moviesDTO = moviesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(moviesDTO));
    }

    /**
     * DELETE  /movies/:id : delete the "id" movies.
     *
     * @param id the id of the moviesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovies(@PathVariable Long id) {
        log.debug("REST request to delete Movies : {}", id);
        moviesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
