package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.VenuesService;
import com.mycompany.bookingapp.domain.Venues;
import com.mycompany.bookingapp.repository.VenuesRepository;
import com.mycompany.bookingapp.service.dto.VenuesDTO;
import com.mycompany.bookingapp.service.mapper.VenuesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Venues.
 */
@Service
@Transactional
public class VenuesServiceImpl implements VenuesService{

    private final Logger log = LoggerFactory.getLogger(VenuesServiceImpl.class);

    private final VenuesRepository venuesRepository;

    private final VenuesMapper venuesMapper;

    public VenuesServiceImpl(VenuesRepository venuesRepository, VenuesMapper venuesMapper) {
        this.venuesRepository = venuesRepository;
        this.venuesMapper = venuesMapper;
    }

    /**
     * Save a venues.
     *
     * @param venuesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VenuesDTO save(VenuesDTO venuesDTO) {
        log.debug("Request to save Venues : {}", venuesDTO);
        Venues venues = venuesMapper.toEntity(venuesDTO);
        venues = venuesRepository.save(venues);
        return venuesMapper.toDto(venues);
    }

    /**
     *  Get all the venues.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VenuesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Venues");
        return venuesRepository.findAll(pageable)
            .map(venuesMapper::toDto);
    }

    /**
     *  Get one venues by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VenuesDTO findOne(Long id) {
        log.debug("Request to get Venues : {}", id);
        Venues venues = venuesRepository.findOne(id);
        return venuesMapper.toDto(venues);
    }

    /**
     *  Delete the  venues by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venues : {}", id);
        venuesRepository.delete(id);
    }
}
