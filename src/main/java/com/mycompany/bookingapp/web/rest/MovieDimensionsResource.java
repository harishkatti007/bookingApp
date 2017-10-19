package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.MovieDimensionsService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.MovieDimensionsDTO;
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
 * REST controller for managing MovieDimensions.
 */
@RestController
@RequestMapping("/api")
public class MovieDimensionsResource {

    private final Logger log = LoggerFactory.getLogger(MovieDimensionsResource.class);

    private static final String ENTITY_NAME = "movieDimensions";

    private final MovieDimensionsService movieDimensionsService;

    public MovieDimensionsResource(MovieDimensionsService movieDimensionsService) {
        this.movieDimensionsService = movieDimensionsService;
    }

    /**
     * POST  /movie-dimensions : Create a new movieDimensions.
     *
     * @param movieDimensionsDTO the movieDimensionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieDimensionsDTO, or with status 400 (Bad Request) if the movieDimensions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-dimensions")
    @Timed
    public ResponseEntity<MovieDimensionsDTO> createMovieDimensions(@Valid @RequestBody MovieDimensionsDTO movieDimensionsDTO) throws URISyntaxException {
        log.debug("REST request to save MovieDimensions : {}", movieDimensionsDTO);
        if (movieDimensionsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new movieDimensions cannot already have an ID")).body(null);
        }
        MovieDimensionsDTO result = movieDimensionsService.save(movieDimensionsDTO);
        return ResponseEntity.created(new URI("/api/movie-dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-dimensions : Updates an existing movieDimensions.
     *
     * @param movieDimensionsDTO the movieDimensionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieDimensionsDTO,
     * or with status 400 (Bad Request) if the movieDimensionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the movieDimensionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-dimensions")
    @Timed
    public ResponseEntity<MovieDimensionsDTO> updateMovieDimensions(@Valid @RequestBody MovieDimensionsDTO movieDimensionsDTO) throws URISyntaxException {
        log.debug("REST request to update MovieDimensions : {}", movieDimensionsDTO);
        if (movieDimensionsDTO.getId() == null) {
            return createMovieDimensions(movieDimensionsDTO);
        }
        MovieDimensionsDTO result = movieDimensionsService.save(movieDimensionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieDimensionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-dimensions : get all the movieDimensions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieDimensions in body
     */
    @GetMapping("/movie-dimensions")
    @Timed
    public ResponseEntity<List<MovieDimensionsDTO>> getAllMovieDimensions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MovieDimensions");
        Page<MovieDimensionsDTO> page = movieDimensionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-dimensions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /movie-dimensions/:id : get the "id" movieDimensions.
     *
     * @param id the id of the movieDimensionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieDimensionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-dimensions/{id}")
    @Timed
    public ResponseEntity<MovieDimensionsDTO> getMovieDimensions(@PathVariable Long id) {
        log.debug("REST request to get MovieDimensions : {}", id);
        MovieDimensionsDTO movieDimensionsDTO = movieDimensionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieDimensionsDTO));
    }

    /**
     * DELETE  /movie-dimensions/:id : delete the "id" movieDimensions.
     *
     * @param id the id of the movieDimensionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-dimensions/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieDimensions(@PathVariable Long id) {
        log.debug("REST request to delete MovieDimensions : {}", id);
        movieDimensionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
