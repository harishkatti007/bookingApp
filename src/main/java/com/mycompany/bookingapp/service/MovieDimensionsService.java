package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.MovieDimensionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MovieDimensions.
 */
public interface MovieDimensionsService {

    /**
     * Save a movieDimensions.
     *
     * @param movieDimensionsDTO the entity to save
     * @return the persisted entity
     */
    MovieDimensionsDTO save(MovieDimensionsDTO movieDimensionsDTO);

    /**
     *  Get all the movieDimensions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MovieDimensionsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" movieDimensions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MovieDimensionsDTO findOne(Long id);

    /**
     *  Delete the "id" movieDimensions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
