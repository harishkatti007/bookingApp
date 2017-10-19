package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Screening.
 */
public interface ScreeningService {

    /**
     * Save a screening.
     *
     * @param screeningDTO the entity to save
     * @return the persisted entity
     */
    ScreeningDTO save(ScreeningDTO screeningDTO);

    /**
     *  Get all the screenings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ScreeningDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" screening.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ScreeningDTO findOne(Long id);

    /**
     *  Delete the "id" screening.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
