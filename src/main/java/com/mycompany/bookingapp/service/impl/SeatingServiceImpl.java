package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.SeatingService;
import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.repository.SeatingRepository;
import com.mycompany.bookingapp.service.dto.SeatingDTO;
import com.mycompany.bookingapp.service.mapper.SeatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Seating.
 */
@Service
@Transactional
public class SeatingServiceImpl implements SeatingService{

    private final Logger log = LoggerFactory.getLogger(SeatingServiceImpl.class);

    private final SeatingRepository seatingRepository;

    private final SeatingMapper seatingMapper;

    public SeatingServiceImpl(SeatingRepository seatingRepository, SeatingMapper seatingMapper) {
        this.seatingRepository = seatingRepository;
        this.seatingMapper = seatingMapper;
    }

    /**
     * Save a seating.
     *
     * @param seatingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatingDTO save(SeatingDTO seatingDTO) {
        log.debug("Request to save Seating : {}", seatingDTO);
        Seating seating = seatingMapper.toEntity(seatingDTO);
        seating = seatingRepository.save(seating);
        return seatingMapper.toDto(seating);
    }

    /**
     *  Get all the seatings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seatings");
        return seatingRepository.findAll(pageable)
            .map(seatingMapper::toDto);
    }

    /**
     *  Get one seating by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeatingDTO findOne(Long id) {
        log.debug("Request to get Seating : {}", id);
        Seating seating = seatingRepository.findOne(id);
        return seatingMapper.toDto(seating);
    }

    /**
     *  Delete the  seating by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seating : {}", id);
        seatingRepository.delete(id);
    }
}
