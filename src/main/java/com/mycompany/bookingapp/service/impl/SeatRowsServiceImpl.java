package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.SeatRowsService;
import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.repository.SeatRowsRepository;
import com.mycompany.bookingapp.service.dto.SeatRowsDTO;
import com.mycompany.bookingapp.service.mapper.SeatRowsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SeatRows.
 */
@Service
@Transactional
public class SeatRowsServiceImpl implements SeatRowsService{

    private final Logger log = LoggerFactory.getLogger(SeatRowsServiceImpl.class);

    private final SeatRowsRepository seatRowsRepository;

    private final SeatRowsMapper seatRowsMapper;

    public SeatRowsServiceImpl(SeatRowsRepository seatRowsRepository, SeatRowsMapper seatRowsMapper) {
        this.seatRowsRepository = seatRowsRepository;
        this.seatRowsMapper = seatRowsMapper;
    }

    /**
     * Save a seatRows.
     *
     * @param seatRowsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatRowsDTO save(SeatRowsDTO seatRowsDTO) {
        log.debug("Request to save SeatRows : {}", seatRowsDTO);
        SeatRows seatRows = seatRowsMapper.toEntity(seatRowsDTO);
        seatRows = seatRowsRepository.save(seatRows);
        return seatRowsMapper.toDto(seatRows);
    }

    /**
     *  Get all the seatRows.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeatRowsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeatRows");
        return seatRowsRepository.findAll(pageable)
            .map(seatRowsMapper::toDto);
    }

    /**
     *  Get one seatRows by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeatRowsDTO findOne(Long id) {
        log.debug("Request to get SeatRows : {}", id);
        SeatRows seatRows = seatRowsRepository.findOne(id);
        return seatRowsMapper.toDto(seatRows);
    }

    /**
     *  Delete the  seatRows by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeatRows : {}", id);
        seatRowsRepository.delete(id);
    }
}
