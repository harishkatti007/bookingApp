package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.BookingsService;
import com.mycompany.bookingapp.domain.Bookings;
import com.mycompany.bookingapp.repository.BookingsRepository;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.mapper.BookingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bookings.
 */
@Service
@Transactional
public class BookingsServiceImpl implements BookingsService{

    private final Logger log = LoggerFactory.getLogger(BookingsServiceImpl.class);

    private final BookingsRepository bookingsRepository;

    private final BookingsMapper bookingsMapper;

    public BookingsServiceImpl(BookingsRepository bookingsRepository, BookingsMapper bookingsMapper) {
        this.bookingsRepository = bookingsRepository;
        this.bookingsMapper = bookingsMapper;
    }

    /**
     * Save a bookings.
     *
     * @param bookingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookingsDTO save(BookingsDTO bookingsDTO) {
        log.debug("Request to save Bookings : {}", bookingsDTO);
        Bookings bookings = bookingsMapper.toEntity(bookingsDTO);
        bookings = bookingsRepository.save(bookings);
        return bookingsMapper.toDto(bookings);
    }

    /**
     *  Get all the bookings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingsRepository.findAll(pageable)
            .map(bookingsMapper::toDto);
    }

    /**
     *  Get one bookings by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BookingsDTO findOne(Long id) {
        log.debug("Request to get Bookings : {}", id);
        Bookings bookings = bookingsRepository.findOne(id);
        return bookingsMapper.toDto(bookings);
    }

    /**
     *  Delete the  bookings by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bookings : {}", id);
        bookingsRepository.delete(id);
    }
}
