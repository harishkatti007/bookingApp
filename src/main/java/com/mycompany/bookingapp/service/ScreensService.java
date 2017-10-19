package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.ScreensDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Screens.
 */
public interface ScreensService {

    /**
     * Save a screens.
     *
     * @param screensDTO the entity to save
     * @return the persisted entity
     */
    ScreensDTO save(ScreensDTO screensDTO);

    /**
     *  Get all the screens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ScreensDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" screens.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ScreensDTO findOne(Long id);

    /**
     *  Delete the "id" screens.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
