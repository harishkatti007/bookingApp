package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.MoviesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Movies.
 */
public interface MoviesService {

    /**
     * Save a movies.
     *
     * @param moviesDTO the entity to save
     * @return the persisted entity
     */
    MoviesDTO save(MoviesDTO moviesDTO);

    /**
     *  Get all the movies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MoviesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" movies.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MoviesDTO findOne(Long id);

    /**
     *  Delete the "id" movies.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
