package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.SeatTypeService;
import com.mycompany.bookingapp.domain.SeatType;
import com.mycompany.bookingapp.repository.SeatTypeRepository;
import com.mycompany.bookingapp.service.dto.SeatTypeDTO;
import com.mycompany.bookingapp.service.mapper.SeatTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SeatType.
 */
@Service
@Transactional
public class SeatTypeServiceImpl implements SeatTypeService{

    private final Logger log = LoggerFactory.getLogger(SeatTypeServiceImpl.class);

    private final SeatTypeRepository seatTypeRepository;

    private final SeatTypeMapper seatTypeMapper;

    public SeatTypeServiceImpl(SeatTypeRepository seatTypeRepository, SeatTypeMapper seatTypeMapper) {
        this.seatTypeRepository = seatTypeRepository;
        this.seatTypeMapper = seatTypeMapper;
    }

    /**
     * Save a seatType.
     *
     * @param seatTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatTypeDTO save(SeatTypeDTO seatTypeDTO) {
        log.debug("Request to save SeatType : {}", seatTypeDTO);
        SeatType seatType = seatTypeMapper.toEntity(seatTypeDTO);
        seatType = seatTypeRepository.save(seatType);
        return seatTypeMapper.toDto(seatType);
    }

    /**
     *  Get all the seatTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeatTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SeatTypes");
        return seatTypeRepository.findAll(pageable)
            .map(seatTypeMapper::toDto);
    }

    /**
     *  Get one seatType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeatTypeDTO findOne(Long id) {
        log.debug("Request to get SeatType : {}", id);
        SeatType seatType = seatTypeRepository.findOne(id);
        return seatTypeMapper.toDto(seatType);
    }

    /**
     *  Delete the  seatType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeatType : {}", id);
        seatTypeRepository.delete(id);
    }
}
