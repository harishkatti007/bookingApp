package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.VenuesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Venues.
 */
public interface VenuesService {

    /**
     * Save a venues.
     *
     * @param venuesDTO the entity to save
     * @return the persisted entity
     */
    VenuesDTO save(VenuesDTO venuesDTO);

    /**
     *  Get all the venues.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VenuesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" venues.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VenuesDTO findOne(Long id);

    /**
     *  Delete the "id" venues.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
