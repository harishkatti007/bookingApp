package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.RegionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Regions.
 */
public interface RegionsService {

    /**
     * Save a regions.
     *
     * @param regionsDTO the entity to save
     * @return the persisted entity
     */
    RegionsDTO save(RegionsDTO regionsDTO);

    /**
     *  Get all the regions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegionsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" regions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegionsDTO findOne(Long id);

    /**
     *  Delete the "id" regions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
