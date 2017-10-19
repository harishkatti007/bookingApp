package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.RegionsService;
import com.mycompany.bookingapp.web.rest.util.HeaderUtil;
import com.mycompany.bookingapp.web.rest.util.PaginationUtil;
import com.mycompany.bookingapp.service.dto.RegionsDTO;
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
 * REST controller for managing Regions.
 */
@RestController
@RequestMapping("/api")
public class RegionsResource {

    private final Logger log = LoggerFactory.getLogger(RegionsResource.class);

    private static final String ENTITY_NAME = "regions";

    private final RegionsService regionsService;

    public RegionsResource(RegionsService regionsService) {
        this.regionsService = regionsService;
    }

    /**
     * POST  /regions : Create a new regions.
     *
     * @param regionsDTO the regionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regionsDTO, or with status 400 (Bad Request) if the regions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regions")
    @Timed
    public ResponseEntity<RegionsDTO> createRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to save Regions : {}", regionsDTO);
        if (regionsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new regions cannot already have an ID")).body(null);
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regions : Updates an existing regions.
     *
     * @param regionsDTO the regionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regionsDTO,
     * or with status 400 (Bad Request) if the regionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the regionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regions")
    @Timed
    public ResponseEntity<RegionsDTO> updateRegions(@Valid @RequestBody RegionsDTO regionsDTO) throws URISyntaxException {
        log.debug("REST request to update Regions : {}", regionsDTO);
        if (regionsDTO.getId() == null) {
            return createRegions(regionsDTO);
        }
        RegionsDTO result = regionsService.save(regionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regions")
    @Timed
    public ResponseEntity<List<RegionsDTO>> getAllRegions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Regions");
        Page<RegionsDTO> page = regionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regions/:id : get the "id" regions.
     *
     * @param id the id of the regionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regions/{id}")
    @Timed
    public ResponseEntity<RegionsDTO> getRegions(@PathVariable Long id) {
        log.debug("REST request to get Regions : {}", id);
        RegionsDTO regionsDTO = regionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(regionsDTO));
    }

    /**
     * DELETE  /regions/:id : delete the "id" regions.
     *
     * @param id the id of the regionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegions(@PathVariable Long id) {
        log.debug("REST request to delete Regions : {}", id);
        regionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
