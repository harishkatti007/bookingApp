package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.SeatRowsService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.SeatRowsDTO;
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
 * REST controller for managing SeatRows.
 */
@RestController
@RequestMapping("/api")
public class SeatRowsResource {

    private final Logger log = LoggerFactory.getLogger(SeatRowsResource.class);

    private static final String ENTITY_NAME = "seatRows";

    private final SeatRowsService seatRowsService;

    public SeatRowsResource(SeatRowsService seatRowsService) {
        this.seatRowsService = seatRowsService;
    }

    /**
     * POST  /seat-rows : Create a new seatRows.
     *
     * @param seatRowsDTO the seatRowsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seatRowsDTO, or with status 400 (Bad Request) if the seatRows has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seat-rows")
    @Timed
    public ResponseEntity<SeatRowsDTO> createSeatRows(@Valid @RequestBody SeatRowsDTO seatRowsDTO) throws URISyntaxException {
        log.debug("REST request to save SeatRows : {}", seatRowsDTO);
        if (seatRowsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seatRows cannot already have an ID")).body(null);
        }
        SeatRowsDTO result = seatRowsService.save(seatRowsDTO);
        return ResponseEntity.created(new URI("/api/seat-rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seat-rows : Updates an existing seatRows.
     *
     * @param seatRowsDTO the seatRowsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seatRowsDTO,
     * or with status 400 (Bad Request) if the seatRowsDTO is not valid,
     * or with status 500 (Internal Server Error) if the seatRowsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seat-rows")
    @Timed
    public ResponseEntity<SeatRowsDTO> updateSeatRows(@Valid @RequestBody SeatRowsDTO seatRowsDTO) throws URISyntaxException {
        log.debug("REST request to update SeatRows : {}", seatRowsDTO);
        if (seatRowsDTO.getId() == null) {
            return createSeatRows(seatRowsDTO);
        }
        SeatRowsDTO result = seatRowsService.save(seatRowsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seatRowsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seat-rows : get all the seatRows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seatRows in body
     */
    @GetMapping("/seat-rows")
    @Timed
    public ResponseEntity<List<SeatRowsDTO>> getAllSeatRows(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SeatRows");
        Page<SeatRowsDTO> page = seatRowsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seat-rows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seat-rows/:id : get the "id" seatRows.
     *
     * @param id the id of the seatRowsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seatRowsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seat-rows/{id}")
    @Timed
    public ResponseEntity<SeatRowsDTO> getSeatRows(@PathVariable Long id) {
        log.debug("REST request to get SeatRows : {}", id);
        SeatRowsDTO seatRowsDTO = seatRowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seatRowsDTO));
    }

    /**
     * DELETE  /seat-rows/:id : delete the "id" seatRows.
     *
     * @param id the id of the seatRowsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seat-rows/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeatRows(@PathVariable Long id) {
        log.debug("REST request to delete SeatRows : {}", id);
        seatRowsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
