package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.ScreeningService;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.repository.ScreeningRepository;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.mapper.ScreeningMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Screening.
 */
@Service
@Transactional
public class ScreeningServiceImpl implements ScreeningService{

    private final Logger log = LoggerFactory.getLogger(ScreeningServiceImpl.class);

    private final ScreeningRepository screeningRepository;

    private final ScreeningMapper screeningMapper;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, ScreeningMapper screeningMapper) {
        this.screeningRepository = screeningRepository;
        this.screeningMapper = screeningMapper;
    }

    /**
     * Save a screening.
     *
     * @param screeningDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScreeningDTO save(ScreeningDTO screeningDTO) {
        log.debug("Request to save Screening : {}", screeningDTO);
        Screening screening = screeningMapper.toEntity(screeningDTO);
        screening = screeningRepository.save(screening);
        return screeningMapper.toDto(screening);
    }

    /**
     *  Get all the screenings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScreeningDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Screenings");
        return screeningRepository.findAll(pageable)
            .map(screeningMapper::toDto);
    }

    /**
     *  Get one screening by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScreeningDTO findOne(Long id) {
        log.debug("Request to get Screening : {}", id);
        Screening screening = screeningRepository.findOne(id);
        return screeningMapper.toDto(screening);
    }

    /**
     *  Delete the  screening by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Screening : {}", id);
        screeningRepository.delete(id);
    }
}
