package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.RegionsService;
import com.mycompany.bookingapp.domain.Regions;
import com.mycompany.bookingapp.repository.RegionsRepository;
import com.mycompany.bookingapp.service.dto.RegionsDTO;
import com.mycompany.bookingapp.service.mapper.RegionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Regions.
 */
@Service
@Transactional
public class RegionsServiceImpl implements RegionsService{

    private final Logger log = LoggerFactory.getLogger(RegionsServiceImpl.class);

    private final RegionsRepository regionsRepository;

    private final RegionsMapper regionsMapper;

    public RegionsServiceImpl(RegionsRepository regionsRepository, RegionsMapper regionsMapper) {
        this.regionsRepository = regionsRepository;
        this.regionsMapper = regionsMapper;
    }

    /**
     * Save a regions.
     *
     * @param regionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegionsDTO save(RegionsDTO regionsDTO) {
        log.debug("Request to save Regions : {}", regionsDTO);
        Regions regions = regionsMapper.toEntity(regionsDTO);
        regions = regionsRepository.save(regions);
        return regionsMapper.toDto(regions);
    }

    /**
     *  Get all the regions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Regions");
        return regionsRepository.findAll(pageable)
            .map(regionsMapper::toDto);
    }

    /**
     *  Get one regions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegionsDTO findOne(Long id) {
        log.debug("Request to get Regions : {}", id);
        Regions regions = regionsRepository.findOne(id);
        return regionsMapper.toDto(regions);
    }

    /**
     *  Delete the  regions by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regions : {}", id);
        regionsRepository.delete(id);
    }
}
