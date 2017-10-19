package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.ScreeningService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
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
 * REST controller for managing Screening.
 */
@RestController
@RequestMapping("/api")
public class ScreeningResource {

    private final Logger log = LoggerFactory.getLogger(ScreeningResource.class);

    private static final String ENTITY_NAME = "screening";

    private final ScreeningService screeningService;

    public ScreeningResource(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    /**
     * POST  /screenings : Create a new screening.
     *
     * @param screeningDTO the screeningDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new screeningDTO, or with status 400 (Bad Request) if the screening has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/screenings")
    @Timed
    public ResponseEntity<ScreeningDTO> createScreening(@Valid @RequestBody ScreeningDTO screeningDTO) throws URISyntaxException {
        log.debug("REST request to save Screening : {}", screeningDTO);
        if (screeningDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new screening cannot already have an ID")).body(null);
        }
        ScreeningDTO result = screeningService.save(screeningDTO);
        return ResponseEntity.created(new URI("/api/screenings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /screenings : Updates an existing screening.
     *
     * @param screeningDTO the screeningDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated screeningDTO,
     * or with status 400 (Bad Request) if the screeningDTO is not valid,
     * or with status 500 (Internal Server Error) if the screeningDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/screenings")
    @Timed
    public ResponseEntity<ScreeningDTO> updateScreening(@Valid @RequestBody ScreeningDTO screeningDTO) throws URISyntaxException {
        log.debug("REST request to update Screening : {}", screeningDTO);
        if (screeningDTO.getId() == null) {
            return createScreening(screeningDTO);
        }
        ScreeningDTO result = screeningService.save(screeningDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, screeningDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /screenings : get all the screenings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of screenings in body
     */
    @GetMapping("/screenings")
    @Timed
    public ResponseEntity<List<ScreeningDTO>> getAllScreenings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Screenings");
        Page<ScreeningDTO> page = screeningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/screenings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /screenings/:id : get the "id" screening.
     *
     * @param id the id of the screeningDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the screeningDTO, or with status 404 (Not Found)
     */
    @GetMapping("/screenings/{id}")
    @Timed
    public ResponseEntity<ScreeningDTO> getScreening(@PathVariable Long id) {
        log.debug("REST request to get Screening : {}", id);
        ScreeningDTO screeningDTO = screeningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(screeningDTO));
    }

    /**
     * DELETE  /screenings/:id : delete the "id" screening.
     *
     * @param id the id of the screeningDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/screenings/{id}")
    @Timed
    public ResponseEntity<Void> deleteScreening(@PathVariable Long id) {
        log.debug("REST request to delete Screening : {}", id);
        screeningService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
