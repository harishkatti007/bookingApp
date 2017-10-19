package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.VenuesService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.VenuesDTO;
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
 * REST controller for managing Venues.
 */
@RestController
@RequestMapping("/api")
public class VenuesResource {

    private final Logger log = LoggerFactory.getLogger(VenuesResource.class);

    private static final String ENTITY_NAME = "venues";

    private final VenuesService venuesService;

    public VenuesResource(VenuesService venuesService) {
        this.venuesService = venuesService;
    }

    /**
     * POST  /venues : Create a new venues.
     *
     * @param venuesDTO the venuesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new venuesDTO, or with status 400 (Bad Request) if the venues has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/venues")
    @Timed
    public ResponseEntity<VenuesDTO> createVenues(@Valid @RequestBody VenuesDTO venuesDTO) throws URISyntaxException {
        log.debug("REST request to save Venues : {}", venuesDTO);
        if (venuesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new venues cannot already have an ID")).body(null);
        }
        VenuesDTO result = venuesService.save(venuesDTO);
        return ResponseEntity.created(new URI("/api/venues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /venues : Updates an existing venues.
     *
     * @param venuesDTO the venuesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated venuesDTO,
     * or with status 400 (Bad Request) if the venuesDTO is not valid,
     * or with status 500 (Internal Server Error) if the venuesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/venues")
    @Timed
    public ResponseEntity<VenuesDTO> updateVenues(@Valid @RequestBody VenuesDTO venuesDTO) throws URISyntaxException {
        log.debug("REST request to update Venues : {}", venuesDTO);
        if (venuesDTO.getId() == null) {
            return createVenues(venuesDTO);
        }
        VenuesDTO result = venuesService.save(venuesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, venuesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /venues : get all the venues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of venues in body
     */
    @GetMapping("/venues")
    @Timed
    public ResponseEntity<List<VenuesDTO>> getAllVenues(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Venues");
        Page<VenuesDTO> page = venuesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/venues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /venues/:id : get the "id" venues.
     *
     * @param id the id of the venuesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the venuesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/venues/{id}")
    @Timed
    public ResponseEntity<VenuesDTO> getVenues(@PathVariable Long id) {
        log.debug("REST request to get Venues : {}", id);
        VenuesDTO venuesDTO = venuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(venuesDTO));
    }

    /**
     * DELETE  /venues/:id : delete the "id" venues.
     *
     * @param id the id of the venuesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/venues/{id}")
    @Timed
    public ResponseEntity<Void> deleteVenues(@PathVariable Long id) {
        log.debug("REST request to delete Venues : {}", id);
        venuesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
