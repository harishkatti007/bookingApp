package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.ScreensService;
import com.mycompany.bookingapp.domain.Screens;
import com.mycompany.bookingapp.repository.ScreensRepository;
import com.mycompany.bookingapp.service.dto.ScreensDTO;
import com.mycompany.bookingapp.service.mapper.ScreensMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Screens.
 */
@Service
@Transactional
public class ScreensServiceImpl implements ScreensService{

    private final Logger log = LoggerFactory.getLogger(ScreensServiceImpl.class);

    private final ScreensRepository screensRepository;

    private final ScreensMapper screensMapper;

    public ScreensServiceImpl(ScreensRepository screensRepository, ScreensMapper screensMapper) {
        this.screensRepository = screensRepository;
        this.screensMapper = screensMapper;
    }

    /**
     * Save a screens.
     *
     * @param screensDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScreensDTO save(ScreensDTO screensDTO) {
        log.debug("Request to save Screens : {}", screensDTO);
        Screens screens = screensMapper.toEntity(screensDTO);
        screens = screensRepository.save(screens);
        return screensMapper.toDto(screens);
    }

    /**
     *  Get all the screens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScreensDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Screens");
        return screensRepository.findAll(pageable)
            .map(screensMapper::toDto);
    }

    /**
     *  Get one screens by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScreensDTO findOne(Long id) {
        log.debug("Request to get Screens : {}", id);
        Screens screens = screensRepository.findOne(id);
        return screensMapper.toDto(screens);
    }

    /**
     *  Delete the  screens by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Screens : {}", id);
        screensRepository.delete(id);
    }
}
