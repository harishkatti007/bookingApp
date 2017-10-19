package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.BookingsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Bookings.
 */
public interface BookingsService {

    /**
     * Save a bookings.
     *
     * @param bookingsDTO the entity to save
     * @return the persisted entity
     */
    BookingsDTO save(BookingsDTO bookingsDTO);

    /**
     *  Get all the bookings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BookingsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bookings.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BookingsDTO findOne(Long id);

    /**
     *  Delete the "id" bookings.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
