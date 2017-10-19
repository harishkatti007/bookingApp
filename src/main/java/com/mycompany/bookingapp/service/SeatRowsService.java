package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.SeatRowsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SeatRows.
 */
public interface SeatRowsService {

    /**
     * Save a seatRows.
     *
     * @param seatRowsDTO the entity to save
     * @return the persisted entity
     */
    SeatRowsDTO save(SeatRowsDTO seatRowsDTO);

    /**
     *  Get all the seatRows.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeatRowsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seatRows.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeatRowsDTO findOne(Long id);

    /**
     *  Delete the "id" seatRows.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
