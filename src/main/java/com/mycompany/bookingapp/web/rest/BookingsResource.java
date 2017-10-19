package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.BookingsService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
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
 * REST controller for managing Bookings.
 */
@RestController
@RequestMapping("/api")
public class BookingsResource {

    private final Logger log = LoggerFactory.getLogger(BookingsResource.class);

    private static final String ENTITY_NAME = "bookings";

    private final BookingsService bookingsService;

    public BookingsResource(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    /**
     * POST  /bookings : Create a new bookings.
     *
     * @param bookingsDTO the bookingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookingsDTO, or with status 400 (Bad Request) if the bookings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bookings")
    @Timed
    public ResponseEntity<BookingsDTO> createBookings(@Valid @RequestBody BookingsDTO bookingsDTO) throws URISyntaxException {
        log.debug("REST request to save Bookings : {}", bookingsDTO);
        if (bookingsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bookings cannot already have an ID")).body(null);
        }
        BookingsDTO result = bookingsService.save(bookingsDTO);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bookings : Updates an existing bookings.
     *
     * @param bookingsDTO the bookingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookingsDTO,
     * or with status 400 (Bad Request) if the bookingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookingsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bookings")
    @Timed
    public ResponseEntity<BookingsDTO> updateBookings(@Valid @RequestBody BookingsDTO bookingsDTO) throws URISyntaxException {
        log.debug("REST request to update Bookings : {}", bookingsDTO);
        if (bookingsDTO.getId() == null) {
            return createBookings(bookingsDTO);
        }
        BookingsDTO result = bookingsService.save(bookingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bookingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bookings : get all the bookings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookings in body
     */
    @GetMapping("/bookings")
    @Timed
    public ResponseEntity<List<BookingsDTO>> getAllBookings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Bookings");
        Page<BookingsDTO> page = bookingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bookings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bookings/:id : get the "id" bookings.
     *
     * @param id the id of the bookingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<BookingsDTO> getBookings(@PathVariable Long id) {
        log.debug("REST request to get Bookings : {}", id);
        BookingsDTO bookingsDTO = bookingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bookingsDTO));
    }

    /**
     * DELETE  /bookings/:id : delete the "id" bookings.
     *
     * @param id the id of the bookingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookings(@PathVariable Long id) {
        log.debug("REST request to delete Bookings : {}", id);
        bookingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
