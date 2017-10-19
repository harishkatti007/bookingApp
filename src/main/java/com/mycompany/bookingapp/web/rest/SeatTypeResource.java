package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.SeatTypeService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.SeatTypeDTO;
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
 * REST controller for managing SeatType.
 */
@RestController
@RequestMapping("/api")
public class SeatTypeResource {

    private final Logger log = LoggerFactory.getLogger(SeatTypeResource.class);

    private static final String ENTITY_NAME = "seatType";

    private final SeatTypeService seatTypeService;

    public SeatTypeResource(SeatTypeService seatTypeService) {
        this.seatTypeService = seatTypeService;
    }

    /**
     * POST  /seat-types : Create a new seatType.
     *
     * @param seatTypeDTO the seatTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seatTypeDTO, or with status 400 (Bad Request) if the seatType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seat-types")
    @Timed
    public ResponseEntity<SeatTypeDTO> createSeatType(@Valid @RequestBody SeatTypeDTO seatTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SeatType : {}", seatTypeDTO);
        if (seatTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new seatType cannot already have an ID")).body(null);
        }
        SeatTypeDTO result = seatTypeService.save(seatTypeDTO);
        return ResponseEntity.created(new URI("/api/seat-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seat-types : Updates an existing seatType.
     *
     * @param seatTypeDTO the seatTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seatTypeDTO,
     * or with status 400 (Bad Request) if the seatTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the seatTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seat-types")
    @Timed
    public ResponseEntity<SeatTypeDTO> updateSeatType(@Valid @RequestBody SeatTypeDTO seatTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SeatType : {}", seatTypeDTO);
        if (seatTypeDTO.getId() == null) {
            return createSeatType(seatTypeDTO);
        }
        SeatTypeDTO result = seatTypeService.save(seatTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seatTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seat-types : get all the seatTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seatTypes in body
     */
    @GetMapping("/seat-types")
    @Timed
    public ResponseEntity<List<SeatTypeDTO>> getAllSeatTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SeatTypes");
        Page<SeatTypeDTO> page = seatTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seat-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seat-types/:id : get the "id" seatType.
     *
     * @param id the id of the seatTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seatTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seat-types/{id}")
    @Timed
    public ResponseEntity<SeatTypeDTO> getSeatType(@PathVariable Long id) {
        log.debug("REST request to get SeatType : {}", id);
        SeatTypeDTO seatTypeDTO = seatTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seatTypeDTO));
    }

    /**
     * DELETE  /seat-types/:id : delete the "id" seatType.
     *
     * @param id the id of the seatTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seat-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeatType(@PathVariable Long id) {
        log.debug("REST request to delete SeatType : {}", id);
        seatTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
