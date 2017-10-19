package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.ScreensService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.ScreensDTO;
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
 * REST controller for managing Screens.
 */
@RestController
@RequestMapping("/api")
public class ScreensResource {

    private final Logger log = LoggerFactory.getLogger(ScreensResource.class);

    private static final String ENTITY_NAME = "screens";

    private final ScreensService screensService;

    public ScreensResource(ScreensService screensService) {
        this.screensService = screensService;
    }

    /**
     * POST  /screens : Create a new screens.
     *
     * @param screensDTO the screensDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new screensDTO, or with status 400 (Bad Request) if the screens has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/screens")
    @Timed
    public ResponseEntity<ScreensDTO> createScreens(@Valid @RequestBody ScreensDTO screensDTO) throws URISyntaxException {
        log.debug("REST request to save Screens : {}", screensDTO);
        if (screensDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new screens cannot already have an ID")).body(null);
        }
        ScreensDTO result = screensService.save(screensDTO);
        return ResponseEntity.created(new URI("/api/screens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /screens : Updates an existing screens.
     *
     * @param screensDTO the screensDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated screensDTO,
     * or with status 400 (Bad Request) if the screensDTO is not valid,
     * or with status 500 (Internal Server Error) if the screensDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/screens")
    @Timed
    public ResponseEntity<ScreensDTO> updateScreens(@Valid @RequestBody ScreensDTO screensDTO) throws URISyntaxException {
        log.debug("REST request to update Screens : {}", screensDTO);
        if (screensDTO.getId() == null) {
            return createScreens(screensDTO);
        }
        ScreensDTO result = screensService.save(screensDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, screensDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /screens : get all the screens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of screens in body
     */
    @GetMapping("/screens")
    @Timed
    public ResponseEntity<List<ScreensDTO>> getAllScreens(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Screens");
        Page<ScreensDTO> page = screensService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/screens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /screens/:id : get the "id" screens.
     *
     * @param id the id of the screensDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the screensDTO, or with status 404 (Not Found)
     */
    @GetMapping("/screens/{id}")
    @Timed
    public ResponseEntity<ScreensDTO> getScreens(@PathVariable Long id) {
        log.debug("REST request to get Screens : {}", id);
        ScreensDTO screensDTO = screensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(screensDTO));
    }

    /**
     * DELETE  /screens/:id : delete the "id" screens.
     *
     * @param id the id of the screensDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/screens/{id}")
    @Timed
    public ResponseEntity<Void> deleteScreens(@PathVariable Long id) {
        log.debug("REST request to delete Screens : {}", id);
        screensService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
