package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.SeatTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SeatType.
 */
public interface SeatTypeService {

    /**
     * Save a seatType.
     *
     * @param seatTypeDTO the entity to save
     * @return the persisted entity
     */
    SeatTypeDTO save(SeatTypeDTO seatTypeDTO);

    /**
     *  Get all the seatTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SeatTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seatType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SeatTypeDTO findOne(Long id);

    /**
     *  Delete the "id" seatType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
