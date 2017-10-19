package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.SeatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Seating.
 */
public interface SeatingService {

    /**
     * Save a seating.
     *
     * @param seatingDTO the entity to save
     * @return the persisted entity
     */
    SeatingDTO save(SeatingDTO seatingDTO);

    /**
     *  Get all the seatings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeatingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seating.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeatingDTO findOne(Long id);

    /**
     *  Delete the "id" seating.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
